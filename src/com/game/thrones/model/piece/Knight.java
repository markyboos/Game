
package com.game.thrones.model.piece;

/**
 * @author James
 *
 * A combat-orientated piece
 */
public class Knight extends Piece implements IKnight {
    
    //this is the number of forces under this knights command
    private int forces;

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
        return "Knight with " + forces + " troops " + super.toString();
    }

    public void recruit(int total) {
        forces += total;
    }

    public void disband(int total) {
        forces -= total;
        if (forces <= 0) {
            forces = 0;
        }
    }
}
