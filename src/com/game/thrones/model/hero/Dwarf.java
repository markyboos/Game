
package com.game.thrones.model.hero;

import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class Dwarf extends Hero {
    
    public Dwarf() {
        super("Dwarf");
    }
    
    @Override
    public void modifyActions() {
        
        super.modifyActions();
        
        if (getPosition().getOwner() == Team.DEMONS) {
            actionsAvailable += 1;       
        }        
    }
    
    @Override
    public int modifyAttack(Minion minion) {        
        
        return modifyAttack(minion.getTeam());
    }
    
    @Override
    public int modifyAttack(General general) { 
        
        return modifyAttack(general.getTeam());
    }
    
    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage - 1);
    }

    private int modifyAttack(Team team) {
        if (team == Team.DRAGONS) {
            return 1;
        }
        
        return 0;
    }

}
