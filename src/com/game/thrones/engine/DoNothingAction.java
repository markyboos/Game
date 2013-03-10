
package com.game.thrones.engine;

import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
class DoNothingAction extends AbstractAction {
    
    public DoNothingAction(final Piece piece) {
        super(piece);
    }

    public void execute() {}
    
    @Override
    public String toString() {
        return "Do nothing";
    }

}
