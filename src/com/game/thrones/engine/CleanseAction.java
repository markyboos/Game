
package com.game.thrones.engine;

import com.game.thrones.model.Territory;
import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public class CleanseAction extends AbstractAction {
    
    final Territory position;
    
    public CleanseAction(final Piece piece) {
        super(piece);
        
        position = piece.getPosition();
    }
    
    Dice dice = new Dice();

    public void execute() {
        
        //use a card
        
        if (dice.roll() > 5 || dice.roll() > 5) {
            position.removeTaint();                        
        }
    }

}
