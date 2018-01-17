package com.game.thrones.engine.descriptions;

import com.game.thrones.engine.actions.CollectItemsAction;
import com.game.thrones.model.item.Item;

/**
 * Created by James on 14/07/13.
 */
public class CollectItemsDescriptionRenderer implements DescriptionRenderer<CollectItemsAction> {

    private ItemDescriptionRenderer itemDescriptionRenderer = new ItemDescriptionRenderer();

    @Override
    public String render(CollectItemsAction model) {
        StringBuilder buf = new StringBuilder();

        buf.append("You settle down for the night and gather your thoughts..");

        for (Item item : model.getItems()) {
            buf.append(itemDescriptionRenderer.render(item));
        }

        return buf.toString();


    }
}
