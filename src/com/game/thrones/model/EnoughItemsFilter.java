
package com.game.thrones.model;

import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.item.AbstractItem;
import com.game.thrones.model.item.AttackGeneralItem;
import com.game.thrones.model.item.Item;

/**
 *
 * @author James
 */
public class EnoughItemsFilter implements Filter<Hero> {
    
    public Filter<AttackGeneralItem> filter;
    
    public EnoughItemsFilter(Filter<AttackGeneralItem> filter) {
        this.filter = filter;        
    }

    public boolean valid(Hero hero) {
        return !hero.getItems(filter, AttackGeneralItem.class).isEmpty();
    }

}
