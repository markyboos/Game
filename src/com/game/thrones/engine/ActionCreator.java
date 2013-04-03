
package com.game.thrones.engine;

import com.game.thrones.model.PieceCriteria;
import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.General;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Minion;
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
        
        controller = GameController.getInstance();
         
        //generic actions
        List<Action> actions = createMoveActions(piece);
        
        PieceCriteria criteria = new PieceCriteria();
        criteria.setClass(Minion.class);
        criteria.setTerritory(piece.getPosition());
        
        boolean minionsAtHero = !controller.getBoard().getPieces(criteria).isEmpty();
        
        criteria.setClass(General.class);
        
        List<Piece> pieces = controller.getBoard().getPieces(criteria);
        
        boolean generalAtHero = !pieces.isEmpty();
        
        if (piece instanceof Hero) {
            
            if (minionsAtHero) {
                actions.add(new AttackAction(piece));
            }
            
            if (generalAtHero) {
                actions.add(new AttackGeneralAction(piece, (General)pieces.get(0)));
            }
            
            Territory position = piece.getPosition();
            
            if (!minionsAtHero && !generalAtHero) {
                actions.add(new HealAction(piece));
            }
            
            if (position.getTainted() > 0) {
                actions.add(new CleanseAction(piece));
            }
            
            //this should be at the an inn
            if (position.getName().equals(Territory.KINGS_LANDING)) {            
                actions.add(new RumorsAction(piece));
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
        }
        
        return actions;
    }

}
