
package com.game.thrones.model.hero;

import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class Fatty extends General implements Woundable {
    
    private int wounded = 2;
    
    private Hero attackedBy;
    
    public Fatty() {
        super(General.FATTY);
        this.rollToDamage = 3;
        this.team = Team.ORCS;        
        maxHealth = 6;    
        health = 6;
    }
    
    @Override
    public boolean isHeavilyWounded() {
        return health <= wounded;
    }
    
    @Override
    public void setAttackedBy(Hero attackedBy) {
        this.attackedBy = attackedBy;        
    }
    
    @Override
    public Hero getAttackedBy() {
        return attackedBy;        
    }

    @Override
    public void inflictPenalty(final Hero hero) {
        hero.takeDamage(2);
        
        //todo take away 2 cards
        hero.setCardsToRemove(hero.getCardsToRemove() + 2);
    }
}
