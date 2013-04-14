
package com.game.thrones.model.hero;

import com.game.thrones.model.Team;
import com.game.thrones.model.Territory;

/**
 *
 * @author James
 */
public class Sorceress extends Hero {
    
    private Team shape = Team.NO_ONE;
    
    private boolean ambushing = true;
    
    public Sorceress() {
        super("Shapeshifter");
        
        maxHealth = 6;
        health = maxHealth;
        actionsAvailable = health;
    }
    
    public void setShape(Team shape) {
        this.shape = shape;        
    }
    
    public Team getShape() {
        return shape;
    }
    
    @Override
    public int modifyAttack(Minion minion) {        
        if (!ambushing) {
            return 0;
        }
        
        return minion.getTeam() == shape ? 2 : 0;        
    }
    
    @Override
    public int modifyAttack(General general) {        
        if (!ambushing) {
            return 0;
        }
        
        return general.getTeam() == shape ? 1 : 0;      
    }
    
    @Override
    public void setPosition(Territory position) {
        
        ambushing = true;
        
        super.setPosition(position);
    }
    
    @Override
    public void finishAttack() {
        ambushing = false;
    }
    
    @Override
    public void takeDamage(Minion minion) {
        if (minion.getTeam() != shape) {
            super.takeDamage(minion);
        }
    }
    
    @Override
    public String toString() {
        return super.toString() + " " + shape.name();
    }

}
