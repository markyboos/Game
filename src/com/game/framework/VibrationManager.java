package com.game.framework;

import android.os.Vibrator;

/**
 *
 * @author James
 */
public class VibrationManager {
    
    private static VibrationManager instance;
    
    private Vibrator vibrator;

    public static VibrationManager getSingleton() {
      if(instance == null) {
         instance = new VibrationManager();
      }
      return instance;
    }
    
    private VibrationManager() { }
    
    private boolean enabled = true;
    
    public void setEnabled(final boolean e) {
        enabled = e;        
    }
    
    public void vibrate(final long time) {
        if (enabled) {
            vibrator.vibrate(time);
        }        
    }

    public void setVibrator(final Vibrator vibrator) {
        this.vibrator = vibrator;
    }
    
}
