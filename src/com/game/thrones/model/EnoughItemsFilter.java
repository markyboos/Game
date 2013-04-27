
package com.game.thrones.model;

import com.game.thrones.model.hero.Hero;

/**
 *
 * @author James
 */
public class EnoughItemsFilter implements PieceFilter<Hero> {
    
    public Team team;
    
    public EnoughItemsFilter(Team team) {
        this.team = team;        
    }

    public boolean valid(Hero hero) {
        return !hero.getItemsForTeam(team).isEmpty();
    }

}
