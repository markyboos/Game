
package com.game.thrones.model.hero;

import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class Fatty extends General {
    
    private int wounded = 2;
    
    private Hero attackedBy;
    
    public Fatty() {
        super(General.FATTY);
        this.rollToDamage = 3;
        this.team = Team.ORCS;        
        maxHealth = 6;    
        health = 6;
    }
    
    public boolean isHeavilyWounded() {
        return health <= wounded;
    }
    
    public void setAttackedBy(Hero attackedBy) {
        this.attackedBy = attackedBy;        
    }
    
    public Hero getAttackedBy() {
        return attackedBy;        
    }
}
