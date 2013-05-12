
package com.game.thrones.model.hero;

import com.game.thrones.model.Team;

/**
 * todo. poop.
 *
 * @author James
 */
public class BootsOfSpeed extends Item {
    
    public BootsOfSpeed(int power) {
        type = ItemType.BOOTS_OF_SPEED;
        this.power = power;
        this.team = Team.NO_ONE;
    }
    
    @Override
    public String toString() {
        return "The boots of speed";
    }

}
