
package com.game.thrones.model.hero;

import com.game.thrones.engine.Action;
import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class ActionItem extends Item implements Action {
    
    private Action action;
    
    public ActionItem(final Action action) {
        this.team = Team.NO_ONE;
        this.type = ItemType.DISPOSABLE;        
        this.action = action;
    }

    public void execute() {
        action.execute();
    }
    
    

}
