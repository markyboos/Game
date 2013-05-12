
package com.game.thrones.engine.actions;

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
}
