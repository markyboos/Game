
package com.game.thrones.engine;

import android.util.Log;
import com.game.thrones.model.House;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author James
 */
public class Orders {
    
    private Map<House, Set<Action>> houseActions;
    
    public Orders() {
        houseActions = new HashMap<House, Set<Action>>();
    }
    
    public void addAction(House house, Action action) {
        if (houseActions.containsKey(house)) {
            houseActions.get(house).add(action);
        } else {
            Set<Action> actions = new HashSet<Action>();
            actions.add(action);
            houseActions.put(house, actions);
        }
    }

    public Set<Action> getActions(House house) {
        //Log.d("orders", "house actions:" + houseActions.get(house));
        return houseActions.containsKey(house) ? houseActions.get(house) : Collections.<Action>emptySet();
    }

    public Set<Action> getOrderedActions() {
        
        Set<Action> actions = new TreeSet<Action>();
        
        for (Set<Action> houseAction : houseActions.values()) {
            if (houseAction == null) {
                continue;
            }
            
            actions.addAll(houseAction);            
        }
        
        return Collections.unmodifiableSet(actions);
    }

}
