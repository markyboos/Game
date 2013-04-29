package com.game.thrones.engine;

import com.game.thrones.engine.actions.AddMinionAction;
import com.game.thrones.engine.actions.Action;
import android.util.Log;
import com.game.thrones.activity.CameraChangeEvent;
import com.game.thrones.activity.CameraChangeListener;
import com.game.thrones.activity.GameFinishedEvent;
import com.game.thrones.activity.GameFinishedListener;
import com.game.thrones.model.AllFilter;
import java.util.ArrayList;
import java.util.List;

import com.game.thrones.model.Board;
import com.game.thrones.model.Filter;
import com.game.thrones.model.Territory;
import com.game.thrones.model.PieceTerritoryFilter;
import com.game.thrones.model.hero.General;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Minion;
import com.game.thrones.model.hero.Woundable;
import java.util.Collections;

/**
 * Singleton of the board
 *
 * @author James
 */
public class GameController {

    private static GameController instance;

    private GameController(){}

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    private AIController aiController;
    
    public AIController getAIController() {
        return aiController;
    }

    private ItemController itemController;

    private QuestController questController;

    public QuestController getQuestController() {
        return questController;
    }

    public ItemController getItemController() {
        return itemController;
    }

    private Board board;

    public Board getBoard() {
        return board;
    }

    private List<Hero> players;

    private Hero player;

    public Hero getPlayer() {
        return player;
    }

    private List<Action> actionsTaken = new ArrayList<Action>();

    public List<Action> getActionsTaken() {
        return Collections.unmodifiableList(actionsTaken);
    }

    public void initialise(List<Hero> heroes) {

        GameInitialiser initialiser = new GameInitialiser();
        board = initialiser.createBoard(heroes);

        players = new ArrayList<Hero>();

        for (Hero piece : board.getPieces(new AllFilter<Hero>(), Hero.class)) {
            players.add(piece);
        }

        player = players.get(0);

        aiController = new AIController();

        itemController = new ItemController();

        questController = new QuestController();
        
        //add two hero cards for each player
        //deal out quest cards
        for (Hero hero : players) {
            hero.addItem(itemController.getTopItem());
            hero.addItem(itemController.getTopItem());
            hero.setQuest(questController.getTopQuest());
        }

        //add extra minions to the board
        addExtraMinions(3, 2);
        addExtraMinions(3, 1);
        //and reshuffle the evil deck
        aiController.reshuffleDeck();
    }

    public void endTurn() {

        //collect items
        player.addItem(itemController.getTopItem());
        player.addItem(itemController.getTopItem());

        //if the hero is in a place with monsters then take life off
        List<Minion> minionsAtHero = board.getPieces(new PieceTerritoryFilter(player.getPosition()),
                Minion.class);

        player.takeDamage(minionsAtHero);

        Territory centralTerritory = board.getCentralTerritory();

        if (player.isDead()) {
            //put them back on kings landing
            player.clearInventory();

            player.setPosition(centralTerritory);

            player.heal();
        }

        //take evil players turn
        aiController.takeTurn();

        //check loss conditions:

        //if a general reaches the centre,
        //there are too many minions on the map
        //the centre has 5 or more minions
        //or the taint has spread too far then its game over

        Filter centralTerritoryFilter = new PieceTerritoryFilter(centralTerritory);
        if (board.getPieces(centralTerritoryFilter, Minion.class).size() > 4) {
            gameFinishedListener.fireGameFinishedEvent(new GameFinishedEvent(GameFinished.CENTRE_OVERRUN));
        }

        int tainted = 0;

        for (Territory territory : board.getTerritories()) {
            tainted += territory.getTainted();
        }

        if (tainted > 11) {
            gameFinishedListener.fireGameFinishedEvent(new GameFinishedEvent(GameFinished.TOO_MUCH_TAINTED_LAND));
        }

        if (!board.getPieces(centralTerritoryFilter, General.class).isEmpty()) {
            gameFinishedListener.fireGameFinishedEvent(new GameFinishedEvent(GameFinished.GENERAL_REACHED_CENTRE));
        }

        if (board.getPieces(AllFilter.INSTANCE, Minion.class).size() > 25) {
            gameFinishedListener.fireGameFinishedEvent(new GameFinishedEvent(GameFinished.TOO_MANY_MINIONS));
        }

        //heal generals

        for (General general : board.getPieces(new AllFilter<General>(), General.class)) {
            if (general instanceof Woundable) {
                Woundable fatty = (Woundable) general;
                //wait a round of players before healing
                if (fatty.isHeavilyWounded() && fatty.getAttackedBy() != player) {
                    continue;
                }
            }
            general.heal();
        }

        //regain actions back based on health for next turn
        player.rest();

        //next player

        player = getNextPlayer();

        actionsTaken.clear();

        player.modifyActions();
    }

    public boolean takeMove(final Action action) {

        //take the turn for that hero
        action.execute();

        actionsTaken.add(action);

        player.useAction();

        if (player.getActionsAvailable() == 0) {
            //end that players turn
            return true;

        }

        return false;
    }

    private Hero getNextPlayer() {
        int index = players.indexOf(player);

        index ++;

        if (index >= players.size()) {
            index = 0;
        }

        return players.get(index);
    }

    private CameraChangeListener listener;

    public void addCameraChangeListener(final CameraChangeListener listener) {
        this.listener = listener;
    }

    public void fireCameraChangeEvent(final CameraChangeEvent event) {
        listener.fireCameraChangeEvent(event);
    }

    private GameFinishedListener gameFinishedListener;

    public void addGameFinishedListener(final GameFinishedListener listener) {
        this.gameFinishedListener = listener;
    }

    public void fireGameFinishedEvent(final GameFinishedEvent event) {
        gameFinishedListener.fireGameFinishedEvent(event);
    }

    /**
     * Adds extra minions to the board on game initialisation.
     * @param cards the number of cards to pick up from the ai controller
     * @param total the number of minions to add
     */
    private void addExtraMinions(int cards, int total) {
        //add extra monsters to the board

        while (true) {
            //draw 3 cards
            Orders orders = aiController.takeTopCard();
            for (Action action : orders.getActions()) {
                if (action instanceof AddMinionAction) {
                    AddMinionAction addMinionAction = (AddMinionAction)action;
                    Territory territory = addMinionAction.getTerritory();

                    //its not possible to have more than 3 on a territory at the beggining
                    if (board.getPieces(new PieceTerritoryFilter(territory), Minion.class).size() + total > 3 
                            || board.getCentralTerritory().equals(territory)) {
                        continue;
                    }

                    Log.d("addExtraMinions", "adding [" + total + "] minions to [" + territory.getName() + "]");

                    for (int i = 0 ; i < total; i++) {
                        board.addMinionToTerritory(territory, territory.getOwner(), false);
                    }
                    cards --;
                    break;
                }
            }

            if (cards == 0) {
                break;
            }
        }
    }
}
