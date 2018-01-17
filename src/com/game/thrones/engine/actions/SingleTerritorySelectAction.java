
package com.game.thrones.engine.actions;

import com.game.thrones.model.Territory;

/**
 *
 * @author James
 */
public interface SingleTerritorySelectAction extends TerritorySelectAction {
    
    void setTerritory(Territory territory);

}
