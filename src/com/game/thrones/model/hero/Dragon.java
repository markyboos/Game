
package com.game.thrones.model.hero;

import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class Dragon extends General {
    
    public Dragon() {
        super(General.DRAGON);
        this.rollToDamage = 5;
        this.health = 3;
        this.maxHealth = 3;
        this.team = Team.DRAGONS;
    }
    
    @Override
    public void heal() {
        health = maxHealth;
        super.heal();
    }

}
