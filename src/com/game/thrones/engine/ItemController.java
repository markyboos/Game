
package com.game.thrones.engine;

import com.game.thrones.engine.actions.ElvenArchersAction;
import com.game.thrones.engine.actions.RemoveMinionsAction;
import com.game.thrones.engine.actions.RemoveTaintAction;
import com.game.thrones.engine.actions.TeleportAnyoneAction;
import com.game.thrones.model.Board;
import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.ActionItem;
import com.game.thrones.model.hero.Item;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author James
 */
public class ItemController {
    
    private Deck<Item> deck = new Deck<Item>(initialiseItems());
    
    public Item getTopItem() {
        return deck.takeTopCard();
    }
    
    public void discard(Item item) {
        if (item == null) {
            return;
        }
        
        //action items get removed from the deck
        if (item instanceof ActionItem) {
            return;
        }
        deck.discard(item);        
    }

    private List<Item> initialiseItems() {
        LinkedList<Item> items = new LinkedList<Item>();
        
        final Board board = GameController.getInstance().getBoard();
        
        List<Territory> territories = board.getTerritories();
        
        for (Territory territory : territories) {
            items.add(new Item(territory.getValue(), territory.getOwner()));
        }
        
        items.add(new ActionItem("Elven archers", "Remove all the minions"
                + " from 2 green territories", new ElvenArchersAction()));
        
        //spell of purity        
        items.add(new ActionItem("Spell of purity", 
                "Remove all tainted crystals from a single location", new RemoveTaintAction(true)));
        
        //kings guard
        items.add(new ActionItem("Kings guard", "Remove 6 minions on or next to monarch city",
                new RemoveMinionsAction(board.getCentralTerritory(), 1, 6)));
        
        //battle fury
        //items.add(new ActionItem("Battle Fury", "Defeat all minions at your current location", new RemoveMinionsAction(null, distanceAway, totalToRemove)));
        
        //battle strategy
        //push one general back and remove all minions from 3 territories
        //items.add(new ActionItem("Battle Strategy", "Push one general back and remove all minions from 3 territories", new ));
        
        //hammer of valor
        items.add(new ActionItem("Hammer of Valor", "Teleport any hero to any location", new TeleportAnyoneAction()));
                
        return items;
    }
}
