
package com.game.thrones.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.game.thrones.engine.GameController;
import com.game.thrones.engine.actions.Action;
import com.game.thrones.engine.actions.AttackGeneralAction;
import com.game.thrones.engine.actions.BarbarianAttackAction;
import com.game.thrones.engine.actions.ItemSelectAction;
import com.game.thrones.engine.actions.MultipleTerritorySelectAction;
import com.game.thrones.engine.actions.PlayerSelectAction;
import com.game.thrones.engine.actions.RumorsAction;
import com.game.thrones.engine.actions.ShapeShiftAction;
import com.game.thrones.engine.actions.SingleTerritorySelectAction;
import com.game.thrones.engine.actions.TeamSelectAction;
import com.game.thrones.engine.descriptions.Describable;
import com.game.thrones.model.AllFilter;
import com.game.thrones.model.AndFilter;
import com.game.thrones.model.EnoughItemsFilter;
import com.game.thrones.model.Filter;
import com.game.thrones.model.PieceTerritoryFilter;
import com.game.thrones.model.Team;
import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.InventorySearcher;
import com.game.thrones.model.item.AttackGeneralItem;
import com.game.thrones.model.item.Item;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Takes actions for an activity, this might include any extra choices the action needs.
 *
 * @author James
 */
public class ActionTaker {

    private final Hero hero;
    private final Activity activity;
    private final boolean finish;
    private InventorySearcher searcher = new InventorySearcher();
    
    private final GameController controller = GameController.getInstance();

    public ActionTaker(Hero hero, Activity activity, boolean finish) {
        this.activity = activity;
        this.hero = hero;
        this.finish = finish;
    }

    public void takeAction(Action selected) {

        if (selected instanceof AttackGeneralAction) {

            //make a choice about the items to use
            choosePlayers((AttackGeneralAction) selected);

            return;
        }
        if (selected instanceof ItemSelectAction) {

            ItemSelectAction itemAction = (ItemSelectAction) selected;

            List<AttackGeneralItem> options = hero.getItems(itemAction.getItemFilter(), AttackGeneralItem.class);

            chooseItem(itemAction, options);

            return;
        }

        if (selected instanceof BarbarianAttackAction) {

            chooseExtraActions((BarbarianAttackAction) selected);

            return;
        }
        if (selected instanceof RumorsAction) {

            chooseTeam((TeamSelectAction) selected, false);

            return;
        }
        if (selected instanceof ShapeShiftAction) {

            chooseTeam((TeamSelectAction) selected, true);

            return;
        }
        if (selected instanceof SingleTerritorySelectAction) {

            SingleTerritorySelectAction action = (SingleTerritorySelectAction) selected;

            if (!action.chosenTerritory()) {
                chooseTerritory(action);

                return;
            }
        }
        if (selected instanceof MultipleTerritorySelectAction) {
            MultipleTerritorySelectAction action = (MultipleTerritorySelectAction) selected;
            
            if (!action.chosenTerritory()) {
                chooseTerritories(action);
                
                return;
            }
        }
        if (selected instanceof PlayerSelectAction) {
            choosePlayer((PlayerSelectAction) selected);
            
            return;
        }

        //all actions have finished
        takeMove(selected);

    }

    private void chooseTeam(final TeamSelectAction action, final boolean includeNoone) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Choose a team");
        builder.setItems(Team.getTeamNames(includeNoone), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                action.setTeam(Team.getTeams(includeNoone)[which]);
                takeMove(action);
            }
        });

        builder.show();
    }

    private void chooseExtraActions(final BarbarianAttackAction action) {

        final Spinner input = new Spinner(activity);
        final ArrayList<Integer> numbers = new ArrayList<Integer>();

        final int actionsAvailable = action.getPiece().getActionsAvailable() - 1;

        if (actionsAvailable == 0) {
            takeMove(action);
            return;
        }

        for (int i = 0; i <= actionsAvailable; i++) {
            numbers.add(i);
        }

        final ArrayAdapter adapter = new ArrayAdapter(activity,
                android.R.layout.simple_spinner_dropdown_item, numbers);

        input.setAdapter(adapter);

        new AlertDialog.Builder(activity)
                .setTitle("Choose extra actions to attack with")
                .setView(input)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int extraAttacks;

                try {
                    extraAttacks = Integer.parseInt(input.getSelectedItem().toString());
                } catch (NumberFormatException ex) {
                    return;
                }

                action.setExtraAttacks(Math.min(extraAttacks, actionsAvailable));

                takeMove(action);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        }).show();
    }

    private void chooseTerritory(final SingleTerritorySelectAction action) {

        List<Territory> options = action.getOptions();

        if (options.isEmpty()) {
            return;
        }

        //todo choose a territory        
        final CharSequence[] items = options.toArray(new CharSequence[options.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Make your selection");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                action.setTerritory(action.getOptions().get(item));

                takeAction(action);
            }
        });

        builder.show();
    }
    
    private void chooseTerritories(final MultipleTerritorySelectAction action) {
        
        final List<Territory> options = action.getOptions();

        if (options.isEmpty()) {
            return;
        }
        
        final List<Territory> selectedTerritories = new ArrayList<Territory>();

        //todo choose a territory        
        final CharSequence[] items = options.toArray(new CharSequence[options.size()]);
        
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Make your selection, please choose " + action.getTotal() + " territories");
        builder.setMultiChoiceItems(options.toArray(new Territory[options.size()]), null,
        new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which,
                    boolean isChecked) {

                Territory selected = options.get(which);
                if (isChecked) {
                    selectedTerritories.add(selected);
                } else if (selectedTerritories.contains(selected)) {
                    selectedTerritories.remove(selected);
                }
            }
        })
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                
                if (selectedTerritories.size() == action.getTotal()) {
                    action.setTerritories(selectedTerritories);
                
                    takeAction(action);                    
                }                
            }
        });
        builder.setCancelable(false);

        builder.show();     
    }
    
    private AttackGeneralItem selectedItem;

    private void chooseItem(final ItemSelectAction action, final List<AttackGeneralItem> options) {

        selectedItem = options.get(0);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Choose which item to use");
        builder.setSingleChoiceItems(options.toArray(new AttackGeneralItem[options.size()]), 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ActionTaker.this.selectedItem = options.get(which);
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                action.setItem(ActionTaker.this.selectedItem);
                takeMove(action);
            }
        });

        builder.show();
    }

    private void choosePlayers(final AttackGeneralAction action) {

        Filter<AttackGeneralItem> teamFilter = searcher.aTeamOrNooneFilter(action.getTarget().getTeam());

        Filter<Hero> filter =
                new AndFilter<Hero>(
                new PieceTerritoryFilter(hero.getPosition()),
                new EnoughItemsFilter(teamFilter));
        
        final List<Hero> options = controller.getBoard()
                .getPieces(filter, Hero.class);

        final LinkedList<Hero> selectedHeroes = new LinkedList<Hero>();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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

        final List<AttackGeneralItem> options = hero.getItems(
                searcher.aTeamOrNooneFilter(action.getTarget().getTeam()), AttackGeneralItem.class);

        final List<AttackGeneralItem> selectedItems = new ArrayList<AttackGeneralItem>();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Choose which items " + hero + " will use");
        builder.setMultiChoiceItems(options.toArray(new AttackGeneralItem[options.size()]), null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                            boolean isChecked) {

                        AttackGeneralItem selected = options.get(which);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Choose which items to discard, you must remove " + toRemove + " items");
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
                    hero.disposeItem(item);
                }

                finish();
            }
        })
                .setCancelable(false);
        builder.show();

    }    
    
    private Hero selectedPlayer;

    private void choosePlayer(final PlayerSelectAction action) {
        final List<Hero> pieces = controller.getBoard().getPieces(AllFilter.INSTANCE, Hero.class);
        
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Choose which players to use");
        builder.setSingleChoiceItems(pieces.toArray(new Hero[pieces.size()]), 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ActionTaker.this.selectedPlayer = pieces.get(which);
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                action.setPlayer(ActionTaker.this.selectedPlayer);
                takeMove(action);
            }
        });
        
        builder.show();
    }

    private void showSummary(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Summary")
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                removeCardsAndFinishActivity();
            }
        });

        builder.show();

    }

    private void takeMove(final Action action) {                
        controller.takeMove(action);
        
        if (action instanceof Describable) {
            Describable describable = (Describable) action;
            showSummary(describable.render());
            return;
        }

        removeCardsAndFinishActivity();
    }

    private void removeCardsAndFinishActivity() {
        if (hero.getCardsToRemove() > 0) {
            chooseItemsToRemove(hero.getCardsToRemove());
            hero.setCardsToRemove(0);
            return;
        }
        finish();
    }

    private void finish() {
        
        if (finish) {
            activity.finish();
        }
    }
}
