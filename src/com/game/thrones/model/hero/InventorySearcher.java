
package com.game.thrones.model.hero;

import com.game.thrones.model.item.ItemTeamFilter;
import com.game.thrones.model.item.AbstractItem;
import com.game.thrones.model.Filter;
import com.game.thrones.model.OrFilter;
import com.game.thrones.model.Team;
import com.game.thrones.model.item.AttackGeneralItem;
import com.game.thrones.model.item.Item;
import com.game.thrones.model.item.SlayerItem;

/**
 *
 * @author James
 */
public class InventorySearcher {
    
    public Filter<AttackGeneralItem> aTeamOrNooneFilter(Team team) {        
        return new OrFilter<AttackGeneralItem>(new ItemTeamFilter(team), new ItemTeamFilter(Team.NO_ONE));        
    }
    
    public boolean hasTeamOrNooneItems(Hero hero, Team team) {
        return !hero.getItems(aTeamOrNooneFilter(team), AttackGeneralItem.class).isEmpty();
    }
    
    public boolean hasTeamItems(Hero hero, Team team) {
        return !hero.getItems(new ItemTeamFilter(team), AttackGeneralItem.class).isEmpty();
    }
    
    public boolean isSlayer(Hero hero, Team team) {
        for (SlayerItem item : hero.getSlayerItems()) {
            if (((SlayerItem)item).getTeam() == team) {
                return true;
            }
        }
        return false;
    }

}
