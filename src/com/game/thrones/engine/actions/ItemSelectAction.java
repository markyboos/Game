
package com.game.thrones.engine.actions;

import com.game.thrones.model.Filter;
import com.game.thrones.model.hero.Item;

/**
 *
 * @author James
 */
public interface ItemSelectAction extends Action {
    
    public void setItem(final Item item);
    
    public Filter getItemFilter();

}
