
package com.game.thrones.engine.actions;

import com.game.thrones.model.Territory;
import java.util.List;

/**
 *
 * @author James
 */
public interface TerritorySelectAction extends Action {
    
    List<Territory> getOptions();
    
    boolean chosenTerritory();

}
