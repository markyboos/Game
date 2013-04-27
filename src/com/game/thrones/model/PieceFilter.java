
package com.game.thrones.model;

import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public interface PieceFilter<P extends Piece> {
    
    boolean valid(P piece);

}
