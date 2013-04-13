
package com.game.thrones.engine;

import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public abstract class AbstractAction<P extends Piece> implements PieceAction<P> {
    
    public static final int DO_NOTHING_ACTION = 0;
    public static final int ASSASSINATE_ACTION = DO_NOTHING_ACTION + 1;
    public static final int ATTACK_ACTION = ASSASSINATE_ACTION + 1;
    public static final int FORTIFY_ACTION = ATTACK_ACTION + 1;
    public static final int MOVE_ACTION = ATTACK_ACTION + 1;
    public static final int PERSUADE_ACTION = MOVE_ACTION + 1;
    public static final int DISBAND_ACTION = PERSUADE_ACTION + 1;
    public static final int RECRUIT_ACTION = DISBAND_ACTION + 1;
    
    protected P piece;
    

    public AbstractAction(P piece) {
        this.piece = piece;
    }

    public P getPiece() {
        return piece;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof AbstractAction) {
            AbstractAction action = (AbstractAction)obj;
            return piece.equals(action.getPiece());
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.piece != null ? this.piece.hashCode() : 0);
        return hash;
    }

}
