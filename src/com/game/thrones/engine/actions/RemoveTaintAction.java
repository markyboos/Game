
package com.game.thrones.engine.actions;

import com.game.thrones.model.Territory;

/**
 *
 * @author James
 */
public class RemoveTaintAction implements TerritorySelectAction {
    
    private Territory territory;
    
    private boolean removeAll;
    
    public RemoveTaintAction(boolean removeAll) {
        this.removeAll = removeAll;
    }
    
    public void setTerritory(Territory territory) {
        this.territory = territory;
    }    

    public void execute() {
        if (removeAll) {
            territory.removeAllTaint();
        }
        
        territory.removeTaint();
    }

}
