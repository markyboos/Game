
package com.game.thrones.engine.actions;

import com.game.thrones.engine.GameController;
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
    private Team team;

    public OrcPatrolsAction(final TerritoryCriteria criteria, Team team) {
        this.criteria = criteria;
        this.team = team;
    }

    public void execute() {
        
        List<Territory> territories = GameController.getInstance().
                getBoard().getTerritories(criteria);
        
        for (Territory territory : territories) {
            GameController.getInstance().
                getBoard().addMinionToTerritory(territory, team, true);
        }
    }

}
