
package com.game.thrones.engine;

import com.game.thrones.activity.CameraChangeEvent;
import com.game.thrones.model.Board;
import com.game.thrones.model.Team;
import com.game.thrones.model.Territory;
import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public class AddMinionAction implements Action {
    
    private Territory territory;
    
    private int number;
    
    private Team team;
    
    public AddMinionAction(Territory territory, int number, Team team) {
        this.territory = territory;
        this.number = number;  
        this.team = team;
    }
    
    public void execute() {
        
        final Board board = GameController.getInstance().getBoard();
        
        GameController.getInstance().fireCameraChangeEvent(new CameraChangeEvent(territory));
        
        for (int i = 0; i < number; i++) {            
            board.addMinionToTerritory(territory, team, true);
        }
    }

    public Piece getPiece() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Integer executionStep() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
