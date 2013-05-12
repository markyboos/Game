
package com.game.thrones.engine.descriptions;

import com.game.thrones.model.hero.Item;

/**
 *
 * @author James
 */
public class RumorsDescriptionRenderer implements DescriptionRenderer<RumorsDescription> {

    public String render(RumorsDescription model) {
        StringBuilder buf = new StringBuilder();
        
        buf.append("You listen to the rumors at the inn\n");
        
        if (model.cards.isEmpty()) {
            buf.append("but you hear nothing of any importance");
        }
        
        for (Item item : model.cards) {
            buf.append("You collect a ");
            buf.append(item.toString());
            buf.append("\n");
        }
        
        return buf.toString();
    }

}
