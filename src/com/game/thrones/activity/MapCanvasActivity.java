package com.game.thrones.activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.game.framework.SoundManager;
import com.game.thrones.AbstractMenuActivity;
import com.game.thrones.MainActivity;
import com.game.thrones.R;
import com.game.thrones.engine.GameController;
import com.game.thrones.engine.GamePhase;
import com.game.thrones.engine.Orders;
import com.game.thrones.engine.actions.Action;
import com.game.thrones.engine.actions.CollectItemsAction;
import com.game.thrones.engine.actions.MinionsAttackAction;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.StartActionHero;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author James
 */
public class MapCanvasActivity extends AbstractMenuActivity implements
        GameFinishedListener, GamePhaseChangeListener, HudUpdateListener {

    private View mapView;
    private GameController controller = GameController.getInstance();
    
    private Button endTurnButton;
    private Button inventoryButton;
    private Button characterButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dashboard);

        mapView = findViewById(R.id.mapView);

        //ensure the HUD is up to date
        ((MapView)mapView).setHUDListener(this);

        GameController.getInstance().addCameraChangeListener((CameraChangeListener) mapView);
        GameController.getInstance().addGameFinishedListener(this);
        GameController.getInstance().addGamePhaseChangeListener(this);

        //zoom in on the starting players
        ((CameraChangeListener)mapView).fireCameraChangeEvent(new CameraChangeEvent(controller.getBoard().getCentralTerritory()));

        updateHUD();

        endTurnButton = (Button) findViewById(R.id.endTurnButton);
        endTurnButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(final View v) {

                controller.fireGamePhaseChangeEvent(new GamePhaseChangeEvent(GamePhase.EVENING));

                updateHUD();
            }
        });

        inventoryButton = (Button) findViewById(R.id.inventoryButton);
        inventoryButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(final View v) {

                if (!controller.getPlayer().getInventory().isEmpty()) {

                    Intent intent = new Intent(MapCanvasActivity.this, InventoryActivity.class);

                    startActivity(intent);
                } else {

                    Toast.makeText(MapCanvasActivity.this, "You dont have any items..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        characterButton = (Button) findViewById(R.id.characterButton);
        characterButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(final View v) {

                //FragmentManager manager = getFragmentManager();
                //FragmentTransaction transaction = manager.beginTransaction();
                //transaction.add(R.id.fragment_container, new HeroViewActivity());
                //transaction.commit();
                HeroViewActivity hv = new HeroViewActivity();
                hv.show(getFragmentManager(), "HeroFragment");
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            updateHUD();

            //if (controller.getPlayer().getActionsAvailable() == 0) {
                //end that players turn
            //    controller.fireGamePhaseChangeEvent(new GamePhaseChangeEvent(GamePhase.EVENING));
            //}
        }

    }

    private void updateHUD() {

        mapView.postInvalidate();

        ((MapView)mapView).resetMoves();

        TextView textView = (TextView) findViewById(R.id.dashBoard);

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
            
            enableUI(false);

            Toast.makeText(this, "Evening comes..", Toast.LENGTH_SHORT).show();

            scheduleNextPhase(phase.getGamePhase());

        } else if (phase.getGamePhase() == GamePhase.NIGHT) {

            Toast.makeText(this, "The forces of darkness make their move", Toast.LENGTH_SHORT).show();

            scheduleNextPhase(phase.getGamePhase());

        } else if (phase.getGamePhase() == GamePhase.MORNING) {

            enableUI(true);

            SoundManager.getSingleton().playSound(MainActivity.MORNING);

            controller.fireCameraChangeEvent(
                    new CameraChangeEvent(controller.getPlayer().getPosition()));

            updateHUD();

            Toast.makeText(this, "You awake to start the day", Toast.LENGTH_SHORT).show();

            scheduleNextPhase(phase.getGamePhase());
        }
    }

    private void enableUI(boolean enabled) {
        endTurnButton.setEnabled(enabled);
        inventoryButton.setEnabled(enabled);
        characterButton.setEnabled(enabled);
    }

    private void scheduleNextPhase(final GamePhase nextPhase) {

        Log.i("state change", "scheduling phase " + nextPhase);

        mapView.setEnabled(false);

        switch (nextPhase) {

            case MORNING:
                controller.startMorningPhase();

                mapView.postInvalidate();
                updateHUD();

                takeStartingAction();
                mapView.setEnabled(true);
                break;
            case EVENING:

                ActionTaker taker =
                        new ActionTaker(controller.getPlayer(), MapCanvasActivity.this, new Handler.Callback() {
                            @Override
                            public boolean handleMessage(Message message) {
                                controller.fireGamePhaseChangeEvent(new GamePhaseChangeEvent(GamePhase.NIGHT));
                                return false;
                            }
                        });

                taker.takeAction(new CollectItemsAction(controller.getPlayer()));

                break;
            case NIGHT:

                takeMinionDamage();
                break;
            default:
                throw new AssertionError(nextPhase.name());

        };
    }

    private void takeStartingAction() {

        if (controller.getPlayer() instanceof StartActionHero) {
            StartActionHero action = (StartActionHero) controller.getPlayer();

            ActionTaker taker =
                    new ActionTaker(controller.getPlayer(), MapCanvasActivity.this);

            taker.takeAction(action.getStartingAction());
        }
    }

    private void takeMinionDamage() {
        AIActionTaker taker = new AIActionTaker(MapCanvasActivity.this, new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                takeNightPhase();
                return false;
            }
        });
        MinionsAttackAction action = new MinionsAttackAction(controller.getPlayer());

        if (action.shouldExecute()) {
            taker.takeAction(action);
        } else {
            takeNightPhase();
        }
    }

    private void takeNightPhase() {

        Log.i("state change", "Taking Night phase");

        controller.endEveningPhase();
        //take evil players turn
        final Queue<Action> orders = controller.takeAIOrders();

        final AIActionTaker taker = new AIActionTaker(MapCanvasActivity.this, orders, new Handler.Callback() {
            AtomicBoolean done = new AtomicBoolean(false);
            @Override
            public boolean handleMessage(Message msg) {
                Log.i("state change", "Firing in night phase");
                //only accept one message
                if (done.compareAndSet(false, true)) {
                    Log.i("state change", "Firing end night phase");
                    controller.endNightPhase();
                }

                return true;
            }
        });

        taker.takeAITurn();


    }

    @Override
    public void UpdateHUD() {
        updateHUD();
    }
}
