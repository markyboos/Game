package com.game.thrones.engine.actions;

import com.game.thrones.engine.descriptions.Describable;
import com.game.thrones.engine.descriptions.ItemDescriptionRenderer;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.item.Item;

/**
 *
 * @author James
 */
public class ItemReward implements Action, Describable<Item> {

    private final Item item;
    private Hero hero;

    public ItemReward(Item item) {
        this.item = item;
    }
    
    public void setHero(Hero piece) {
        this.hero = piece;
    }

    public void execute() {
        hero.addItem(item);
    }

    public Item summary() {
        return item;
    }

    public String render() {
        return new ItemDescriptionRenderer().render(summary());
    }
}
