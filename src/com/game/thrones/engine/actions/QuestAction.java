
package com.game.thrones.engine.actions;

import com.game.thrones.engine.descriptions.QuestDescription;
import com.game.thrones.engine.GameController;
import com.game.thrones.engine.descriptions.Describable;
import com.game.thrones.engine.descriptions.QuestDescriptionRenderer;
import com.game.thrones.model.Quest;
import com.game.thrones.model.Requirement;
import com.game.thrones.model.hero.Hero;

/**
 *
 * @author James
 */
public class QuestAction extends AbstractAction<Hero> implements Describable<QuestDescription> {
    
    private QuestDescription description;

    public QuestAction(Hero hero) {
        super(hero);
    }

    public void execute() {
        Quest quest = piece.getQuest();
        
        Requirement requirement = quest.getRequirement();
        
        if (!quest.isSatisfied(piece)) {
            
            description = new QuestDescription(requirement);
            
            return;            
        }
        
        quest.collectReward(piece);
        
        piece.competeQuest();
        
        Quest next = GameController.getInstance().getQuestController().getTopQuest();
        
        description = new QuestDescription(requirement);
        
        piece.setQuest(next);
    }
    
    @Override
    public String toString() {
        return piece.getQuest().toString();
    }

    public QuestDescription summary() {
        return description;
    }

    public String render() {
        return new QuestDescriptionRenderer().render(summary());
    }
    
    
}
