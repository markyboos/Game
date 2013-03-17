
package com.game.thrones.engine;

import com.game.thrones.model.piece.IEmissary;
import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public class PersuadeAction extends AbstractAction {
    
    public PersuadeAction(final Piece piece) {
        super(piece, PERSUADE_ACTION);
        
        if (!(piece instanceof IEmissary)) {
            throw new IllegalArgumentException("Only emissaries can persuade houses");
        }
    }

    //works every time!
    public void execute() {
        piece.getPosition().getOwner().setServes(piece.getHouse());
    }
    
    @Override
    public String toString() {
        return "Persuade " + piece.getPosition().getOwner().getName() + " to fight for you";
    }

}
