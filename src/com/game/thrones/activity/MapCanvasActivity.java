
package com.game.thrones.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.game.thrones.R;
import com.game.thrones.engine.GameController;
import com.game.thrones.model.hero.Hero;

/**
 *
 * @author James
 */
public class MapCanvasActivity extends Activity {
    
    private View mapView;

    @Override public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.dashboard);
        
        mapView = findViewById(R.id.mapView);
        
        updateTextDashboard();
        
        Button buttonOne = (Button) findViewById(R.id.endTurnButton);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(final View v) {
                GameController controller = GameController.getInstance();
                
                if (controller.endTurn()) {        
                    showLoseDialog();
                }
                
                mapView.invalidate();
                
                updateTextDashboard();
            }
        });
        
        Button buttonTwo = (Button) findViewById(R.id.standings);
        buttonTwo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(final View v) {
                Intent intent = new Intent(MapCanvasActivity.this, HouseStandingsActivity.class);
        
                MapCanvasActivity.this.startActivity(intent);
            }
        });
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        
        if (hasFocus) {
            updateTextDashboard();
        }
        
    }
    
    private void updateTextDashboard() {
        
        TextView textView = (TextView)findViewById(R.id.dashBoard);
        
        GameController controller = GameController.getInstance();
        Hero player = controller.getPlayer();
        
        textView.setText("turn[" + player.getName() + "] moves left[" + player.getActionsAvailable() + "]");
        textView.invalidate();
    }
    
    private void showLoseDialog() {
        //youve lost!

        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("You lose obi wah kinobi");
        ab.setCancelable(true);
        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            
            public void onClick(DialogInterface arg0, int x) {
                MapCanvasActivity.this.finish();
            }
        });

        ab.show();      
    }

}
