
package com.game.thrones.engine;

import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public class HealAction extends AbstractAction {
    
    public HealAction(final Piece piece) {
        super(piece);        
    }

    public void execute() {
        Hero hero = (Hero)piece;
        
        hero.heal();
    }

}
