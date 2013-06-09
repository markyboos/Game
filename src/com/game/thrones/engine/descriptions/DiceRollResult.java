package com.game.thrones.engine.descriptions;

/**
 *
 * @author James
 */
public class DiceRollResult implements AttackResult {

    public final int needed;
    public final int rolled;
    public final int modifier;

    public DiceRollResult(int roll, int needed, int modifier) {
        this.needed = needed;
        this.rolled = roll;
        this.modifier = modifier;
    }
    
    public boolean success() {
        return total() >= needed;
    }
    
    public int total() {
        return rolled + modifier;
    }
    
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("You need a ");
        buf.append(needed);
        buf.append(" and rolled a ");
        buf.append(rolled);
        if (modifier > 0) {
            buf.append(" + ");
            buf.append(modifier);
        }            

        buf.append(".\n");
        
        return buf.toString();
    }
}
