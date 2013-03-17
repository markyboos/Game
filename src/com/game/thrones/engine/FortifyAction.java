
package com.game.thrones.engine;

import com.game.thrones.model.piece.IKnight;
import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public class FortifyAction extends AbstractAction {
    
    public FortifyAction(final Piece piece) {
        super(piece, FORTIFY_ACTION);
        
        if (!(piece instanceof IKnight)) {
            throw new IllegalArgumentException("Must be a knight");
        }
    }

    public void execute() {
        //if any warring houses move through this tile
        //then this unit will attack them
        
        IKnight knight = (IKnight)piece;
        knight.fortify();
    }
    
    @Override
    public String toString() {
        return "Fortify";
    }

}
