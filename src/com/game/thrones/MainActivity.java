package com.game.thrones;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import com.game.framework.SoundManager;
import com.game.framework.VibrationManager;
import com.game.thrones.activity.MapCanvasActivity;
import com.game.thrones.engine.GameController;

/**
 * Doesnt do a lot but will probably be the menu in the future
 * @author James
 */
public class MainActivity extends AbstractMenuActivity {
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        GameController.reset();
        
        GameController.getInstance();
        
        loadSoundManager();
        loadVibrationHandler();
        
        //start the map activity
        Intent intent = new Intent(this, MapCanvasActivity.class);
        
        startActivity(intent);
    }
    
    @Override
    public void onDestroy() {
        SoundManager.getSingleton().releaseResources();        
        super.onDestroy();
    }
    
    @Override
    public void onResume() {
        super.onResume();
    }

    private void loadSoundManager() {   
        final SoundManager soundManager = SoundManager.getSingleton();
        
        soundManager.initSounds(getApplicationContext());
        
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... arg0) {
                soundManager.playMusic(R.raw.themetune);
                
                return null;
            }
        }.doInBackground((Void) null);
    }

    private void loadVibrationHandler() {
        VibrationManager vibrationManager = VibrationManager.getSingleton();        
        vibrationManager.setVibrator((Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE));        
    }
}
