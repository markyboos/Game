
package com.game.thrones.engine;

import com.game.thrones.engine.actions.Action;
import com.game.thrones.engine.actions.RemoveMinionsAction;
import com.game.thrones.engine.actions.RemoveTaintAction;
import com.game.thrones.model.Board;
import com.game.thrones.model.PieceTerritoryFilter;
import com.game.thrones.model.Team;
import com.game.thrones.model.Territory;
import com.game.thrones.model.TerritoryCriteria;
import com.game.thrones.model.hero.ActionItem;
import com.game.thrones.model.hero.Item;
import com.game.thrones.model.hero.Minion;
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
        
        final Board board = GameController.getInstance().getBoard();
        
        List<Territory> territories = board.getTerritories();
        
        for (Territory territory : territories) {
            items.add(new Item(territory.getValue(), territory.getOwner()));
        }
        
        items.add(new ActionItem("Elven archers", "Remove all the minions"
                + " from 2 green territories", elvenArchers()));
        
        //spell of purity        
        items.add(new ActionItem("Spell of purity", 
                "Remove all tainted crystals from a single location", new RemoveTaintAction(true)));
        
        //kings guard
        items.add(new ActionItem("Kings guard", "Remove 6 minions on or next to monarch city",
                new RemoveMinionsAction(board.getCentralTerritory(), 1, 6)));
        
        Collections.shuffle(items);
        
        this.heroItems = items;
        
    }
    
    private Action elvenArchers() {
        
        return new Action() {
            
            private List<Territory> territories;
            
            public void setTerritories(List<Territory> territories) {
                this.territories = territories;                
            }
            
            public int getTotal() {
                return 2;
            }
            
            public List<Territory> getOptions() {
                Board board = GameController.getInstance().getBoard();
                
                TerritoryCriteria criteria = new TerritoryCriteria();
                criteria.setOwner(Team.ORCS);
                
                return board.getTerritories(criteria);                                
            }
            
            public void execute() {
                
                Board board = GameController.getInstance().getBoard();
                
                for (Territory territory : territories) {
                
                    List<Minion> pieces = board.getPieces(new PieceTerritoryFilter<Minion>(territory), Minion.class);
                    
                    for (Minion minion : pieces) {
                        board.removePiece(minion);
                    }                    
                }                
            }
        };        
    }
}
