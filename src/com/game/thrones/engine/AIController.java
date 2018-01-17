
package com.game.thrones.engine;

import com.game.thrones.engine.actions.MoveAction;
import com.game.thrones.engine.actions.evil.AllQuietAction;
import com.game.thrones.engine.actions.evil.AssaultAction;
import com.game.thrones.engine.actions.MoveAlongPathAction;
import com.game.thrones.engine.actions.evil.AddMinionAction;
import com.game.thrones.engine.actions.evil.OrcPatrolsAction;
import com.game.thrones.engine.actions.Action;
import android.util.Log;
import com.game.thrones.model.AllFilter;
import com.game.thrones.model.Board;
import com.game.thrones.model.PieceTerritoryFilter;
import com.game.thrones.model.Team;
import com.game.thrones.model.Territory;
import com.game.thrones.model.TerritoryCriteria;
import com.game.thrones.model.hero.General;
import com.game.thrones.model.hero.Minion;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * In charge of AI moves.
 *
 * @author James
 */
public class AIController {
    
    /**
     * Increases as generals die.
     */
    private enum WarStatus {
        
        START(1), EARLY_MID(2), LATE_MID(2), LATE(3);
        
        private int cardsToDraw;
        WarStatus(int total) {
            cardsToDraw = total;
        }
        
        public int getCardsToDraw() {
            return cardsToDraw;
        }
    }
    
    private WarStatus warStatus = WarStatus.START;
    
    private GameController controller = GameController.getInstance();
    
    private Deck<Action> deck = new Deck<Action>(createEvilActions());
    
    public void increaseWarStatus() {
        if (warStatus.ordinal() == WarStatus.values().length) {
            return;
        }
        
        warStatus 
                = WarStatus.values()[warStatus.ordinal() + 1];
    }
    
    public Action takeTopCard() {
        Action orders = deck.takeTopCard();
        
        deck.discard(orders);
        
        return orders;
    }
    
    public Queue<Action> takeTurn() {
        Queue<Action> orders = new LinkedList<Action>();
        for (int i = 0; i < warStatus.getCardsToDraw(); i++) {
            orders.add(takeTopCard());
        }
        return orders;
    }

    public void initialise() {
        //add extra minions to the board
        addExtraMinions(3, 2);
        addExtraMinions(3, 1);
        //and reshuffle the evil deck
        deck.reshuffleDeck();
    }
    
    private List<Action> createEvilActions() {
        
        Log.d("AIController:createEvilActions", "Generating evil actions...");
        
        LinkedList<Action> orders = new LinkedList<Action>();
        final Territory centralTerritory = controller.getBoard().getCentralTerritory();
        
        for (Territory t : controller.getBoard().getTerritories()) {
            
            if (t.equals(centralTerritory) || t.getOwner() == Team.NO_ONE) {
                continue;
            }

            orders.add(new AddMinionAction(t, 2, t.getOwner()));
        }
             
        for (General general : controller.getBoard().getPieces(new AllFilter<General>(), General.class)) {

            List<Territory> path = controller.getBoard()
                    .getPathToTerritory(general.getPosition(), centralTerritory);

            //includes direct movement and partial
            for (Territory t : path) {
                orders.add(new MoveAction(general, t));
            }

            for (int i = 0; i < 2; i ++) {
                orders.add(new MoveAlongPathAction(general, centralTerritory, 1));
            }
        }
        
        //orc patrols
        
        TerritoryCriteria territoryCriteria = new TerritoryCriteria();
        territoryCriteria.setMinionCount(1);
        territoryCriteria.setOwner(Team.ORCS);
        
        orders.add(createOrcPatrols(territoryCriteria));
        
        territoryCriteria = new TerritoryCriteria();
        territoryCriteria.setMinionCount(0);
        territoryCriteria.setOwner(Team.ORCS);
        
        orders.add(createOrcPatrols(territoryCriteria));
        
        //assault on the centre
        
        orders.add(createAssaultAction());
        
        //all quiet
        for (int i = 0; i < 3; i ++) {
            orders.add(allQuiet());
        }
        
        Collections.shuffle(orders);
        
        return orders;
        
    }

    //orc patrols
    private Action createOrcPatrols(TerritoryCriteria territoryCriteria) {
        
        return new OrcPatrolsAction(territoryCriteria, Team.ORCS);
    }

    private Action createAssaultAction() {
        return new AssaultAction();
    }
    
    private Action allQuiet() {
        return new AllQuietAction();
    }
    
    /**
     * Adds extra minions to the board on game initialisation.
     * @param cards the number of cards to pick up from the ai controller
     * @param total the number of minions to add
     */
    private void addExtraMinions(int cards, int total) {
        //add extra monsters to the board
        
        final Board board = controller.getBoard();

        while (true) {
            //draw 3 cards
            Action action = takeTopCard();
            if (action instanceof AddMinionAction) {
                AddMinionAction addMinionAction = (AddMinionAction)action;
                Territory territory = addMinionAction.getTerritory();

                //its not possible to have more than 3 on a territory at the beginning
                if (board.getPieces(new PieceTerritoryFilter(territory), Minion.class).size() + total > 3
                        || board.getCentralTerritory().equals(territory)) {
                    continue;
                }

                Log.d("addExtraMinions", "adding [" + total + "] minions to [" + territory.getName() + "]");

                for (int i = 0 ; i < total; i++) {
                    board.addMinionToTerritory(territory, territory.getOwner(), false);
                }
                cards --;
                break;
            }

            if (cards == 0) {
                break;
            }
        }
    }

}
