
package com.game.thrones.engine;

import com.game.thrones.model.Team;
import com.game.thrones.model.Territory;
import com.game.thrones.model.TerritoryCriteria;
import java.util.List;

/**
 *
 * @author James
 */
public class OrcPatrolsAction implements Action {
    
    private TerritoryCriteria criteria;

    public OrcPatrolsAction(final TerritoryCriteria criteria) {
        this.criteria =criteria;
    }

    public void execute() {
        
        List<Territory> territories = GameController.getInstance().
                getBoard().getTerritories(criteria);
        
        for (Territory territory : territories) {
            GameController.getInstance().
                getBoard().addMinionToTerritory(territory, Team.ORCS, true);
        }
    }

}
