
package com.game.thrones.model.hero;

import com.game.thrones.engine.Dice;
import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class DemonKing extends General implements Woundable {
    
    private int wounded = 1;
    private Hero attackedBy;
    
    public DemonKing() {
        super("Demon");
        
        this.team = Team.OLD_DEMONS;
        this.rollToDamage = 4;
        
        maxHealth = 6;    
        health = 6;
    }

    @Override
    public void inflictPenalty(Hero hero) {
        Dice dice = new Dice();
        hero.takeDamage(dice.roll() / 2);
        
        //lose all hero cards
        hero.clearInventory();
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

}
