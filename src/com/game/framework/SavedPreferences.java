package com.game.framework;

import java.io.Serializable;

/**
 *
 * @author James
 */
public class SavedPreferences implements Serializable {
    
    private static final long serialVersionUID = 1l;
    
    private static transient SavedPreferences instance;

    public static SavedPreferences getSingleton() {
      if(instance == null) {
         instance = new SavedPreferences();
      }
      return instance;
    }

    public void setInstance() {
        instance = this;
    }
    
    private SavedPreferences(){};
    
    private boolean rated;
    private int askedLeft = 3;
    private boolean musicEnabled = true;    
    private boolean vibrationEnabled = true;
    
    public boolean askRating() {
        if (!rated && askedLeft > 0) {
            askedLeft --;
            return true;
        }
        return false;        
    }
    
    public void setRated(final boolean r) {
        rated = r;
    }
    
    public void setMusicEnabled(final boolean m) {
        musicEnabled = m;        
    }
    
    public void setVibrationEnabled(final boolean v) {
        vibrationEnabled = v;
    }
    
    public boolean isRated() {
        return rated;
    }
    
    public boolean getMusicEnabled() {
        return musicEnabled;
    }
    
    public boolean getVibrationEnabled() {
        return vibrationEnabled;
    }
    
}
