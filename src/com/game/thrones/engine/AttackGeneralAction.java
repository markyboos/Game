
package com.game.thrones.engine;

import com.game.thrones.model.hero.General;
import com.game.thrones.model.hero.Item;
import com.game.thrones.model.piece.Piece;
import java.util.List;

/**
 *
 * @author James
 */
public class AttackGeneralAction extends AbstractAction {
    
    private List<Item> itemsToUse;
    private General target;
    private Dice dice = new Dice();
    
    public AttackGeneralAction(final Piece hero, final General target) {
        super(hero);
        
        this.target = target;        
    }
    
    public void setItemsToUse(final List<Item> items) {
        itemsToUse = items;
    }

    public void execute() {
        
        if (itemsToUse == null) {
            throw new NullPointerException("You need to choose some items to use.");
        }
        
        int attacks = 0;
        
        for (Item item : itemsToUse) {
            attacks += item.getPower();                        
        }
        
        
        for (int i = 0; i < attacks; i++) {
            if (dice.roll() > target.getRollToDamage()) {
                target.damage();
            }
        }
        
        if (target.isDead()) {
            GameController.getInstance().getBoard().removePiece(target);
            
            //make the hero brilliant
        } else {
            //take off life or something
        }
        
        
        
    }

}
