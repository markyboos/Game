
package com.game.thrones.model.item;

import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public interface AttackGeneralItem extends Item {
    
    int getAttackValue();
    
    Team getTeam();

}
