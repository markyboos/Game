
package com.game.thrones.engine;

import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Item;
import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public class RumorsAction extends AbstractAction { 
    
    public RumorsAction(final Piece piece) {
        super(piece);
    }

    public void execute() {
        
        Hero hero = (Hero)piece;
        
        hero.addItem(new Item(1));
    }
    
    @Override
    public String toString() {
        return "Listen to rumors";
    }

}
