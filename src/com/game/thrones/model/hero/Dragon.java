
package com.game.thrones.model.hero;

import com.game.thrones.engine.Dice;
import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class Dragon extends General {
    
    public Dragon() {
        super(General.DRAGON);
        this.rollToDamage = 5;
        this.health = 4;
        this.maxHealth = 4;
        this.team = Team.DRAGONS;
    }
    
    @Override
    public void heal() {
        health = maxHealth;
        super.heal();
    }

    @Override
    public void inflictPenalty(final Hero hero) {
        hero.takeDamage(3);
        
        //choose cards to remove
        
        Dice dice = new Dice();
        
        hero.setCardsToRemove(hero.getCardsToRemove() + dice.roll());
    }

}
