
package com.game.thrones.engine.actions;

import com.game.thrones.model.Territory;
import java.util.List;

/**
 *
 * @author James
 */
public interface MultipleTerritorySelectAction extends TerritorySelectAction {
    
    public void setTerritories(List<Territory> territory);
    
    public int getTotal();

}
