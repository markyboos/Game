
package com.game.thrones.engine.actions.evil;

import android.util.Log;
import com.game.thrones.activity.CameraChangeEvent;
import com.game.thrones.engine.GameController;
import com.game.thrones.engine.actions.Action;
import com.game.thrones.engine.descriptions.ActionDescription;
import com.game.thrones.engine.descriptions.Describable;
import com.game.thrones.model.Board;
import com.game.thrones.model.Team;
import com.game.thrones.model.Territory;

/**
 *
 * @author James
 */
public class AddMinionAction implements Action, Describable {
    
    private Territory territory;
    
    private int number;
    
    private Team team;
    
    public AddMinionAction(Territory territory, int number, Team team) {
        
        if (team == Team.NO_ONE) {
            throw new AssertionError("add minion action must have a valid minion team was [" + team + "]");
        }
        this.territory = territory;
        this.number = number;  
        this.team = team;
    }
    
    public Territory getTerritory() {
        return territory;
    }
    
    public void execute() {
        
        final Board board = GameController.getInstance().getBoard();
        
        GameController.getInstance().fireCameraChangeEvent(new CameraChangeEvent(territory));
        
        for (int i = 0; i < number; i++) {
            Log.d("Add Minion To territory", "[" + territory + "] [" + team + "]");
            board.addMinionToTerritory(territory, team, true);
        }
    }

    @Override
    public ActionDescription summary() {
        return null;
    }

    @Override
    public String render() {
        return new AddMinionDescriptionRenderer().render();
    }

    public class AddMinionDescriptionRenderer {

        public String render() {
            StringBuilder buf = new StringBuilder();

            buf.append(number + " " + team + " minions were added at " + territory);

            return buf.toString();
        }
    }
}
