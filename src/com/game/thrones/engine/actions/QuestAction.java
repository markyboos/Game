
package com.game.thrones.engine.actions;

import com.game.thrones.engine.GameController;
import com.game.thrones.engine.descriptions.Describable;
import com.game.thrones.engine.descriptions.QuestDescriptionRenderer;
import com.game.thrones.model.Quest;
import com.game.thrones.model.hero.Hero;

/**
 *
 * @author James
 */
public class QuestAction extends AbstractAction<Hero> implements Describable<Quest> {
    
    private Quest quest;
    
    public QuestAction(Hero hero) {
        super(hero);
        this.quest = piece.getQuest();
    }

    public void execute() {
        
        piece.useAction();
        
        if (!quest.isSatisfied(piece)) {            
            return;            
        }
        
        piece.collectReward(quest);
        
        Quest next = GameController.getInstance().getQuestController().getTopQuest();
        
        piece.setQuest(next);
    }
    
    @Override
    public String toString() {
        return piece.getQuest().toString();
    }

    public Quest summary() {
        return quest;
    }

    public String render() {
        return new QuestDescriptionRenderer().render(summary());
    }
    
    
}
