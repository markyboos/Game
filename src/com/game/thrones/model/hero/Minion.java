
package com.game.thrones.model.hero;

import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public class Minion extends Piece {
    
    public Minion(int roll) {
        name = Double.toString(Math.random());
        rollToDamage = roll;
    }
    
    int rollToDamage;
    
    public int getRollToDamage() {
        return rollToDamage;
    }
    
    @Override
    public String toString() {
        return "Evil minion";
    }

}
