
package com.game.thrones.engine.descriptions;

import com.game.thrones.model.Requirement;

/**
 *
 * @author James
 */
public class QuestDescriptionRenderer implements DescriptionRenderer<QuestDescription> {

    public String render(QuestDescription model) {
        StringBuilder buf = new StringBuilder();
        
        buf.append("quest boy");
        
        buf.append(model.requirement.render());
        
        return buf.toString();
    }

}
