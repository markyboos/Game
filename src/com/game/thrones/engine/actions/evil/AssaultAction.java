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
 * Created by James on 01/01/2018.
 */
public class AssaultAction implements Action, Describable {

    private Board board = GameController.getInstance().getBoard();

    public void execute() {

        final Territory central = board.getCentralTerritory();

        GameController.getInstance().fireCameraChangeEvent(new CameraChangeEvent(central));

        for (Team team : Team.values()) {
            if (team == Team.NO_ONE || !team.enabled()) {
                continue;
            }
            board.addMinionToTerritory(central, team, true);
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

            buf.append("The forces of evil join up to attack the capital! A minion of each team is placed in the center");

            return buf.toString();
        }
    }
}
