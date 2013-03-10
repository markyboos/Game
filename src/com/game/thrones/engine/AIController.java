
package com.game.thrones.engine;

import com.game.thrones.model.House;
import com.game.thrones.model.Standing;
import com.game.thrones.model.piece.Piece;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * In charge of AI moves.
 *
 * @author James
 */
public class AIController {
    
    private GameController controller;
    
    private ActionCreator actionCreator = new ActionCreator();
    
    public void takeTurn(final House house) {
        
        controller = GameController.getInstance();
        
        Map<House, Standing> standings = house.getHouseStandings();
        
        //generate possible moves        
        Set<Piece> pieces = controller.getBoard().getPieces(house);
        
        Orders orders = controller.getOrders();
        
        for (Piece piece : pieces) {
            List<Action> actions = actionCreator.createActions(piece);
            
            orders.addAction(chooseBestAction(actions));
        }
    }
    
    //todo    
    private Action chooseBestAction(final List<Action> actions) {
        Action best = null;
        
        for (Action action : actions) {
            
            if (best == null) {
                best = action;
            } else if (action instanceof RecruitAction) {
                best = action;
            }            
        }
        
        return best;
    }

}
