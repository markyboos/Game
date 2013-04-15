
package com.game.thrones.model.hero;

import com.game.thrones.engine.Dice;
import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class UndeadKing extends General {
    
    public UndeadKing() {
        super("Boney.M");
        
        this.team = Team.UNDEAD;
        this.rollToDamage = 4;
        
        health = 5;
        maxHealth = 5;
    }

    @Override
    public void inflictPenalty(Hero hero) {        
        Dice dice = new Dice();
        hero.takeDamage(dice.roll());         
        hero.setCardsToRemove(hero.getCardsToRemove() + dice.roll());
    }

}
