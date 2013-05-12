
package com.game.thrones.model.hero;

import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class Barbarian extends Hero {
    
    public Barbarian() {
        super("Barbarian");
    }
    
    @Override
    public int itemsPerTurn() {
        if (getPosition().getOwner() == Team.NO_ONE 
                || getPosition().getOwner() == Team.UNDEAD) {
            return 3;
        }
        
        return super.itemsPerTurn();
    }

}
