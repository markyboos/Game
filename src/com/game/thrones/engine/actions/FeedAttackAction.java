
package com.game.thrones.engine.actions;

import com.game.thrones.model.hero.Daywalker;

/**
 *
 * @author James
 */
public class FeedAttackAction extends AttackAction<Daywalker> {
    
    public FeedAttackAction(Daywalker daywalker) {
        super(daywalker);        
    }
    
    @Override
    public void execute(int killed) {
        
        //need to do the choice here
        if (killed > 0) {        
            if (dice.roll() % 2 == 1) {
                piece.bloodyFrenzy();
            }
            
            piece.feed();
        }        
    }
    
    @Override
    public String toString() {
        return "Ravage the minions";
    }
}
