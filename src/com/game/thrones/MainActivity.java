package com.game.thrones;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.game.thrones.activity.MapCanvasActivity;
import com.game.thrones.engine.GameController;

/**
 * Doesnt do a lot but will probably be the menu in the future
 * @author James
 */
public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        GameController.reset();
        
        GameController.getInstance();
        
        //start the map activity
        Intent intent = new Intent(this, MapCanvasActivity.class);
        
        this.startActivity(intent);
    }
    
    @Override
    public void onResume() {
        super.onResume();
    }
}
