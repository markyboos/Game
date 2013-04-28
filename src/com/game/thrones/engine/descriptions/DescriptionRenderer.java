
package com.game.thrones.engine.descriptions;

/**
 * Describes an action.
 *
 * @author James
 */
public interface DescriptionRenderer <M extends ActionDescription> {
    
    public String render(M model);

}
