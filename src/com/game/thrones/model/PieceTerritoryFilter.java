
package com.game.thrones.model;

import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public class PieceTerritoryFilter<P extends Piece> implements Filter<P> {
    
    private Territory territory;
    
    public PieceTerritoryFilter(Territory territory) {
        this.territory = territory;        
    }

    public boolean valid(Piece piece) {
        return piece.getPosition().equals(territory);
    }
    
    
}
