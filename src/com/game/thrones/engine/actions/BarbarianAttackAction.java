
package com.game.thrones.engine.actions;

import com.game.thrones.engine.GameController;
import com.game.thrones.model.PieceTerritoryFilter;
import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.Barbarian;
import com.game.thrones.model.hero.Minion;
import java.util.List;

/**
 *
 * @author James
 */
public class BarbarianAttackAction extends AttackAction<Barbarian> {
    
    private int extraAttacks;
    
    public BarbarianAttackAction(Barbarian barbarian) {
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
                List<Minion> pieces = GameController.getInstance().getBoard().getPieces(
                        new PieceTerritoryFilter(border), Minion.class);
                
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
    protected int modifyAttack(Minion minion) {
        return super.modifyAttack(minion) + extraAttacks;        
    }
    
    

}
