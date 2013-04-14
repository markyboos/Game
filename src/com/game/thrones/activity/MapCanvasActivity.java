
package com.game.thrones.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.game.thrones.AbstractMenuActivity;
import com.game.thrones.R;
import com.game.thrones.engine.GameController;
import com.game.thrones.model.hero.Hero;

/**
 *
 * @author James
 */
public class MapCanvasActivity extends AbstractMenuActivity implements GameFinishedListener {
    
    private View mapView;

    @Override public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.dashboard);
        
        mapView = findViewById(R.id.mapView);
        
        GameController.getInstance().addCameraChangeListener((CameraChangeListener)mapView);
        GameController.getInstance().addGameFinishedListener(this);
        
        updateHUD();
        
        Button buttonOne = (Button) findViewById(R.id.endTurnButton);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(final View v) {
                
                GameController controller = GameController.getInstance();

                controller.endTurn();
                
                mapView.invalidate();

                updateHUD();
            }
        });
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        
        if (hasFocus) {
            updateHUD();
        }
        
    }
    
    private void updateHUD() {
        
        TextView textView = (TextView)findViewById(R.id.dashBoard);
        
        GameController controller = GameController.getInstance();
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

}
