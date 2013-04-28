
package com.game.thrones.model.hero;

/**
 *
 * @author James
 */
public class Paladin extends Hero {
    
    public Paladin() {
        super("Paladin");        
    }
    
    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage - 1);
    }
    
    @Override
    protected boolean affectedByUndead() {
        return false;
    }

}
