package com.game.thrones;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import com.game.framework.SoundManager;
import com.game.framework.VibrationManager;
import com.game.thrones.activity.MapCanvasActivity;
import com.game.thrones.engine.GameController;
import com.game.thrones.model.hero.Barbarian;
import com.game.thrones.model.hero.Dwarf;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Paladin;
import com.game.thrones.model.hero.Ranger;
import com.game.thrones.model.hero.Sorceress;
import com.game.thrones.model.hero.Wizard;
import java.util.ArrayList;
import java.util.List;

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
        
        loadSoundManager();
        loadVibrationHandler();
        
        choosePlayers();
        
        //startGame();
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
    
    private void choosePlayers() {
        
        final List<Hero> options = new ArrayList<Hero>();
        
        options.add(new Paladin());
        options.add(new Barbarian());
        options.add(new Dwarf());
        options.add(new Wizard());
        options.add(new Ranger());
        options.add(new Sorceress());
        
        final List<Hero> selectedHeroes = new ArrayList<Hero>();
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("What heroes will you choose");
        builder.setMultiChoiceItems(options.toArray(new Hero[options.size()]), null,
                   new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which,
                    boolean isChecked) {

                Hero selected = options.get(which);
                if (isChecked) {
                    selectedHeroes.add(selected);
                } else if (selectedHeroes.contains(selected)) {
                    selectedHeroes.remove(selected);
                }
            }
        })
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (selectedHeroes.size() < 2) {
                    if (!selectedHeroes.contains(options.get(0))) {
                        selectedHeroes.add(options.get(0));
                    }
                    if (!selectedHeroes.contains(options.get(1))) {
                        selectedHeroes.add(options.get(1));
                    }
                }
                startGame(selectedHeroes.subList(0, 2));
            }
        });

        builder.show();
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

    private void startGame(List<Hero> heroes) {
        
        GameController controller = GameController.getInstance();
        controller.initialise(heroes);
        
        //start the map activity
        Intent intent = new Intent(this, MapCanvasActivity.class);
        
        startActivity(intent);
    }
}
