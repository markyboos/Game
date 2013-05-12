
package com.game.thrones.model.hero;

import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class Rogue extends Hero {
    
    public Rogue() {
        super("Rogue");
        super.maxHealth = 6;
        super.health = 6;
    }
    
    @Override
    protected int takeDamage(Minion minion) {
        return 0;
    }
    
    @Override
    public int itemsPerTurn() {
        if (getPosition().getOwner() == Team.DEMONS) {
            return 3;
        }
        
        return super.itemsPerTurn();
    }

}
