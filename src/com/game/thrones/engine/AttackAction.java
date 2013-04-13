
package com.game.thrones.engine;

import com.game.thrones.model.PieceCriteria;
import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.Barbarian;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Minion;
import com.game.thrones.model.piece.Piece;
import java.util.List;

/**
 *
 * @author James
 */
public class AttackAction extends AbstractAction<Hero> {
    
    protected Territory attackingTerritory;

    public AttackAction(final Hero piece) {
        super(piece);
        
        attackingTerritory = piece.getPosition();
    }
    
    private Dice dice = new Dice();

    public void execute() {
        
        PieceCriteria criteria = new PieceCriteria<Minion>();
        criteria.setTerritory(attackingTerritory);
        criteria.setClass(Minion.class);
        
        List<Piece> minions = GameController.getInstance().getBoard().getPieces(criteria);
        
        int killed = 0;
        
        for (Piece minionPiece : minions) {
            
            Minion minion = (Minion) minionPiece;
            
            //roll the dice
            int roll = dice.roll() + modifyAttack();
            
            if (roll > minion.getRollToDamage()) {
                //remove the minion
                GameController.getInstance().getBoard().removePiece(minion);
                killed ++;
            }
        }
        
        if (killed >= 2 && piece instanceof Barbarian) {
            //remove 1 piece from all surrounding territories
            
            List<Territory> bordering = GameController.getInstance().getBoard()
                    .getBorderingTerritories(attackingTerritory);
            
            for (Territory border : bordering) {
                criteria = new PieceCriteria();
                criteria.setClass(Minion.class);
                criteria.setTerritory(border);
                
                List<Piece> pieces = GameController.getInstance().getBoard().getPieces(criteria);
                
                if (pieces.isEmpty()) {
                    continue;
                }
                
                GameController.getInstance().getBoard().removePiece(pieces.get(0));
            }
            
        }
    }
    
    protected int modifyAttack() {
        return piece.modifyAttack();
    }
    
    @Override
    public String toString() {
        return "Attack the minions";
    }

}
