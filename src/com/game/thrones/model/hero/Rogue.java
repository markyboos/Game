
package com.game.thrones.model.hero;

import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class Rogue extends Hero {
    
    public Rogue() {
        super("Rogue", 6);
    }
    
    @Override
    protected int takeDamage(Minion minion) {
        return 0;
    }
    
    @Override
    public int itemsPerTurn() {
        if (getPosition().getOwner() == Team.OLD_DEMONS) {
            return 3;
        }
        
        return super.itemsPerTurn();
    }

}
