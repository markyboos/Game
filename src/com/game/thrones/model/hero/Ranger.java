
package com.game.thrones.model.hero;

import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class Ranger extends Hero {
    
    public Ranger() {
        super("Ranger");
    }
    
    @Override
    public int modifyAttack() {
        
        if (getPosition().getOwner() == Team.ORCS) {
            return 1;
        }
        
        return 0;
    }
    
    @Override
    public void modifyActions() {
        
        if (getPosition().getOwner() == Team.ORCS) {
            actionsAvailable += 1;       
        }        
    }

}
