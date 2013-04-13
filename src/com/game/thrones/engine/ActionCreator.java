
package com.game.thrones.engine;

import com.game.thrones.model.PieceCriteria;
import com.game.thrones.model.Team;
import com.game.thrones.model.Territory;
import com.game.thrones.model.TerritoryCriteria;
import com.game.thrones.model.hero.Barbarian;
import com.game.thrones.model.hero.General;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Minion;
import com.game.thrones.model.hero.Ranger;
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
            
            Hero hero = (Hero)piece;
            
            if (minionsAtHero) {
                if (piece instanceof Barbarian) {
                    actions.add(new BarbarianAttackAction(hero));
                    
                } else {
                    actions.add(new AttackAction(hero));
                }
            }
            
            if (generalAtHero) {
                actions.add(new AttackGeneralAction(hero, (General)pieces.get(0)));
            }
            
            Territory position = piece.getPosition();
            
            if (!minionsAtHero && !generalAtHero && !hero.isAtMaxHealth()) {
                actions.add(new HealAction(hero));
            }
            
            if (position.getTainted() > 0) {
                actions.add(new CleanseAction(hero));
            }
            
            int listenedActions = 0;
            
            for (Action action : controller.getActionsTaken()) {
                if (action instanceof RumorsAction) {
                    listenedActions ++;
                }                
            }
                        
            if (listenedActions < 2 && position.getOwner() == Team.NO_ONE && 
                    !position.getName().equals(Territory.KINGS_LANDING)) {            
                actions.add(new RumorsAction(hero));
            }
            
            //hero specific actions            
            if (piece instanceof Ranger) {
                actions.addAll(createRangedAttackActions(hero));
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

    private List<Action> createRangedAttackActions(final Hero piece) {
        
        List<Action> actions = new ArrayList<Action>();
        
        TerritoryCriteria criteria = new TerritoryCriteria();
        criteria.setBordering(piece.getPosition());
        criteria.setMinionCountOperator(TerritoryCriteria.Operator.MORE_THAN);
        criteria.setMinionCount(0);
        
        List<Territory> territories = controller.getBoard().getTerritories(criteria);
        
        for (Territory territory : territories) {
            actions.add(new RangedAttackAction(piece, territory));
        }
        
        return actions;
    }

}
