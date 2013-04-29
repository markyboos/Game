
package com.game.thrones.model;

import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Item;

/**
 *
 * @author James
 */
public class EnoughItemsFilter implements Filter<Hero> {
    
    public Filter<Item> filter;
    
    public EnoughItemsFilter(Filter<Item> filter) {
        this.filter = filter;        
    }

    public boolean valid(Hero hero) {
        return !hero.getItems(filter, Item.class).isEmpty();
    }

}
