
package com.game.thrones.engine.actions;

import com.game.thrones.model.Territory;
import java.util.List;

/**
 *
 * @author James
 */
public interface MultipleTerritorySelectAction extends TerritorySelectAction {
    
    void setTerritories(List<Territory> territory);
    
    int getTotal();

}
