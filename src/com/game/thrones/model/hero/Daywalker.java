
package com.game.thrones.model.hero;

import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class Daywalker extends Hero {
    
    public Daywalker() {
        super("Daywalker", 5);
    }
    
    //silver armoury
    @Override
    public int modifyAttack(Minion minion) {        
        return modifyAttack(minion.getTeam());
    }
    
    @Override
    public int modifyAttack(General general) {        
        return modifyAttack(general.getTeam());  
    }

    private int modifyAttack(Team team) {
        return (team == Team.VAMPIRES || team == Team.WOLVES) ? 1 : 0;
    }
}
