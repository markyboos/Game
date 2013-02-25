
package com.game.thrones.model;

/**
 *
 * @author James
 */
public class Hero implements Comparable<Hero>{

    final private String name;
    
    //who they are fighting for
    private House house;
    
    //where they are
    private Territory position;

    public Hero(String name) {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the house
     */
    public House getHouse() {
        return house;
    }

    /**
     * @param house the house to set
     */
    public void setHouse(House house) {
        this.house = house;
    }

    /**
     * @return the position
     */
    public Territory getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Territory position) {
        this.position = position;
    }

    @Override
    public int compareTo(Hero hero) {
        return getName().compareTo(hero.getName());
    }
}
