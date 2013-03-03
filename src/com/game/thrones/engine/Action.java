
package com.game.thrones.engine;

import com.game.thrones.model.piece.Piece;

/**
 * A possible action for a piece
 *
 * @author James
 */
public interface Action {
    
    void execute();
    
    Piece getPiece();
    
}
