
package com.game.thrones.engine;

import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public abstract class AbstractAction implements Action {
    
    protected Piece piece;

    public AbstractAction(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof AbstractAction) {
            AbstractAction action = (AbstractAction)obj;
            return piece.equals(action.getPiece());
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.piece != null ? this.piece.hashCode() : 0);
        return hash;
    }

}
