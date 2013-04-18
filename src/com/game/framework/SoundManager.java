package com.game.framework;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import java.util.HashMap;

/**
 *
 * @author James
 */
public class SoundManager {
    
    private boolean enabled = true;
    
    private static SoundManager instance;

    public static SoundManager getSingleton() {
      if(instance == null) {
         instance = new SoundManager();
      }
      return instance;
    }
    
    private SoundManager() { }
    
    public void setEnabled(final boolean e) {
        enabled = e;
    }
    
    private Context context;
    private SoundPool soundPool;
    private HashMap<Integer, Integer> soundPoolMap;
    private AudioManager audioManager;      
    private MediaPlayer mediaPlayer;

    public void initSounds(final Context context) {
        this.context = context;
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        soundPoolMap = new HashMap();
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public void addSound(int index, int soundId) {
        soundPoolMap.put(index, soundPool.load(context, soundId, 1));
    }
    
    /**
     * Rather than with sounds, only one piece of music can be played at one time.
     * @param themeTune the resource id
     */    
    public void playMusic(int themeTune) {
        
        if (!enabled) {
            return;
        }
        
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        
        mediaPlayer = MediaPlayer.create(context, themeTune);
        
        float streamVolume = getVolume();        
        mediaPlayer.setVolume(streamVolume, streamVolume);

        mediaPlayer.setLooping(true);

        mediaPlayer.start();        
    }
    
    public boolean isPlayingMusic() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }
    
    public void playSound(int index, float speed) {
        if (!enabled) {
            return;
        }        
        float streamVolume = getVolume();
        soundPool.play(soundPoolMap.get(index), streamVolume, streamVolume, 1, 0, speed);
        
    }

    public void playSound(int index) {        
        playSound(index, 1f);
    }

    public void playLoopedSound(int index) {
        if (!enabled) {
            return;
        }
        float streamVolume = getVolume();
        soundPool.play(soundPoolMap.get(index), streamVolume, streamVolume, 1, -1, 1f);
    }
    
    /**
     * Stops any music that is currently playing.
     */
    public void stop() {
        mediaPlayer.stop();
        
        for (Integer index : soundPoolMap.keySet()) {
            soundPool.stop(index);
        }
    }

    public void releaseResources() {
        soundPool.release();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    private float getVolume() {
        float streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        return streamVolume / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

}
