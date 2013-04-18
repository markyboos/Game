
package com.game.thrones.engine;

import com.game.thrones.model.Quest;
import com.game.thrones.model.hero.Hero;

/**
 *
 * @author James
 */
public class QuestAction extends AbstractAction<Hero> {
    

    public QuestAction(Hero hero) {
        super(hero);
    }

    public void execute() {
        Quest quest = piece.getQuest();
        
        if (!quest.isSatisfied(piece)) {
            return;            
        }
        
        quest.collectReward(piece);
        
        piece.competeQuest();
        
        Quest next = GameController.getInstance().getQuestController().getTopQuest();
        
        piece.setQuest(next);
    }
    
    @Override
    public String toString() {
        return piece.getQuest().toString();
    }
    
    
}
