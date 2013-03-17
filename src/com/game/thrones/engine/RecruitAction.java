
package com.game.thrones.engine;

import com.game.thrones.model.piece.IKnight;
import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public class RecruitAction extends AbstractAction {
    
    public RecruitAction(final Piece piece) {
        super(piece, RECRUIT_ACTION);
    }

    //todo
    public void execute() {
        ((IKnight)piece).recruit(1);
    }
    
    @Override
    public String toString() {
        return "Recruit more units";
    }

}
