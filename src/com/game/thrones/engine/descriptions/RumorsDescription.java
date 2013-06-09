
package com.game.thrones.engine.descriptions;

import com.game.thrones.model.item.Item;
import java.util.List;

/**
 *
 * @author James
 */
public class RumorsDescription implements ActionDescription {
    
    final List<Item> cards;
    
    public RumorsDescription(List<Item> items) {
        cards = items;        
    }

}
