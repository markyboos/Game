
package com.game.thrones.model.hero;

import com.game.thrones.engine.Dice;
import com.game.thrones.engine.GameController;
import com.game.thrones.model.PieceTerritoryFilter;
import com.game.thrones.model.Team;
import java.util.List;

/**
 *
 * @author James
 */
public class Daywalker extends Hero implements DamageListener {
    
    private boolean thirstSated = false;
    
    public Daywalker() {
        super("Daywalker", 5);
    }
    
    //silver armoury
    @Override
    public int modifyAttack(Minion minion) {        
        return modifyAttack(minion.getTeam());
    }
    
    @Override
    public int modifyAttack(General general) {        
        return modifyAttack(general.getTeam());  
    }
    
    private int modifyAttack(Team team) {
        return (team == Team.VAMPIRES || team == Team.WOLVES) ? 1 : 0;
    }
    
    @Override
    public void rest() {
        super.rest();
        thirstSated = false;
    }
    
    private Dice dice = new Dice();

    public void damageEventFired(final Hero hero) {
        //check the daywalker feed ability
        if (hero != this && hero.getPosition() == getPosition()) {            
            if (dice.roll() % 2 == 0) {
                bloodyFrenzy();
            }
        }
    }
    
    public void feed() {
        if (this.isAtMaxHealth() || thirstSated) {
            return;
        }
        health ++;
        thirstSated = true;
    }
    
    public void bloodyFrenzy() {
        if (thirstSated) {
            return;
        }
        
        List<Hero> pieces = GameController.getInstance().getBoard()
                .getPieces(new PieceTerritoryFilter<Hero>(this.getPosition()), Hero.class);

        for (Hero hero : pieces) {
            
            if (hero instanceof HellBlazer) {
                thirstSated = true;
                takeDamage(1);
            } else if (!hero.equals(this)) {
                thirstSated = true;
                hero.takeDamage(1);
            }        
        }        
    }
}
