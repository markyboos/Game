
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
    public int modifyAttack(Minion minion) {        
        return modifyAttack();
    }
    
    @Override
    public int modifyAttack(General general) {
        return modifyAttack();
    }
    
    @Override
    public void modifyActions() {
        
        if (getPosition().getOwner() == Team.ORCS) {
            actionsAvailable += 1;       
        }        
    }

    private int modifyAttack() {
        return getPosition().getOwner() == Team.ORCS ? 1 : 0;
    }

}
