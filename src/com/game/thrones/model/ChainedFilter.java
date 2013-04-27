
package com.game.thrones.model;

import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public class ChainedFilter implements PieceFilter {
    
    private PieceFilter[] filters;
    
    public ChainedFilter(PieceFilter... filters) {
        this.filters = filters;                
    }

    public boolean valid(Piece piece) {
        for (PieceFilter filter : filters) {
            if (!filter.valid(piece)) {
                return false;
            }
        }
        
        return true;
    }

}
