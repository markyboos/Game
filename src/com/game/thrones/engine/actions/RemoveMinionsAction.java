package com.game.thrones.engine.actions;

import android.util.Log;
import com.game.thrones.engine.GameController;
import com.game.thrones.engine.descriptions.ActionDescription;
import com.game.thrones.engine.descriptions.Describable;
import com.game.thrones.model.Board;
import com.game.thrones.model.PieceTerritoryFilter;
import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.Minion;

/**
 * Removes minions around a territory.
 *
 * @author James
 */
public class RemoveMinionsAction implements Action, 
        Describable<RemoveMinionsAction>, ActionDescription {

    int totalToRemove;
    Territory territory;
    int distanceAway;
    
    public RemoveMinionsAction(Territory territory, int distanceAway, int totalToRemove) {
        this.territory = territory;
        this.distanceAway = distanceAway;
        this.totalToRemove = totalToRemove;
    }

    public void execute() {
        int total = totalToRemove;

        Log.d("QuestController", "collection mc cormic reward killed:[" + total + "]");

        Board board = GameController.getInstance().getBoard();

        for (Territory away : board.getAllTerritoriesDistanceAway(territory, distanceAway)) {
            if (removePieces(away, total)) {
                return;
            }
        }
    }

    private boolean removePieces(Territory territory, int total) {

        Board board = GameController.getInstance().getBoard();
        for (Minion minion : board.getPieces(new PieceTerritoryFilter<Minion>(territory), Minion.class)) {
            total--;
            board.removePiece(minion);
            if (total == 0) {
                return true;
            }
        }
        return false;
    }

    public RemoveMinionsAction summary() {
        return this;
    }

    public String render() {
        return totalToRemove + " minions were killed around " + territory;
    }
}
