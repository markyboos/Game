
package com.game.thrones.engine;

import com.game.thrones.model.PieceCriteria;
import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Minion;
import com.game.thrones.model.piece.Piece;
import java.util.List;

/**
 *
 * @author James
 */
public class BarbarianAttackAction extends AttackAction {
    
    private int extraAttacks;
    
    public BarbarianAttackAction(Hero barbarian) {
        super(barbarian);
        
    }

    public void setExtraAttacks(int amount) {
        extraAttacks = amount;
    }
    
    @Override
    protected void execute(int killed) {
        
        if (killed >= 2) {
            //remove 1 piece from all surrounding territories
            
            List<Territory> bordering = GameController.getInstance().getBoard()
                    .getBorderingTerritories(attackingTerritory);
            
            for (Territory border : bordering) {
                PieceCriteria criteria = new PieceCriteria();
                criteria.setClass(Minion.class);
                criteria.setTerritory(border);
                
                List<Piece> pieces = GameController.getInstance().getBoard().getPieces(criteria);
                
                if (pieces.isEmpty()) {
                    continue;
                }
                
                GameController.getInstance().getBoard().removePiece(pieces.get(0));
            }
        }
        
        for (int i = 0; i < extraAttacks; i ++) {        
            piece.useAction();
        }
    }
    
    @Override
    protected int modifyAttack() {
        return super.modifyAttack() + extraAttacks;        
    }
    
    

}
