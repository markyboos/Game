
package com.game.thrones.engine;

import com.game.thrones.model.piece.Piece;

/**
 * A possible action for a piece
 *
 * @author James
 */
public interface Action {
    
    /**
     * Executes this action.
     * Some actions (such as attacking) need to be resolved at a later stage, so this method will set
     * up the execution for the later steps.
     */
    void execute();
    
    /**
     * Get the piece this action is for
     * 
     * @return 
     */
    Piece getPiece();
    
    
    /**
     * Get the order that this action should be executed in.
     * i.e diplomatic moves (such as assassinations are completed before moves and attacks)
     * 
     * @return 
     */    
    Integer executionStep();
    
}
