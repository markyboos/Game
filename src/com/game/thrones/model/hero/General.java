
package com.game.thrones.model.hero;

import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public abstract class General extends Piece {
    
    public static final String FATTY = "FATTY";
    public static final String DRAGON = "DRAGON";
    
    int rollToDamage;
    
    int maxHealth = 5;
    
    int health = 5;
    
    public General(final String name) {
        this.name = name;        
    }
    
    public int getRollToDamage() {
        return rollToDamage;
    }

    public void damage() {
        health -= 1;
    }

    public boolean isDead() {
        return health <= 0;
    }
    
    public void heal() {
        health += 1;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }
    
    public abstract void inflictPenalty(final Hero piece);
    
    @Override
    public String toString() {
        return name + " [" + health + "/" + maxHealth + "]";
    }

}
