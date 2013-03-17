
package com.game.thrones.engine;

import com.game.thrones.model.piece.IEmissary;
import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public class AssassinateActivity extends AbstractAction {
    
    private Piece target;
    
    public AssassinateActivity(final Piece piece, final Piece target) {
        super(piece, ASSASSINATE_ACTION);
        
        this.target = target;
        
        if (!(piece instanceof IEmissary)) {
            throw new IllegalArgumentException("Should be emissary piece");
        }
    }

    public void execute() {
       //target
       //target.
    }
    
    @Override
    public String toString() {
        return "Assasinate " + target.getName(); 
    }

}
