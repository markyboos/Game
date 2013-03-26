
package com.game.thrones.engine;

import com.game.thrones.model.Territory;
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
        
        //moves will not work if an attacking piece is blocking the way
        GameController.getInstance()
                .getBoard().movePiece(piece, territory);
    }
    
    @Override
    public String toString() {
        return "Move to " + territory.getName();
    }

}
