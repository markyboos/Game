
package com.game.thrones.engine;

import com.game.thrones.activity.CameraChangeEvent;
import com.game.thrones.model.Board;
import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.General;
import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public class MoveAction extends AbstractAction {
    
    private Territory territory;
    
    /**
     * @param territory where they are going
     * @param piece the piece that wants to move
     */
    public MoveAction(final Piece piece, final Territory territory) {
        super(piece);
        this.territory = territory;
    }
    
    public Territory getMovingTo() {
        return territory;
    }

    public void execute() {
        final Board board = GameController.getInstance().getBoard();
        
        //generals can never move away from the centre of the map
        if (piece instanceof General) {
            if (!board.getPathToTerritory(piece.getPosition(), 
                    board.getCentralTerritory()).contains(territory)) {
                return;
            }                            
        }
        
        GameController.getInstance().fireCameraChangeEvent(new CameraChangeEvent(territory));
        
        board.movePiece(piece, territory);
    }
    
    @Override
    public String toString() {
        return "Move to " + territory.getName();
    }

}
