
package com.game.thrones.model.hero;

import com.game.thrones.model.Team;
import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public class Minion extends Piece {
    
    public Minion(Team team) {
        name = Double.toString(Math.random());
        super.team = team;
        
        switch(team) {
            case ORCS:
                rollToDamage = 3;
                break;
            case DRAGONS:
                rollToDamage = 5;
                break;
            case DEMONS:
                rollToDamage = 4;
                break;
            case UNDEAD:
                rollToDamage = 4;
                break;
            default:
                throw new AssertionError(team.name());
        }
    }
    
    int rollToDamage;
    
    public int getRollToDamage() {
        return rollToDamage;
    }
    
    @Override
    public String toString() {
        return "Evil minion " + super.getTeam();
    }

}
