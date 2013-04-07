
package com.game.thrones.model.hero;

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
    }
    
    @Override
    public void heal() {
        health = maxHealth;
        super.heal();
    }

}
