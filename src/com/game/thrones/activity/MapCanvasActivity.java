
package com.game.thrones.activity;

import android.app.Activity;
import android.os.Bundle;

/**
 *
 * @author James
 */
public class MapCanvasActivity extends Activity {

    @Override public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        
        setContentView(new MapView(this, null));
        
    }
        

}
