package com.game.thrones.model.piece;

import com.game.thrones.model.House;
import com.game.thrones.model.Territory;

/**
 * Author: Jimmy
 * Date: 26/02/13
 * Time: 00:22
 *
 * An abstract class defining the commonalities between pieces
 */
public abstract class Piece {
    protected String name;

    //who they are fighting for
    private House house = House.NO_ONE;

    //where they are
    private Territory position;
    
    private boolean prisoner;

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
    
    public boolean isPrisoner() {
        return prisoner;
    }
    
    public void setPrisoned() {
        prisoner = true;
    }

    /**
     * Enforce uniqueness of name (in combination with the Set containing all the pieces)
     * @param o Object to compare to
     * @return true if equal, false if not
     */
    @Override
    public boolean equals(Object o) {

        return o instanceof Piece &&
                getName().equals(((Piece) o).getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
    @Override
    public String toString() {
        return name + " of house " + house.getName() + " at " + position;
    }
}
