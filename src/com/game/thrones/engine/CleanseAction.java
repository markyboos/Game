
package com.game.thrones.engine;

import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Item;
import com.game.thrones.model.hero.Sorceress;

/**
 *
 * @author James
 */
public class CleanseAction extends AbstractAction<Hero> {
    
    private final Territory position;
    private Item itemToUse;
    
    public CleanseAction(final Hero piece) {
        super(piece);
        
        position = piece.getPosition();
    }
    
    Dice dice = new Dice();
    
    public void setItemToUse(Item item) {
        itemToUse = item;        
    }

    public void execute() {
        
        //use a card
        piece.useItem(itemToUse);
        
        int toRoll = 5;
        
        if (piece instanceof Sorceress) {
            toRoll = 4;
        }
        
        if (dice.roll(toRoll) || dice.roll(toRoll)) {
            position.removeTaint();                        
        }
    }
    
    @Override
    public String toString() {
        return "Heal the land";
    }

}
