
package com.game.thrones.model.item;

import com.game.thrones.model.Team;
import com.game.thrones.model.Territory;

/**
 *
 * @author James
 */
public class TerritoryItem extends AbstractItem implements AttackGeneralItem {
    
    private final Territory territory;
    
    public TerritoryItem(final Territory territory) {
        super(territory.getName(), "Use this when attacking a general, or to heal taint.");
        this.territory = territory;
    }
    
    public int getAttackValue() {
        return territory.getValue();
    }

    public Team getTeam() {
        return territory.getOwner();
    }
    
    @Override
    public String toString() {
        return territory.getName() + " " + territory.getOwner().toString() + " " + territory.getValue();
    }

}
