package com.game.thrones.model.piece;

import com.game.thrones.model.House;
import com.game.thrones.model.Territory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: Jimmy
 * Date: 26/02/13
 * Time: 01:01
 * A special hero type; can perform both diplomatic and combat duties better than a normal hero.
 */
public class Lord extends Piece implements IEmissary, IKnight {
    
    private int forces = 1;
    private boolean fortified;
    
    private List<Piece> prisoners = new ArrayList<Piece>();
    
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
    public int getTroopSize() {
        return forces;
    }
    
    public void recruit(int total) {
        forces += total;
    }

    public void disband(int total) {
        forces -= total;
        if (forces <= 1) {
            forces = 1;
        }
    }
    
    public boolean kill(int total) {
        forces -= total;
        if (forces <= 0) {
            forces = 0;
            return true;
        }
        
        return false;
    }
    
    public void fortify() {
        fortified = true;
    }
    
    public List<Piece> getPrisoners() {
        return Collections.unmodifiableList(prisoners);
    }

    public void addPrisoner(Piece piece) {
        piece.setPrisoned();
        prisoners.add(piece);
    }

    @Override
    public void setHouse(House house) {
        if(house == null) {
            throw new IllegalArgumentException("A Lord cannot be without a House!");
        }

        super.setHouse(house);
    }
    
    @Override
    public void setPosition(Territory territory) {
        super.setPosition(territory);
        
        for (Piece prisoner : prisoners) {
            prisoner.setPosition(territory);
        }
        
        fortified = false;        
    }
    
    @Override
    public String toString() {
        return "Lord [" + name + "] troops [" + forces + "]";
    }    
}
