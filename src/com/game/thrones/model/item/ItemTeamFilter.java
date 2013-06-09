
package com.game.thrones.model.item;

import com.game.thrones.model.Filter;
import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class ItemTeamFilter implements Filter<AttackGeneralItem> {
    
    private final Team team;
    
    public ItemTeamFilter(Team team) {
        this.team = team;        
    }

    public boolean valid(AttackGeneralItem t) {
        return t.getTeam().equals(team);
    }

}
