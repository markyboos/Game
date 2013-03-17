
package com.game.thrones.engine;

import com.game.thrones.model.Territory;
import com.game.thrones.model.piece.IEmissary;
import com.game.thrones.model.piece.IKnight;
import com.game.thrones.model.piece.Piece;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Creates actions for pieces
 *
 * @author James
 */
public class ActionCreator {
    
    GameController controller;
    
    public List<Action> createActions(final Piece piece) {
        
        controller = GameController.getInstance();
        
        if (piece.isPrisoner()) {
            return Collections.<Action>singletonList(new DoNothingAction(piece));
        }
         
        //generic actions
        List<Action> actions = createMoveActions(piece);
        
        actions.add(new DoNothingAction(piece));
        
        //piece specific actions
        if (piece instanceof IKnight) {
            actions.add(new RecruitAction(piece));
            actions.add(new DisbandAction(piece));
            actions.add(new FortifyAction(piece));
        }
        
        if (piece instanceof IEmissary) {
            actions.add(new PersuadeAction(piece));
            
            for (Piece target : controller.getBoard().getPieces(piece.getPosition())) {
                if (!target.equals(piece)) {
                    actions.add(new AssassinateActivity(piece, target)); 
                }
            }            
        }
        
        return actions;
        
    }
    
    private List<Action> createMoveActions(final Piece piece) {
                
        //all possible moves  
        List<Territory> territories = controller.getBoard().getBorderingTerritories(piece.getPosition());
           
        List<Action> actions = new ArrayList<Action>();
        
        for (Territory territory : territories) {
            actions.add(new MoveAction(piece, territory));
            
            if (piece instanceof IKnight) { 
                    //!controller.getBoard()
            //    .getAlliedTerritories(piece.getHouse()).contains(territory)) { 
            
                //can you attack allies?
                actions.add(new AttackAction(piece, territory));            
            
            }
        }
        
        return actions;
    }

}
