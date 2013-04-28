package com.game.thrones.activity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.game.thrones.engine.Action;
import com.game.thrones.engine.ActionCreator;
import com.game.thrones.engine.AttackAction;
import com.game.thrones.engine.AttackGeneralAction;
import com.game.thrones.engine.BarbarianAttackAction;
import com.game.thrones.engine.GameController;
import com.game.thrones.engine.ItemSelectAction;
import com.game.thrones.engine.RumorsAction;
import com.game.thrones.engine.ShapeShiftAction;
import com.game.thrones.engine.TeamSelectAction;
import com.game.thrones.engine.TerritorySelectAction;
import com.game.thrones.engine.descriptions.AttackDescriptionRenderer;
import com.game.thrones.model.ChainedFilter;
import com.game.thrones.model.EnoughItemsFilter;
import com.game.thrones.model.PieceFilter;
import com.game.thrones.model.Team;
import com.game.thrones.model.Territory;
import com.game.thrones.model.TerritoryFilter;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Item;
import com.game.thrones.model.piece.Piece;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This is an activity which shows a list of actions a piece can do for that
 * turn. Might be better as a pop up or when the graphics are better be put into
 * the map.
 *
 * @author James
 */
public class PlayerActionActivity extends ListActivity {

    public static final String PIECE_NAME = "PIECE";    
    private GameController controller = GameController.getInstance();
    private ActionCreator actionCreator = new ActionCreator();
    private Hero hero;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //this ensures that the volume control is for music rather than ringtone
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            throw new IllegalArgumentException("Failed to get PIECE_NAME, did you forget to add it?");
        }

        String name = extras.getString(PIECE_NAME);

        if (name == null) {
            throw new IllegalArgumentException("Failed to get PIECE_NAME, did you forget to add it?");
        }

        Piece turnItIs = controller.getBoard().getPiece(name);

        hero = (Hero) turnItIs;

        List<Action> actions = actionCreator.createActions(turnItIs);

        setListAdapter(new ActionAdapter(actions));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Action selected = (Action) l.getItemAtPosition(position);

        if (selected instanceof AttackGeneralAction) {
            
            //make a choice about the items to use
            choosePlayers((AttackGeneralAction) selected);
            
            return;            
        }
        if (selected instanceof ItemSelectAction) {
            
            chooseItem((ItemSelectAction)selected);
            
            return;
        }
        
        if (selected instanceof BarbarianAttackAction) {
            
            chooseExtraActions((BarbarianAttackAction)selected);
            
            return;            
        }
        if (selected instanceof RumorsAction) {

            chooseTeam((TeamSelectAction)selected, false);
            
            return;
        }
        if (selected instanceof ShapeShiftAction) {

            chooseTeam((TeamSelectAction)selected, true);
            
            return;
        }
        if (selected instanceof TerritorySelectAction) {
            chooseTerritory((TerritorySelectAction)selected);
            
            return;
        }

        //all actions have finished
        takeMove(selected);
    }

    class ActionAdapter extends ArrayAdapter<Action> {

        public ActionAdapter(List<Action> options) {
            super(PlayerActionActivity.this,
                    android.R.layout.simple_list_item_1, options);
        }
    }

    private void chooseTeam(final TeamSelectAction action, final boolean includeNoone) {
        String[] items = new String[includeNoone ? Team.values().length : Team.values().length - 1];

        for (int i = 0; i < Team.values().length; i++) {
            final Team team = Team.values()[i];
            
            if (team == Team.NO_ONE && !includeNoone) {
                continue;
            }
            
            items[i] = team.name();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a team");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                
                action.setTeam(Team.values()[which]);
                takeMove(action);
            }
        });

        builder.show();
    }
    
    private void chooseExtraActions(final BarbarianAttackAction action) {
        
        final EditText input = new EditText(this);
        input.setText("0");

        new AlertDialog.Builder(this)
        .setTitle("Choose extra actions to attack with")
        .setView(input)
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {                
                int extraAttacks;
                
                try {
                    extraAttacks = Integer.parseInt(input.getText().toString());
                } catch (NumberFormatException ex) {
                    return;
                }
                
                action.setExtraAttacks(Math.min(extraAttacks, action.getPiece().getActionsAvailable() - 1));
                
                takeMove(action);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {}
        }).show();
    }
    
    private void chooseTerritory(final TerritorySelectAction action) {
        
        //choose a territory
        Territory territory = GameController.getInstance().getBoard().getCentralTerritory();
        
        action.setTerritory(territory);
        
        takeMove(action);
        
    }
    
    private Item selectedItem;
    
    private void chooseItem(final ItemSelectAction action) {
        
        final List<Item> options = hero.getItemsForTeam(hero.getPosition().getOwner());
        
        selectedItem = options.get(0);
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose which item to use");
        builder.setSingleChoiceItems(options.toArray(new Item[options.size()]), 0,
                   new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                PlayerActionActivity.this.selectedItem = options.get(which);
            }
        })
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                action.setItem(PlayerActionActivity.this.selectedItem);
                takeMove(action);
            }
        });

        builder.show();
    }
    
    private void choosePlayers(final AttackGeneralAction action) {
                        
        PieceFilter filter = 
                new ChainedFilter(
                    new TerritoryFilter(hero.getPosition()),
                    new EnoughItemsFilter(action.getTarget().getTeam())
                );
        
        final List<Hero> options = GameController.getInstance().getBoard()
                .getPieces(filter, Hero.class);                
        
        final LinkedList<Hero> selectedHeroes = new LinkedList<Hero>();
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose which players to use");
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
                chooseItems(selectedHeroes, action);
            }
        });

        builder.show();
        
    }
    
    private void chooseItems(final LinkedList<Hero> heroes, final AttackGeneralAction action) {
        
        //if weve gone through the list already then we should take the move
        if (heroes.isEmpty()) {
            takeMove(action);
            return;
        }
        
        final Hero hero = heroes.pop();
        
        final List<Item> options = hero.getItemsForTeam(action.getTarget().getTeam());        
        final List<Item> selectedItems = new ArrayList<Item>();
                
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose which items " + hero + " will use");
        builder.setMultiChoiceItems(options.toArray(new Item[options.size()]), null,
                   new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which,
                    boolean isChecked) {

                Item selected = options.get(which);
                if (isChecked) {
                    selectedItems.add(selected);
                } else if (selectedItems.contains(selected)) {
                    selectedItems.remove(selected);
                }
            }
        })
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                action.putItems(hero, selectedItems);
                chooseItems(heroes, action);
            }
        });

        builder.show();
    }
    
    private void chooseItemsToRemove(final int total) {
        
        final List<Item> options = hero.getInventory();
        
        final int toRemove = Math.min(total, options.size());
        
        final List<Item> selectedItems = new ArrayList<Item>();
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose which items to discard");
        builder.setMultiChoiceItems(options.toArray(new Item[options.size()]), null,
                   new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which,
                    boolean isChecked) {

                Item selected = options.get(which);
                if (isChecked) {
                    selectedItems.add(selected);
                } else if (selectedItems.contains(selected)) {
                    selectedItems.remove(selected);
                }
            }
        })
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (selectedItems.size() != toRemove) {
                    //todo keep trying
                    return;
                }
                
                for (Item item : selectedItems) {
                    hero.useItem(item);
                }
                
                PlayerActionActivity.this.finish();
            }
        });
        builder.show();
        
    }
    
    private void showSummary(String message) {
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Summary")
        .setMessage(message)
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {                
                PlayerActionActivity.this.finish();
            }
        });
        
        builder.show();
        
    }
    
    private void takeMove(final Action action) {
        controller.takeMove(action);
            
        if (hero.getCardsToRemove() > 0) {
            
            chooseItemsToRemove(hero.getCardsToRemove());
            
            hero.setCardsToRemove(0);
            return;
        }
        
        if (action instanceof AttackAction) {
            AttackAction describable = (AttackAction)action;
            AttackDescriptionRenderer view = new AttackDescriptionRenderer();
            showSummary(view.render(describable.summary()));
            return;
        }
        
        this.finish();
    }
}
