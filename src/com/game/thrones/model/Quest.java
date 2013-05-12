
package com.game.thrones.model;

import com.game.thrones.engine.actions.Action;
import com.game.thrones.engine.descriptions.ActionDescription;
import com.game.thrones.model.hero.Hero;

/**
 *
 * @author James
 */
public class Quest implements ActionDescription {
    
    private Territory territory;
    
    private Requirement requirement;
    
    private Action reward;
    
    private String name;
    
    private boolean satisfied;
    
    public Quest(final String name, final Territory territory, 
            final Requirement requirement, final Action reward) {
        this.name = name;
        this.territory = territory;
        this.reward = reward;
        this.requirement = requirement;
    }
    
    public Requirement getRequirement() {
        return requirement;
    }
    
    public boolean isSatisfied() {
        return satisfied;
    }    
    
    public boolean isSatisfied(Hero hero) {        
        this.satisfied = requirement.satisfied(hero);
        
        return satisfied;
    }
    
    public Action getReward() {
        return reward;
    }
    
    public Territory getTerritory() {
        return territory;
    }
    
    @Override
    public String toString() {
        return name;
    }

}
