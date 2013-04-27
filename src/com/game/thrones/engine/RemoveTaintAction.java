
package com.game.thrones.engine;

import com.game.thrones.model.Territory;

/**
 *
 * @author James
 */
public class RemoveTaintAction implements TerritorySelectAction {
    
    private Territory territory;
    
    public void setTerritory(Territory territory) {
        this.territory = territory;
    }    

    public void execute() {
        territory.removeTaint();
    }

}
