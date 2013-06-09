
package com.game.thrones.engine.actions;

import com.game.thrones.model.Filter;
import com.game.thrones.model.item.AbstractItem;
import com.game.thrones.model.item.AttackGeneralItem;
import com.game.thrones.model.item.Item;

/**
 *
 * @author James
 */
public interface ItemSelectAction extends Action {
    
    public void setItem(final AttackGeneralItem item);
    
    public Filter getItemFilter();

}
