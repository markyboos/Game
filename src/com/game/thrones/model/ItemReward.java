package com.game.thrones.model;

import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Item;

/**
 *
 * @author James
 */
public class ItemReward implements Reward {

    private final Item item;

    public ItemReward(Item item) {
        this.item = item;
    }

    public void collect(Hero hero) {
        hero.addItem(item);
    }
}
