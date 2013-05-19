
package com.game.thrones.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.game.thrones.AbstractMenuActivity;
import com.game.thrones.R;
import com.game.thrones.engine.GameController;
import com.game.thrones.engine.GamePhase;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.StartActionHero;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author James
 */
public class MapCanvasActivity extends AbstractMenuActivity implements GameFinishedListener, GamePhaseChangeListener {
    
    private View mapView;
    
    private GameController controller = GameController.getInstance();

    @Override public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.dashboard);
        
        mapView = findViewById(R.id.mapView);
        
        GameController.getInstance().addCameraChangeListener((CameraChangeListener)mapView);
        GameController.getInstance().addGameFinishedListener(this);
        GameController.getInstance().addGamePhaseChangeListener(this);
        
        updateHUD();
        
        Button buttonOne = (Button) findViewById(R.id.endTurnButton);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(final View v) {
                
                fireGamePhaseChangeEvent(new GamePhaseChangeEvent(GamePhase.EVENING));
                
                updateHUD();
            }
        });
        
        Button buttonTwo = (Button) findViewById(R.id.inventoryButton);
        buttonTwo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(final View v) {
                
                Intent intent = new Intent(MapCanvasActivity.this, InventoryActivity.class);
        
                startActivity(intent);                
            }
        });
        
        this.fireGamePhaseChangeEvent(new GamePhaseChangeEvent(GamePhase.MORNING));
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        
        if (hasFocus) {
            updateHUD();
            
            if (controller.getPlayer().getActionsAvailable() == 0) {
                //end that players turn
                controller.fireGamePhaseChangeEvent(new GamePhaseChangeEvent(GamePhase.EVENING));
            }
        }
        
    }
    
    private void updateHUD() {
        
        mapView.postInvalidate();
        
        TextView textView = (TextView)findViewById(R.id.dashBoard);
        
        Hero player = controller.getPlayer();
        
        textView.setText("turn[" + player.getName() + "] moves left[" + player.getActionsAvailable() + "]");
        textView.invalidate();
    }

    public void fireGameFinishedEvent(final GameFinishedEvent event) {
        
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle(event.getFinished().getDescription());
        ab.setCancelable(true);
        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int x) {
                MapCanvasActivity.this.finish();
            }
        });

        ab.show();
    }
    
    public void fireGamePhaseChangeEvent(GamePhaseChangeEvent phase) {
        if (phase.getGamePhase() == GamePhase.EVENING) {
            
            Toast.makeText(this, "Evening comes..", Toast.LENGTH_SHORT).show();
            
            scheduleNextPhase(phase.getGamePhase());
            
        } else if (phase.getGamePhase() == GamePhase.NIGHT) {
            
            Toast.makeText(this, "The forces of darkness makes their move", Toast.LENGTH_SHORT).show();
            
            scheduleNextPhase(phase.getGamePhase());
            
        } else if (phase.getGamePhase() == GamePhase.MORNING) {
            
            controller.fireCameraChangeEvent(
                    new CameraChangeEvent(controller.getPlayer().getPosition()));
                                                         
            updateHUD();
            
            Toast.makeText(this, "You awake to start the day", Toast.LENGTH_SHORT).show();
            
            scheduleNextPhase(phase.getGamePhase());            
        }
    }
    
    private Timer timer = new Timer();
    private Handler handler = new Handler();
    
    //the actual length of a short toast    
    private static final int SHORT_DELAY = 2000;

    private void scheduleNextPhase(final GamePhase nextPhase) {
        
        mapView.setEnabled(false);
        
        timer.schedule(
            new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {

                            public void run() {
                                switch (nextPhase) {
                                    case MORNING:
                                        controller.startMorningPhase();
                                        
                                        mapView.postInvalidate();                                        
                                        updateHUD();
                                        
                                        takeStartingAction(controller);
                                        mapView.setEnabled(true);
                                        break;
                                    case EVENING:
                                        
                                        controller.takeEveningPhase();
                                        break;
                                    case NIGHT:
                                        
                                        controller.takeNightPhase();
                                        break;
                                    default:
                                        throw new AssertionError(nextPhase.name());

                                }
                            }
                        });                        
                    }
            }, SHORT_DELAY);
    }
    
    private void takeStartingAction(final GameController controller) {
        
        if (controller.getPlayer() instanceof StartActionHero) {
            StartActionHero action = (StartActionHero)controller.getPlayer();

            ActionTaker taker = 
                    new ActionTaker(controller.getPlayer(), MapCanvasActivity.this, false);

            taker.takeAction(action.getStartingAction());
        }
    }
}
