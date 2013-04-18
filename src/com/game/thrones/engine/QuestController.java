package com.game.thrones.engine;

import android.util.Log;
import com.game.thrones.model.Board;
import com.game.thrones.model.DiceRequirement;
import com.game.thrones.model.ItemReward;
import com.game.thrones.model.Quest;
import com.game.thrones.model.Reward;
import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Item;
import com.game.thrones.model.hero.Minion;
import com.game.thrones.model.piece.Piece;

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
        //kill D6 minions up to 1 space away
        quests.add(new Quest("The Duke's Envoy", thornyWoods, new DiceRequirement(3, 5), createDukesReward(thornyWoods)));

        //boots of speed
        Territory dragonRange = GameController.getInstance().getBoard().getTerritory(Territory.DRAGON_RANGE);
        //roll 3 dice, get a 6
        //gain +2 actions per turn
        quests.add(new Quest("Boots of Speed", dragonRange,
                new DiceRequirement(3, 6), new ItemReward(new Item(2))));

        //old tree bloke
        Territory oldFatherTreeBloke = GameController.getInstance().getBoard().getTerritory(Territory.OLD_FATHER_WOOD);
        //roll 4 dice, get a 6
        //todo discard to remove any single taint
        quests.add(new Quest("The Old Tree Bloke", oldFatherTreeBloke,
                new DiceRequirement(4, 6), new ItemReward(new Item())));
    }

    private Reward createDukesReward(final Territory territory) {
        return new Reward() {
            public void collect(Hero hero) {
                Dice dice = new Dice();

                int total = dice.roll();

                Log.d("QuestController", "collection mc cormic reward killed:[" + total + "]");

                Board board = GameController.getInstance().getBoard();

                if (removePieces(territory, total)) {
                    return;
                }

                for (Territory bordering : board.getBorderingTerritories(territory)) {
                    if (removePieces(bordering, total)) {
                        return;
                    }
                }
            }

            private boolean removePieces(Territory ter, int total) {

                Board board = GameController.getInstance().getBoard();
                for (Piece piece : board.getPieces(ter)) {
                    if (piece instanceof Minion) {
                        total--;
                        board.removePiece(piece);
                        if (total == 0) {
                            return true;
                        }
                    }
                }
                return false;
            }
        };
    }
}
