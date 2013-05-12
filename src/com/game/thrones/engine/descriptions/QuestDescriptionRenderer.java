
package com.game.thrones.engine.descriptions;

import com.game.thrones.model.Quest;


/**
 *
 * @author James
 */
public class QuestDescriptionRenderer implements DescriptionRenderer<Quest> {

    public String render(Quest model) {
        StringBuilder buf = new StringBuilder();
        
        buf.append(model.toString());
        buf.append("\n");
        
        buf.append(model.getRequirement().render());
        buf.append("\n");
        
        if (model.isSatisfied()) {        
            buf.append(((Describable)model.getReward()).render());
        }
        
        return buf.toString();
    }

}
