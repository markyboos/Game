package com.game.thrones.model.piece;

import com.game.thrones.model.Team;
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
    protected Team team;

    //where they are
    private Territory position;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    public Team getTeam() {
        return team;
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
        return name + " at " + position;
    }
}
