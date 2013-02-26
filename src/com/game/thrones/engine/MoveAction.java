
package com.game.thrones.engine;

import com.game.thrones.model.Territory;
import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public class MoveAction implements Action {
    
    private Territory territory;
    private Piece piece;
    
    /**
     * @param territory where they are going
     * @param piece the piece that wants to move
     */
    public MoveAction(final Piece piece, final Territory territory) {
        this.territory = territory;
        this.piece = piece;
    }
    

    public void execute() {
        GameController.getInstance()
                .getBoard().movePiece(piece, territory);
    }

    public String getDescription() {
        return "Move to " + territory.getName();
    }
    
    @Override
    public String toString() {
        return getDescription();
    }

}
