
package com.game.thrones.engine.actions;

import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.item.AbstractItem;

/**
 *
 * @author James
 */
public class AddItemAction extends AbstractAction<Hero> {
    
    public AddItemAction(Hero hero, AbstractItem item) {
        super(hero);
    }

    public void execute() {
        this.piece.addItem(null);
    }

}
