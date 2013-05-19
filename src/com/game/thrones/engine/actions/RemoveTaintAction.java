
package com.game.thrones.engine.actions;

import com.game.thrones.engine.GameController;
import com.game.thrones.model.Territory;
import com.game.thrones.model.TerritoryCriteria;
import java.util.List;

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
    
    public List<Territory> getOptions() {
        TerritoryCriteria criteria = new TerritoryCriteria();
        criteria.setTainted();
        
        return GameController.getInstance().getBoard().getTerritories(criteria);
    }

    public void execute() {
        if (removeAll) {
            territory.removeAllTaint();
        }
        
        territory.removeTaint();
    }

    public boolean chosenTerritory() {
        return territory != null;
    }
}
