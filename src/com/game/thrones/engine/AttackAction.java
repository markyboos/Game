
package com.game.thrones.engine;

import com.game.thrones.model.PieceCriteria;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Minion;
import com.game.thrones.model.piece.Piece;
import java.util.List;

/**
 *
 * @author James
 */
class AttackAction extends AbstractAction {
    

    public AttackAction(final Piece piece) {
        super(piece);
        
        if (!(piece instanceof Hero)) {
            throw new IllegalArgumentException("Needs to be a knight piece");                            
        }
    }
    
    private Dice dice = new Dice();

    public void execute() {
        
        PieceCriteria criteria = new PieceCriteria<Minion>();
        criteria.setTerritory(piece.getPosition());
        criteria.setClass(Minion.class);
        
        List<Piece> minions = GameController.getInstance().getBoard().getPieces(criteria);
                
        for (Piece piece : minions) {
            
            Minion minion = (Minion) piece;
            
            //roll the dice
            int roll = dice.roll();
            
            if (roll > minion.getRollToDamage()) {
                //remove the minion
                GameController.getInstance().getBoard().removePiece(minion);
            }
        }
    }
    
    @Override
    public String toString() {
        return "Attack the minions";
    }

}
