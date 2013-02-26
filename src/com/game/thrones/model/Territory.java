
package com.game.thrones.model;

/**
 *
 * @author James
 */
public class Territory {
    
    private final String name;

    /**
     *  Value of the territory; a value which may be used by a number of functions. E.g. gold value, unit value, etc.
     */
    private final int value;

    private House owner;

    public Territory(String name, int value, House ownedBy) {
        this.name = name;
        this.value = value;
        this.owner = ownedBy;
    }

    /**
     * @return the goldPerTurn that this territory generates; a function of the value.
     */
    public int getGoldPerTurn() {
        return getValue();
    }

    /**
     * The higher value territories are the hardest to defend, perhaps?
     * @return the defensive value of the territory (a bonus applicable to a unit fighting in his own territory); a function of the value
     */
    public int getDefensiveValue() {
        return getValue() / 2;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public House getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Territory) {
            Territory t2 = (Territory) o;

            return getName().equals(t2.getName());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
    
}
