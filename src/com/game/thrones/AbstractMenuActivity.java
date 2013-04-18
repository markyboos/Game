
package com.game.thrones;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.game.framework.SavedPreferences;
import com.game.framework.SoundManager;
import com.game.framework.VibrationManager;

/**
 * Provides some basic things for all activities
 *
 * @author James
 */
public abstract class AbstractMenuActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }
    
    @Override
    public void onPause() {
        super.onPause();
    }    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_settings:
                showOptionsDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    private void showOptionsDialog() {
        
        final SavedPreferences preferences = SavedPreferences.getSingleton();        
        
        boolean[] options = new boolean[2];
        options[0] = preferences.getMusicEnabled();
        options[1] = preferences.getVibrationEnabled();
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Set the dialog title
        builder.setTitle(R.string.options)
                
        // Specify the list array, the items to be selected by default (null for none),
        // and the listener through which to receive callbacks when items are selected
        .setMultiChoiceItems(R.array.options, options,
                   new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which,
                    boolean isChecked) {

                if (which == 0) {
                    preferences.setMusicEnabled(!preferences.getMusicEnabled());
                } else {
                    preferences.setVibrationEnabled(!preferences.getVibrationEnabled());
                }               

            }
        })
        // Set the action buttons
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... arg0) {
                        SoundManager soundManager = SoundManager.getSingleton();
                
                        soundManager.setEnabled(preferences.getMusicEnabled());                
                        if (preferences.getMusicEnabled() == false) {
                            soundManager.stop();                                
                        } else if (!soundManager.isPlayingMusic()) {
                            soundManager.playMusic(R.raw.themetune);
                        }

                        VibrationManager.getSingleton().setEnabled(preferences.getVibrationEnabled());

                        return null;
                    }
                }.doInBackground((Void) null);
                
            }
        });

        builder.show();
    }

}
