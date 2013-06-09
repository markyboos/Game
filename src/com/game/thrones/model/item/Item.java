
package com.game.thrones.model.item;

import com.game.thrones.engine.descriptions.ActionDescription;

/**
 *
 * @author James
 */
public interface Item extends CharSequence, ActionDescription{
    
    public String getDescription();
    
    public String getName();

}
