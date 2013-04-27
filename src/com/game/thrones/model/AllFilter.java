
package com.game.thrones.model;

import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public class AllFilter implements PieceFilter {
    
    public static final AllFilter INSTANCE = new AllFilter();

    public boolean valid(Piece piece) {
        return true;
    }
}
