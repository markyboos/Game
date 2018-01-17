package com.game.thrones;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import com.game.framework.SoundManager;
import com.game.framework.VibrationManager;
import com.game.thrones.activity.MapCanvasActivity;
import com.game.thrones.engine.GameController;
import com.game.thrones.engine.GameInitialiser;
import com.game.thrones.engine.GameOptions;
import com.game.thrones.engine.GameType;
import com.game.thrones.model.hero.Barbarian;
import com.game.thrones.model.hero.Daywalker;
import com.game.thrones.model.hero.DoctorJekyll;
import com.game.thrones.model.hero.Dwarf;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Paladin;
import com.game.thrones.model.hero.Ranger;
import com.game.thrones.model.hero.Rogue;
import com.game.thrones.model.hero.Sorceress;
import com.game.thrones.model.hero.Wizard;
import java.util.ArrayList;
import java.util.List;

/**
 * Doesn't do a lot but will probably be the menu in the future
 * @author James
 */
public class MainActivity extends AbstractMenuActivity {
    
    public static final int FIREBALL = 0;
    public static final int SWORDFIGHT = 1;
    public static final int WALK = 2;
    public static final int HORSE = 3;
    public static final int EAGLE = 4;
    public static final int TELEPORT = 5;
    public static final int MORNING = 6;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        loadSoundManager();
        loadVibrationHandler();
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

    public void chooseGameMode(View view) {
        final List<CharSequence> options = new ArrayList<CharSequence>();

        options.add(GameType.BIG.toString());
        options.add(GameType.SMALL.toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("What game mode do you want to play?");
        builder.setSingleChoiceItems(options.toArray(new CharSequence[options.size()]), 1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selected = (String)options.get(which);
                        GameType picked = GameType.valueOf(selected);
                        choosePlayers(picked);
                        dialog.dismiss();
                    }
                });

        builder.show();
    }
    
    private void choosePlayers(final GameType gameType) {
        
        final List<Hero> options = new ArrayList<Hero>();
        
        options.add(new Barbarian());
        //options.add(new Daywalker());
        //options.add(new DoctorJekyll());
        options.add(new Dwarf());
        options.add(new Paladin());        
        options.add(new Ranger());
        options.add(new Rogue());
        options.add(new Sorceress());
        options.add(new Wizard());

        final List<Hero> selectedHeroes = new ArrayList<Hero>();

        final int numberOfOptions = gameType.maxHeros;
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Which heroes will you choose");
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
                if (selectedHeroes.size() < numberOfOptions) {
                    if (!selectedHeroes.contains(options.get(0))) {
                        selectedHeroes.add(options.get(0));
                    }
                    if (!selectedHeroes.contains(options.get(1))) {
                        selectedHeroes.add(options.get(1));
                    }
                }

                List<Hero> picked = selectedHeroes.subList(0, numberOfOptions);
                startGame(new GameOptions(gameType, picked));
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
                soundManager.addSound(WALK, R.raw.walkingedited);
                soundManager.addSound(HORSE, R.raw.galloping);
                soundManager.addSound(EAGLE, R.raw.eaglecall);
                soundManager.addSound(TELEPORT, R.raw.teleport);
                soundManager.addSound(MORNING, R.raw.morning);
                
                return null;
            }
        }.doInBackground((Void) null);
    }

    private void loadVibrationHandler() {
        VibrationManager vibrationManager = VibrationManager.getSingleton();        
        vibrationManager.setVibrator((Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE));        
    }

    private void startGame(GameOptions options) {
        
        GameController controller = GameController.getInstance();
        controller.initialise(options);
        
        //start the map activity
        Intent intent = new Intent(this, MapCanvasActivity.class);
        
        startActivity(intent);
    }
}
