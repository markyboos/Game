
package com.game.thrones.model.item;

import com.game.thrones.model.Team;
import com.game.thrones.model.hero.General;

/**
 *
 * @author James
 */
public class SlayerItem extends AbstractItem {
    private final Team team;
    
    public SlayerItem(String description, General slayed) {
        super("Slayer of " + slayed.getName(), description);
        this.team = slayed.getTeam();
    }
    
    public Team getTeam() {
        return team;
    }

}
