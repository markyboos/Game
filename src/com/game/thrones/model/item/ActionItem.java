
package com.game.thrones.model.item;

import com.game.thrones.engine.actions.Action;
import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class ActionItem extends AbstractItem {
    
    private final Action action;
    
    public ActionItem(final String name, final String description, final Action action) {
        super(name, description);
        this.action = action;
    }

    public Action getAction() {
        return action;
    }
}
