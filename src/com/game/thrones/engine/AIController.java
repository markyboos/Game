
package com.game.thrones.engine;

import com.game.thrones.engine.actions.MoveAlongPathAction;
import com.game.thrones.engine.actions.AddMinionAction;
import com.game.thrones.engine.actions.MoveAction;
import com.game.thrones.engine.actions.OrcPatrolsAction;
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
        private WarStatus(int total) {
            cardsToDraw = total;
        }
        
        public int getCardsToDraw() {
            return cardsToDraw;
        }
    }
    
    private WarStatus warStatus = WarStatus.START;
    
    private GameController controller = GameController.getInstance();
    
    private Deck<Orders> deck = new Deck<Orders>(createEvilActions());
    
    public void increaseWarStatus() {
        if (warStatus.ordinal() == WarStatus.values().length) {
            return;
        }
        
        warStatus 
                = WarStatus.values()[warStatus.ordinal() + 1];
    }
    
    public Orders takeTopCard() {
        return deck.takeTopCard();
    }
    
    public void takeTurn() {        
        for (int i = 0; i < warStatus.getCardsToDraw(); i++) {
            drawAndUseCard();
        }
    }
    
    private void drawAndUseCard() {
        Orders orders = deck.takeTopCard();

        //add minions to places        
        //add taint        
        //possibly move general
        for (Action action : orders.getActions()) {
            action.execute();            
        }
        
        deck.discard(orders);
    }
    
    public void initialise() {
        //add extra minions to the board
        addExtraMinions(3, 2);
        addExtraMinions(3, 1);
        //and reshuffle the evil deck
        deck.reshuffleDeck();
    }
    
    private List<Orders> createEvilActions() {
        
        Log.d("AIController:createEvilActions", "Generating evil actions...");
        
        LinkedList<Orders> orders = new LinkedList<Orders>();
        final Territory centralTerritory = controller.getBoard().getCentralTerritory();
        
        for (Territory t : controller.getBoard().getTerritories()) {
            
            if (t.equals(centralTerritory) || t.getOwner() == Team.NO_ONE) {
                continue;
            }
            
            Orders order = new Orders();
            order.addAction(new AddMinionAction(t, 2, t.getOwner()));
            orders.add(order);
        }
             
        for (General general : controller.getBoard().getPieces(new AllFilter<General>(), General.class)) {
                    
            List<Territory> path = controller.getBoard()
                    .getPathToTerritory(general.getPosition(), centralTerritory);

            for (Territory t : path) {
                Orders order = new Orders();
                order.addAction(new MoveAction(general, t));

                orders.add(order);
            }

            for (int i = 0; i < 2; i ++) {

                Orders order = new Orders();
                order.addAction(new MoveAlongPathAction(general, centralTerritory, 1));

                orders.add(order);
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
        
        orders.add(createAssaultAction(centralTerritory));
        
        //all quiet
        for (int i = 0; i < 3; i ++) {
            orders.add(allQuiet());
        }
        
        Collections.shuffle(orders);
        
        return orders;
        
    }

    //orc patrols
    private Orders createOrcPatrols(TerritoryCriteria territoryCriteria) {
        
        Orders order = new Orders();
        
        order.addAction(new OrcPatrolsAction(territoryCriteria, Team.ORCS));
        
        return order;        
    }

    private Orders createAssaultAction(Territory centre) {
        Orders order = new Orders();
        
        for (Team team : Team.values()) {
            if (team == Team.NO_ONE || !team.enabled()) {
                continue;
            }
            order.addAction(new AddMinionAction(centre, 1, team));
        }
        
        return order;
    }
    
    private Orders allQuiet() {
        return new Orders();
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
            Orders orders = takeTopCard();
            for (Action action : orders.getActions()) {
                if (action instanceof AddMinionAction) {
                    AddMinionAction addMinionAction = (AddMinionAction)action;
                    Territory territory = addMinionAction.getTerritory();

                    //its not possible to have more than 3 on a territory at the beggining
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
            }

            if (cards == 0) {
                break;
            }
        }
    }

}
