
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
            
            if (isSlayer(minion) || 
                    getRoll(minion) >= minion.getRollToDamage()) {
                //remove the minion
                GameController.getInstance().getBoard().removePiece(minion);
                killed ++;
            }
        }
        
        execute(killed);
        
        piece.finishAttack();
    }
    
    private int getRoll(Minion minion) {
        return dice.roll() + modifyAttack(minion);
    }
    
    protected void execute(int killed) {}
    
    protected int modifyAttack(Minion minion) {
        return piece.modifyAttack(minion);
    }
    
    protected boolean isSlayer(Minion minion) {
        return piece.isSlayer(minion.getTeam());
    }
    
    @Override
    public String toString() {
        return "Attack the minions";
    }
    
}
