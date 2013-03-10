
package com.game.thrones.engine;

import com.game.thrones.model.piece.IKnight;
import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public class DisbandAction extends AbstractAction {
    
    public DisbandAction(final Piece piece) {
        super(piece);
        
        if (!(piece instanceof IKnight)) {
            throw new IllegalArgumentException("Needs to be a knight piece");                            
        }
    }

    
    public void execute() {
        IKnight knight = (IKnight)piece;
        knight.disband(1);
    }
    
    @Override
    public String toString() {
        return "Disband troops";
    }

}
