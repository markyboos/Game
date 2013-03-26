
package com.game.thrones.engine;

import com.game.thrones.model.House;
import com.game.thrones.model.hero.General;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        
        for (int i = 0; i < 30; i ++) {
            Orders order = new Orders();
            order.addAction(House.NO_ONE, new AddMinionAction(controller.getBoard().getRandomTerritory(), 2));
            order.addAction(House.NO_ONE, new AddMinionAction(controller.getBoard().getRandomTerritory(), 1));            
            
            orders.add(order);            
        }
        
        for (int i = 0; i < 5; i ++) {
            Orders order = new Orders();
            order.addAction(House.NO_ONE, new MoveAction(controller.getBoard().getPiece(General.FATTY), controller.getBoard().getCentralTerritory()));

            orders.add(order);
        }      
        
        return orders;
        
    }

}
