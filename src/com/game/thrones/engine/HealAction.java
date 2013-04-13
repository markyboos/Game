
package com.game.thrones.engine;

import com.game.thrones.model.hero.Hero;

/**
 *
 * @author James
 */
public class HealAction extends AbstractAction<Hero> {
    
    public HealAction(final Hero piece) {
        super(piece);        
    }

    public void execute() {        
        piece.heal();
    }
    
    @Override
    public String toString() {
        return "Heal yourself";
    }

}
