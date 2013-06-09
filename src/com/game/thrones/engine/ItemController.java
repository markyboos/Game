
package com.game.thrones.engine;

import com.game.thrones.engine.actions.ElvenArchersAction;
import com.game.thrones.engine.actions.RemoveMinionsAction;
import com.game.thrones.engine.actions.RemoveTaintAction;
import com.game.thrones.engine.actions.TeleportAnyoneAction;
import com.game.thrones.model.Board;
import com.game.thrones.model.Team;
import com.game.thrones.model.Territory;
import com.game.thrones.model.item.ActionItem;
import com.game.thrones.model.item.AttackGeneralActionItem;
import com.game.thrones.model.item.AttackGeneralItem;
import com.game.thrones.model.item.TerritoryItem;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author James
 */
public class ItemController {
    
    private Deck<AttackGeneralItem> deck = new Deck<AttackGeneralItem>(initialiseItems());
    
    public AttackGeneralItem getTopItem() {
        return deck.takeTopCard();
    }
    
    public void discard(AttackGeneralItem item) {
        if (item == null) {
            return;
        }
        
        //action items get removed from the deck
        if (item instanceof ActionItem) {
            return;
        }
        deck.discard(item);        
    }

    private List<AttackGeneralItem> initialiseItems() {
        LinkedList<AttackGeneralItem> items = new LinkedList<AttackGeneralItem>();
        
        final Board board = GameController.getInstance().getBoard();
        
        List<Territory> territories = board.getTerritories();
        
        for (Territory territory : territories) {
            items.add(new TerritoryItem(territory));
        }
        
        items.add(new AttackGeneralActionItem("Elven archers", "Remove all the minions"
                + " from 2 green territories", new ElvenArchersAction(), Team.ORCS, 2));
        
        //spell of purity        
        items.add(new AttackGeneralActionItem("Spell of purity", 
                "Remove all tainted crystals from a single location", new RemoveTaintAction(true), Team.ORCS, 1));
        
        //kings guard
        items.add(new AttackGeneralActionItem("Kings guard", "Remove 6 minions on or next to monarch city",
                new RemoveMinionsAction(board.getCentralTerritory(), 1, 6), Team.NO_ONE, 2));
        
        //battle fury
        //items.add(new ActionItem("Battle Fury", "Defeat all minions at your current location", new RemoveMinionsAction(null, distanceAway, totalToRemove)));
        
        //battle strategy
        //push one general back and remove all minions from 3 territories
        //items.add(new ActionItem("Battle Strategy", "Push one general back and remove all minions from 3 territories", new ));
        
        //hammer of valor
        items.add(new AttackGeneralActionItem("Hammer of Valor", "Teleport any hero to any location", new TeleportAnyoneAction(), Team.NO_ONE, 2));
                
        return items;
    }
}
