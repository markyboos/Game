package com.game.thrones.model.piece;

/**
 * Author: Jimmy
 * Date: 26/02/13
 * Time: 01:01
 * A diplomatic piece.
 */
public class Emissary extends Piece implements IEmissary {
    public Emissary(String name) {
        this.name = name;
    }

    @Override
    public int getSpyEffectiveness() {
        return 1;
        //TODO
    }
    
    @Override
    public String toString() {
        return "Emissary [" + name + "]";
    }
}
