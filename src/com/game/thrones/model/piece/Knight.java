
package com.game.thrones.model.piece;

import com.game.thrones.model.Territory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author James
 *
 * A combat-orientated piece
 */
public class Knight extends Piece implements IKnight {
    
    //this is the number of forces under this knights command
    private int forces = 1;
    
    private boolean fortified;
    
    private List<Piece> prisoners = new ArrayList<Piece>();

    public Knight(String name) {
        this.name = name;
    }

    @Override
    public int getCombatEffectiveness() {
        return 1;
        //TODO
    }
    
    @Override
    public int getTroopSize() {
        return forces;
    }
    
    @Override
    public String toString() {
        return "Knight [" + forces + "] troops";
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
    public void setPosition(Territory territory) {
        super.setPosition(territory);
        
        for (Piece prisoner : prisoners) {
            prisoner.setPosition(territory);
        }
        
        fortified = false;        
    }
}
