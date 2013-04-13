
package com.game.thrones.engine;

import com.game.thrones.model.piece.Piece;

/**
 * An action for a specific piece.
 *
 * @author James
 */
public interface PieceAction<P extends Piece> extends Action {
    
    /**
     * Get the piece this action is for
     * 
     * @return 
     */
    P getPiece();

}
