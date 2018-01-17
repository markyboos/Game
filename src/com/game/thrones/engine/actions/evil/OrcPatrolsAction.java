
package com.game.thrones.engine.actions.evil;

import com.game.thrones.engine.GameController;
import com.game.thrones.engine.actions.Action;
import com.game.thrones.engine.descriptions.AttackDescription;
import com.game.thrones.engine.descriptions.Describable;
import com.game.thrones.model.Team;
import com.game.thrones.model.Territory;
import com.game.thrones.model.TerritoryCriteria;
import java.util.List;

/**
 *
 * @author James
 */
public class OrcPatrolsAction implements EvilAction, Describable<AttackDescription> {
    
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

    @Override
    public AttackDescription summary() {
        return null;
    }

    @Override
    public String render() {
        return new OrcPatrolsRenderer().render();
    }

    public class OrcPatrolsRenderer {

        public String render() {
            return "Orc patrols start where there are only " + criteria.getMinionCount() + " minions";
        }

    }
}
