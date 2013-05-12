
package com.game.thrones.model.hero;

import com.game.thrones.engine.actions.Action;
import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class ActionItem extends Item {
    
    private Action action;
    private String name;
    private String description;
    
    public ActionItem(final String name, final String description, final Action action) {
        this.team = Team.NO_ONE;
        this.type = ItemType.DISPOSABLE;        
        this.action = action;
        this.name = name;
        this.description = description;
    }

    public Action getAction() {
        return action;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ActionItem other = (ActionItem) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
    

}
