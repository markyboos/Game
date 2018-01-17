package com.game.thrones.engine.actions;

import com.game.thrones.engine.GameController;
import com.game.thrones.engine.ItemController;
import com.game.thrones.engine.descriptions.ActionDescription;
import com.game.thrones.engine.descriptions.CollectItemsDescriptionRenderer;
import com.game.thrones.engine.descriptions.Describable;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.item.AttackGeneralItem;
import com.game.thrones.model.item.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 14/07/13.
 */
public class CollectItemsAction extends AbstractAction<Hero> implements Describable<CollectItemsAction>, ActionDescription {

    public CollectItemsAction(Hero hero) {
        super(hero);
    }

    private List<Item> items = new ArrayList<Item>();

    @Override
    public void execute() {
        //collect items
        for (int i = 0; i < piece.itemsPerTurn(); i++) {

            AttackGeneralItem topItem = GameController.getInstance().getItemController().getTopItem();

            if (topItem == null) {
                return;
            }

            piece.addItem(topItem);
            items.add(topItem);
        }
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public CollectItemsAction summary() {
        return this;
    }

    @Override
    public String render() {
        return new CollectItemsDescriptionRenderer().render(this);
    }
}
