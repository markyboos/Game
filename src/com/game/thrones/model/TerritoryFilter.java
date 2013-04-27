
package com.game.thrones.model;

import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public class TerritoryFilter implements PieceFilter {
    
    private Territory territory;
    
    public TerritoryFilter(Territory territory) {
        this.territory = territory;        
    }

    public boolean valid(Piece piece) {
        return piece.getPosition().equals(territory);
    }
    
    
}
