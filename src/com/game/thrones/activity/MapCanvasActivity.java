
package com.game.thrones.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.game.thrones.R;
import com.game.thrones.engine.GameController;
import com.game.thrones.model.House;

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
        
        setTextDashboard();
        
        Button buttonOne = (Button) findViewById(R.id.endTurnButton);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(final View v) {
                GameController controller = GameController.getInstance();
                controller.takeTurn();
                
                mapView.invalidate();
                
                setTextDashboard();
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
    
    private void setTextDashboard() {
        
        TextView textView = (TextView)findViewById(R.id.dashBoard);
        
        GameController controller = GameController.getInstance();
        House house = controller.getPlayer();
        
        textView.setText("turn[" + house.getName() + "] £[" + house.getFunds() + "]");
        textView.invalidate();
    }

}
