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
    
    public static final int FIREBALL = 0;
    public static final int SWORDFIGHT = 1;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        GameController controller = GameController.getInstance();
        controller.initialise();
        
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
                
                soundManager.addSound(FIREBALL, R.raw.fireball);
                soundManager.addSound(SWORDFIGHT, R.raw.swordfight);
                
                return null;
            }
        }.doInBackground((Void) null);
    }

    private void loadVibrationHandler() {
        VibrationManager vibrationManager = VibrationManager.getSingleton();        
        vibrationManager.setVibrator((Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE));        
    }
}
