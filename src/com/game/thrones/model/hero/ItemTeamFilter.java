
package com.game.thrones.model.hero;

import com.game.thrones.model.Filter;
import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class ItemTeamFilter implements Filter<Item> {
    
    private final Team team;
    
    public ItemTeamFilter(Team team) {
        this.team = team;        
    }

    public boolean valid(Item t) {
        return t.team.equals(team);
    }

}
