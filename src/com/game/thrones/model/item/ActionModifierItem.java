
package com.game.thrones.model.item;

import com.game.thrones.model.Team;

/**
 * todo. poop.
 *
 * @author James
 */
public class ActionModifierItem extends AbstractItem {
    
    private final int extraActions;
    
    public ActionModifierItem(String name, String description, int extraActions) {
        super(name, description);
        
        this.extraActions = extraActions;
    }
    
    public int getExtraActions() {
        return extraActions;
    }
}
