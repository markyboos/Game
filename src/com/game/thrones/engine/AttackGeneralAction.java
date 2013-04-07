
package com.game.thrones.engine;

import com.game.thrones.activity.GameFinishedEvent;
import com.game.thrones.model.Board;
import com.game.thrones.model.PieceCriteria;
import com.game.thrones.model.hero.General;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Item;
import com.game.thrones.model.piece.Piece;
import java.util.ArrayList;
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
    
    public General getTarget() {
        return target;
    }
    
    public void setItemsToUse(final List<Item> items) {
        itemsToUse = items;
    }

    public void execute() {
        
        if (itemsToUse == null || itemsToUse.isEmpty()) {
            throw new NullPointerException("You need to choose some items to use.");
        }
        
        int attacks = 0;
        
        for (Item item : itemsToUse) {
            if (item.getTeam() != target.getTeam()) {
                throw new AssertionError("Cannot use items against a general that arent of that team");                
            }
            
            attacks += item.getPower();                        
        }
        
        Hero hero = (Hero)piece;
        
        List<Item> items = new ArrayList<Item>(itemsToUse);
        
        for (Item item : items) {
            hero.useItem(item);
        }
        
        for (int i = 0; i < attacks; i++) {
            if (dice.roll() > target.getRollToDamage()) {
                target.damage();
            }
        }
        
        if (target.isDead()) {
            
            Board board = GameController.getInstance().getBoard();
            
            board.removePiece(target);
            
            //make the hero brilliant
                        
            
            //victory condition check           
            PieceCriteria criteria = new PieceCriteria();
            criteria.setClass(General.class);
            
            if (board.getPieces(criteria).isEmpty()) {
                GameController.getInstance().fireGameFinishedEvent(new GameFinishedEvent(GameFinished.GENERALS_ALL_DEAD));                
            }
            
        } else {
            //take off life or something
            
        }
    }
    
    @Override
    public String toString() {
        return "Attack the general [" + target.getName() + "]";
    }

}
