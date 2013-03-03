
package com.game.thrones.engine;

import com.game.thrones.model.piece.IKnight;

/**
 *
 * @author James
 */
public class RecruitAction implements Action {
    
    private final IKnight knight;
    
    public RecruitAction(IKnight knight) {
        this.knight = knight;
    }

    //todo
    public void execute() {
        knight.recruit(1);
    }
    
    @Override
    public String toString() {
        return "Recruit more units";
    }

}
