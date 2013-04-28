package com.game.thrones.engine.descriptions;

/**
 *
 * @author James
 */
public class SingleAttackResult {

    public final int needed;
    public final int rolled;
    public final boolean slayer;

    public SingleAttackResult(boolean slayer, int roll, int needed) {
        this.needed = needed;
        this.slayer = slayer;
        this.rolled = roll;
    }
}
