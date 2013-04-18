
package com.game.thrones.engine;

import android.util.Log;
import com.game.thrones.model.PieceCriteria;
import com.game.thrones.model.Team;
import com.game.thrones.model.Territory;
import com.game.thrones.model.TerritoryCriteria;
import com.game.thrones.model.hero.General;
import com.game.thrones.model.piece.Piece;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * In charge of AI moves.
 *
 * @author James
 */
public class AIController {
    
    private GameController controller = GameController.getInstance();
    
    private Set<Orders> discardPile = new HashSet<Orders>();
    
    private Queue<Orders> evilActions;
    
    public void takeTurn() {
        
        if (evilActions == null) {
            evilActions = createEvilActions();
        }
        
        Orders orders = evilActions.poll();
        
        if (orders == null) {
            recreateDeckFromDiscardPile();
            orders = evilActions.poll();
        }
        
        //add minions to places        
        //add taint        
        //possibly move general
        for (Action action : orders.getActions()) {
            action.execute();            
        }
        
        discardPile.add(orders);
        
    }
    
    private void recreateDeckFromDiscardPile() {
        
        Log.d("AIController:recreateDeck", "Recreating the deck...");
        
        LinkedList<Orders> newActions = new LinkedList<Orders>(discardPile);
        
        discardPile.clear();
        
        Collections.shuffle(newActions);
        
        evilActions = newActions;        
    }
    
    private Queue<Orders> createEvilActions() {
        
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
        
        PieceCriteria criteria = new PieceCriteria();
        criteria.setClass(General.class);
             
        for (Piece piece : controller.getBoard().getPieces(criteria)) {
            
            General general = (General)piece;
        
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
        territoryCriteria.setMinionTeam(Team.ORCS);
        
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
        
        order.addAction(new OrcPatrolsAction(territoryCriteria));
        
        return order;        
    }

    private Orders createAssaultAction(Territory centre) {
        Orders order = new Orders();
                
        order.addAction(new AddMinionAction(centre, 1, Team.ORCS));
        order.addAction(new AddMinionAction(centre, 1, Team.DRAGONS));
        order.addAction(new AddMinionAction(centre, 1, Team.DEMONS));
        order.addAction(new AddMinionAction(centre, 1, Team.UNDEAD));
        
        return order;
    }
    
    private Orders allQuiet() {
        return new Orders();
    }

}
