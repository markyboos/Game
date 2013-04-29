
package com.game.thrones.engine.descriptions;

/**
 *
 * @author James
 */
public interface Describable<D extends ActionDescription> {
    
    /**
     * Gives a summary of what the action did.
     * Can only be called after execute.
     * 
     * @return an action description.
     */    
    D summary();
    
    /**
     * Renders this action to a string.
     * @return 
     */    
    String render();

}
