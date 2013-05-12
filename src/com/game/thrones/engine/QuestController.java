package com.game.thrones.engine;

import com.game.thrones.engine.actions.RemoveMinionsAction;
import com.game.thrones.engine.actions.RemoveTaintAction;
import com.game.thrones.model.DiceRequirement;
import com.game.thrones.engine.actions.ItemReward;
import com.game.thrones.model.Quest;
import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.ActionItem;
import com.game.thrones.model.hero.BootsOfSpeed;
import java.util.Collections;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author James
 */
public class QuestController {

    private Queue<Quest> quests;

    public Quest getTopQuest() {

        if (quests == null) {
            initialiseQuests();
        }

        return quests.poll();
    }

    public void initialiseQuests() {

        quests = new LinkedList<Quest>();

        //dukes envoy
        //mc cormic highlands
        final Territory thornyWoods = GameController.getInstance().getBoard().getTerritory(Territory.THORNY_WOODS);
        //roll 3 dice, get a 5
        //kill D6 minions up to 2 spaces away
        quests.add(new Quest("The Duke's Envoy", thornyWoods, new DiceRequirement(3, 5), 
                new RemoveMinionsAction(thornyWoods, 2, new Dice().roll())));
        //boots of speed
        Territory dragonRange = GameController.getInstance().getBoard().getTerritory(Territory.DRAGON_RANGE);
        //roll 3 dice, get a 6
        //gain +2 actions per turn
        quests.add(new Quest("Boots of Speed", dragonRange,
                new DiceRequirement(3, 6), new ItemReward(new BootsOfSpeed(2))));

        //old tree bloke
        Territory oldFatherTreeBloke = GameController.getInstance().getBoard().getTerritory(Territory.OLD_FATHER_WOOD);
        //roll 4 dice, get a 6
        //todo discard to remove any single taint
        quests.add(new Quest("The Old Tree Bloke", oldFatherTreeBloke,
                new DiceRequirement(4, 6), new ItemReward(new ActionItem("Ancient tree of magic", "Remove one taint from any land on the map", new RemoveTaintAction(false)))));
        
        Collections.shuffle((LinkedList)quests);
        
    }
}
