
package com.game.thrones.model.item;

import com.game.thrones.engine.actions.Action;
import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class AttackGeneralActionItem extends ActionItem implements AttackGeneralItem {
        
    private final int value;
    private final Team team;
    
    public AttackGeneralActionItem(final String name, final String description, final Action action, final Team team, final int value) {
        super(name + " " + team + " " + value, description, action);
        this.team = team;
        this.value = value;
    }

    public int getAttackValue() {
        return value;
    }

    public Team getTeam() {
        return team;
    }

}
