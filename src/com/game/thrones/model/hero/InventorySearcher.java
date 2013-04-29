
package com.game.thrones.model.hero;

import com.game.thrones.model.AndFilter;
import com.game.thrones.model.Filter;
import com.game.thrones.model.OrFilter;
import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class InventorySearcher {
    
    public Filter<Item> aTeamOrNooneFilter(Team team) {        
        return new OrFilter<Item>(new ItemTeamFilter(team), new ItemTeamFilter(Team.NO_ONE));        
    }
    
    public boolean hasTeamOrNooneItems(Hero hero, Team team) {
        return !hero.getItems(aTeamOrNooneFilter(team), Item.class).isEmpty();
    }
    
    public boolean hasTeamItems(Hero hero, Team team) {
        return !hero.getItems(new ItemTeamFilter(team), Item.class).isEmpty();
    }
    
    public boolean isSlayer(Hero hero, Team team) {
        return !hero.getItems(
                new AndFilter<Item>(
                    new ItemTeamFilter(team), 
                    new ItemTypeFilter(Item.ItemType.SLAYER)
                ), Item.class).isEmpty();
    }

}
