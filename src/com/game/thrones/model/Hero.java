
package com.game.thrones.model;

/**
 *
 * @author James
 */
public class Hero {
    
    private String name;
    
    //who they are fighting for
    private House house;
    
    //where they are
    private Territory position;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
    
    public boolean move(Territory territory) {        
        if (getPosition().isNextTo(territory)) {
            setPosition(territory);
            return true;
        }
        
        return false;        
    }
    
}
