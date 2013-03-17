
package com.game.thrones.engine;

import com.game.thrones.model.Territory;
import com.game.thrones.model.piece.IKnight;
import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
class AttackAction extends AbstractAction {
    
    final Territory target;

    public AttackAction(final Piece piece, final Territory target) {
        super(piece, ATTACK_ACTION);
        this.target = target;
        
        if (!(piece instanceof IKnight)) {
            throw new IllegalArgumentException("Needs to be a knight piece");                            
        }
    }

    public void execute() {
        
        GameController.getInstance().addAttackingPiece((IKnight)piece, target);
        
        //adds it to the list of things attacking that place
        //IKnight knight = (IKnight)piece;
        //double capability = knight.getCombatEffectiveness() * knight.getTroopSize();
    }
    
    @Override
    public String toString() {
        return "Attack " + target.getName();
    }

}
