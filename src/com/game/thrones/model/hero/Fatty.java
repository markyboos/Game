
package com.game.thrones.model.hero;

import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class Fatty extends General {
    
    public Fatty() {
        super(General.FATTY);
        this.rollToDamage = 3;
        this.team = Team.ORCS;
    }
}
