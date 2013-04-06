
package com.game.thrones.engine;

import com.game.thrones.model.House;
import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.General;
import com.game.thrones.model.piece.Piece;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * In charge of AI moves.
 *
 * @author James
 */
public class AIController {
    
    private GameController controller;
    
    private List<Orders> evilActions;
    
    public void takeTurn() {
        
        if (evilActions == null) {
            evilActions = createEvilActions();
        }
        
        //shuffle and pick one
        Collections.shuffle(evilActions);
        
        Orders orders = evilActions.get(0);
        
        //add minions to places        
        //add taint        
        //possibly move general
        for (Action action : orders.getOrderedActions()) {
            action.execute();            
        }
        
    }
    
    private List<Orders> createEvilActions() {
        
        controller = GameController.getInstance();
        
        List<Orders> orders = new ArrayList<Orders>();
        final Territory centralTerritory = controller.getBoard().getCentralTerritory();
        
        for (Territory t : controller.getBoard().getTerritories()) {
            if (t.equals(centralTerritory)) {
                continue;
            }
            Orders order = new Orders();
            order.addAction(House.NO_ONE, new AddMinionAction(t, 2));
            order.addAction(House.NO_ONE, new AddMinionAction(t, 1));            
            
            orders.add(order);
        }
        
        Piece general = controller.getBoard().getPiece(General.FATTY);
        
        Set<Territory> path = controller.getBoard()
                .getPathToTerritory(general.getPosition(), centralTerritory);
        
        for (Territory t : path) {
            Orders order = new Orders();
            order.addAction(House.NO_ONE, new MoveAction(controller.getBoard().getPiece(General.FATTY), t));

            orders.add(order);
        }
        
        for (int i = 0; i < 2; i ++) {
            
            Orders order = new Orders();
            order.addAction(House.NO_ONE, new MoveAlongPathAction(controller.getBoard().getPiece(General.FATTY), centralTerritory, 1));
            
            orders.add(order);
        }
        
        return orders;
        
    }

}
