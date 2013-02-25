
package com.game.thrones.model;

/**
 *
 * @author James
 */
public class Territory implements Comparable<Territory> {
    
    private final String name;

    /**
     *  Value of the territory; a value which may be used by a number of functions. E.g. gold value, unit value, etc.
     */
    private final int value;

    public Territory(String name, int value) {
        this.name = name;
        this.value = value;
    }

    /**
     * @return the goldPerTurn that this territory generates; a function of the value.
     */
    public int getGoldPerTurn() {
        return getValue();
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Territory territory) {
        return getName().compareTo(territory.getName());
    }
}
