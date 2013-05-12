
package com.game.thrones.engine.descriptions;

import com.game.thrones.model.hero.Item;

/**
 *
 * @author James
 */
public class ItemDescriptionRenderer implements DescriptionRenderer<Item> {

    public String render(Item model) {
        return "You gained a\n" + model.toString();
    }
}
