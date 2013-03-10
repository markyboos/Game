
package com.game.thrones.engine;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author James
 */
public class Orders {
    
    private Set<Action> actions;
    
    public Orders() {
        actions = new HashSet<Action>();
    }
    
    public void addAction(Action action) {
        actions.add(action);
    }

    public Set<Action> getActions() {
        return actions;
    }

}
