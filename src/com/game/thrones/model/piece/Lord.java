package com.game.thrones.model.piece;

import com.game.thrones.model.House;

/**
 * Author: Jimmy
 * Date: 26/02/13
 * Time: 01:01
 * A special hero type; can perform both diplomatic and combat duties better than a normal hero.
 */
public class Lord extends Piece implements IEmissary, IKnight {
    //Unlike other units, a Lord must belong to a house (else he is not a Lord!)
    public Lord(String name, House house) {
        this.name = name;
        setHouse(house);
    }

    @Override
    public int getSpyEffectiveness() {
        return 2;
        //TODO More effective than a regular spy; but only one lord per house so use wisely.
    }

    @Override
    public int getCombatEffectiveness() {
        return 2;
        //TODO More effective than a regular knight; but only one lord per house so use wisely.
    }

    @Override
    public void setHouse(House house) {
        if(house == null) {
            throw new IllegalArgumentException("A Lord cannot be without a House!");
        }

        super.setHouse(house);
    }
    
    @Override
    public String toString() {
        return "Lord " + super.toString();
    }
}
