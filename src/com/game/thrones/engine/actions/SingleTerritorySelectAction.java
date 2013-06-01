
package com.game.thrones.engine.actions;

import com.game.thrones.model.Territory;

/**
 *
 * @author James
 */
public interface SingleTerritorySelectAction extends TerritorySelectAction {
    
    public void setTerritory(Territory territory);

}
