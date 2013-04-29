
package com.game.thrones.model.hero;

import com.game.thrones.model.Filter;
import com.game.thrones.model.hero.Item.ItemType;

/**
 *
 * @author James
 */
public class ItemTypeFilter implements Filter<Item> {
    
    private final ItemType type;
    
    public ItemTypeFilter(ItemType type) {
        this.type = type;        
    }

    public boolean valid(Item t) {
        return t.type.equals(type);
    }

}
