
package com.game.thrones.model.piece;

/**
 * @author James
 *
 * A combat-orientated piece
 */
public class Knight extends Piece implements IKnight {

    public Knight(String name) {
        this.name = name;
    }

    @Override
    public int getCombatEffectiveness() {
        return 1;
        //TODO
    }
}
