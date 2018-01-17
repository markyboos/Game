
package com.game.thrones.engine.actions;

import android.util.Log;
import com.game.thrones.engine.GameController;
import com.game.thrones.engine.descriptions.ActionDescription;
import com.game.thrones.engine.descriptions.Describable;
import com.game.thrones.model.Board;
import com.game.thrones.model.PieceTerritoryFilter;
import com.game.thrones.model.Team;
import com.game.thrones.model.Territory;
import com.game.thrones.model.TerritoryCriteria;
import com.game.thrones.model.hero.Minion;
import java.util.List;

/**
 *
 * @author James
 */
public class ElvenArchersAction implements MultipleTerritorySelectAction, Describable<ActionDescription>{

    private List<Territory> territories;
            
    public void setTerritories(List<Territory> territories) {
        this.territories = territories;                
    }

    public int getTotal() {
        return 2;
    }

    public List<Territory> getOptions() {
        Board board = GameController.getInstance().getBoard();

        TerritoryCriteria criteria = new TerritoryCriteria();
        criteria.setOwner(Team.ORCS);

        return board.getTerritories(criteria);                                
    }
    
    public boolean chosenTerritory() {
        return territories != null;
    }

    public void execute() {

        Board board = GameController.getInstance().getBoard();
        
        Log.d("Elven archers", "executing...");

        for (Territory territory : territories) {

            List<Minion> pieces = board.getPieces(new PieceTerritoryFilter<Minion>(territory), Minion.class);
            
            Log.d("Elven archers", "removing [" + pieces.size() + "] minions from [" + territory.getName() + "]");

            for (Minion minion : pieces) {
                board.removePiece(minion);
            }                    
        }                
    }

    public ActionDescription summary() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String render() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    

}
