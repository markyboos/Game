
package com.game.thrones.engine;

import com.game.thrones.model.House;
import com.game.thrones.model.Standing;
import com.game.thrones.model.piece.Piece;
import java.util.List;
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
        
        //generate possible moves        
        Set<Piece> pieces = controller.getBoard().getPieces(house);
        
        Orders orders = controller.getOrders();
        
        for (Piece piece : pieces) {
            List<Action> actions = actionCreator.createActions(piece);
            
            orders.addAction(house, chooseBestAction(house, actions));
        }
    }
    
    //todo    
    private Action chooseBestAction(final House house, final List<Action> actions) {
        Action best = null;
        
        boolean atWar = false;
        
        for (House opposingHouse : controller.getBoard().getHouses()) {
            if (opposingHouse.equals(house)) {
                continue;
            }
            Standing standing = house.getHouseStandings().get(opposingHouse);
            
            if (standing.atWar()) {
                atWar = true;                
            }
        }
        
        int score = 0;
        
        for (Action action : actions) {
            
            if (best == null) {
                best = action;
            } else if (atWar && score < 5 && action instanceof AttackAction) {
                best = action;
                score = 5;
            } else if (atWar && score < 2 && action instanceof RecruitAction) {
                best = action;
                score = 2;
            }
        }
        
        return best;
    }
    
    public void updateStandingBasedOnAction(final House house, final House other) {
        
        controller = GameController.getInstance();
        
        //if other players do whats asked of them thats good
        
        //if they give you money thats good
        
        //if nothing happens thats good
        
        //if they attack you thats bad
        
        //if they are aggressive towards you thats bad
        
        //if they are less aggressive thats good
        
        Standing standing = house.getHouseStandings().get(other);
        
        for (Action action : controller.getOrders().getActions(other)) {
        
            if (action instanceof MoveAction) {
                MoveAction mAction = (MoveAction)action;
                if (controller.getBoard().getAlliedTerritories(house).contains(mAction.getMovingTo())) {
                    standing.didBadThing();
                } else {
                    standing.didGoodThing();
                }
            } else if (action instanceof AttackAction) {
                standing.ruined();
            } else if (action instanceof RecruitAction) {
                standing.didBadThing();
            } else if (action instanceof DoNothingAction) {
                standing.didGoodThing();
            } else if (action instanceof DisbandAction) {
                standing.didGoodThing();
            }
        }
    }

}
