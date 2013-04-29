
package com.game.thrones.model;

import com.game.thrones.model.hero.Hero;

/**
 *
 * @author James
 */
public class Quest {
    
    private Territory territory;
    
    private Requirement requirement;
    
    private Reward reward;
    
    private String name;
    
    public Quest(final String name, final Territory territory, 
            final Requirement requirement, final Reward reward) {
        this.name = name;
        this.territory = territory;
        this.reward = reward;
        this.requirement = requirement;
    }
    
    public Requirement getRequirement() {
        return requirement;
    }
    
    public boolean isSatisfied(Hero hero) {
        return requirement.satisfied(hero);
    }
    
    public void collectReward(Hero hero) {
        reward.collect(hero);
    }
    
    public Territory getTerritory() {
        return territory;
    }
    
    @Override
    public String toString() {
        return name;
    }

}
