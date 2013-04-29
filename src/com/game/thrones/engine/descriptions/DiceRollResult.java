package com.game.thrones.engine.descriptions;

/**
 *
 * @author James
 */
public class DiceRollResult {

    public final int needed;
    public final int rolled;

    public DiceRollResult(int roll, int needed) {
        this.needed = needed;
        this.rolled = roll;
    }
    
    public boolean success() {
        return rolled >= needed;
    }
}
