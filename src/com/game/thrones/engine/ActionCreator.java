
package com.game.thrones.engine;

import com.game.thrones.model.Territory;
import com.game.thrones.model.piece.IKnight;
import com.game.thrones.model.piece.Piece;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates actions for pieces
 *
 * @author James
 */
public class ActionCreator {
    
    GameController controller;
    
    public List<Action> createActions(final Piece piece) {
         
        //generic actions
        List<Action> actions = createMoveActions(piece);
        
        actions.add(new DoNothingAction(piece));
        
        //piece specific actions
        if (piece instanceof IKnight) {
            actions.add(new RecruitAction(piece));
            actions.add(new DisbandAction(piece));
        }
        
        return actions;
        
    }
    
    private List<Action> createMoveActions(final Piece piece) {
        
        controller = GameController.getInstance();
        //all possible moves  
        List<Territory> territories = controller.getBoard().getBorderingTerritories(piece.getPosition());
           
        List<Action> actions = new ArrayList<Action>();
        
        for (Territory territory : territories) {
            actions.add(new MoveAction(piece, territory));
        }
        
        return actions;
    }

}
