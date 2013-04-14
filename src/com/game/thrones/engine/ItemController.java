
package com.game.thrones.engine;

import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.Item;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author James
 */
public class ItemController {
    
    private Queue<Item> heroItems;
    
    public Item getTopItem() {
        
        if (heroItems == null) {
            initialiseItems();
        }
        
        Item nextItem = heroItems.poll();
        
        if (nextItem == null) {
            initialiseItems();
            
            nextItem = heroItems.poll();
        }
        
        return nextItem;
    }

    private void initialiseItems() {
        LinkedList<Item> items = new LinkedList<Item>();
        
        List<Territory> territories = GameController.getInstance()
                .getBoard().getTerritories();
        
        for (Territory territory : territories) {
            items.add(new Item(territory.getValue(), territory.getOwner()));
        }
        
        Collections.shuffle(items);
        
        this.heroItems = items;
        
    }
    
    

}
