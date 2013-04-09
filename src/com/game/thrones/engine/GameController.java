
package com.game.thrones.engine;

import com.game.thrones.activity.CameraChangeEvent;
import com.game.thrones.activity.CameraChangeListener;
import com.game.thrones.activity.GameFinishedEvent;
import com.game.thrones.activity.GameFinishedListener;
import java.util.ArrayList;
import java.util.List;

import com.game.thrones.model.Board;
import com.game.thrones.model.PieceCriteria;
import com.game.thrones.model.Team;
import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.General;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Item;
import com.game.thrones.model.hero.Minion;
import com.game.thrones.model.piece.Piece;

/**
 * Singleton of the board
 * 
 * @author James
 */
public class GameController {
    
    private static GameController instance;
    
    private GameController(){
        
        GameInitialiser initialiser = new GameInitialiser();
        board = initialiser.createBoard();
        
        PieceCriteria criteria = new PieceCriteria();
        criteria.setClass(Hero.class);
        
        players = new ArrayList<Hero>();
        
        for (Piece piece : board.getPieces(criteria)) {
            players.add((Hero)piece);
        }        
        
        player = players.get(0);
        aiController = new AIController();
        
        itemController = new ItemController();
        
        //todo        
        //pick 3 random territories and add 2 minions to each        
        //pick 3 random territories and add 1 minion to each
    }
    
    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }        
        return instance;
    }
    
    public static void reset() {
        instance = new GameController();
    }
    
    private AIController aiController;
    
    private ItemController itemController;
    
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
    
    List<Item> items = new ArrayList<Item>();
    
    public void endTurn() {
                
        //collect items
        player.addItem(itemController.getTopItem());
        player.addItem(itemController.getTopItem());
        
        //if the hero is in a place with monsters then take life off
        PieceCriteria criteria = new PieceCriteria();
        criteria.setClass(Minion.class);
        criteria.setTerritory(player.getPosition());
        
        List<Piece> minionsAtHero = board.getPieces(criteria);
        
        for(Piece pminion : minionsAtHero) {
            Minion minion = (Minion)pminion;
            //todo depending on the minion the player can take more damage
            player.damage();
        }
        
        //take evil players turn
        aiController.takeTurn();
        
        //check loss conditions:
        
        //if a general reaches the centre,
        //there are too many minions on the map
        //the centre has taint
        //or the taint has spread too far then its game over
        Territory centralTerritory = board.getCentralTerritory();
        
        if (centralTerritory.getTainted() > 0) {
            gameFinishedListener.fireGameFinishedEvent(new GameFinishedEvent(GameFinished.CENTRE_TAINTED));
        }
        
        int tainted = 0;
        
        for (Territory territory : board.getTerritories()) {
            tainted += territory.getTainted();
        }
        
        if (tainted > 10) {
            gameFinishedListener.fireGameFinishedEvent(new GameFinishedEvent(GameFinished.TOO_MUCH_TAINTED_LAND));
        }
        
        criteria = new PieceCriteria();
        criteria.setTerritory(centralTerritory);
        criteria.setClass(General.class);
        
        if (!board.getPieces(criteria).isEmpty()) {
            gameFinishedListener.fireGameFinishedEvent(new GameFinishedEvent(GameFinished.GENERAL_REACHED_CENTRE));
        }
        
        criteria = new PieceCriteria();
        criteria.setClass(Minion.class);
        
        if (board.getPieces(criteria).size() > 25) {
            gameFinishedListener.fireGameFinishedEvent(new GameFinishedEvent(GameFinished.TOO_MANY_MINIONS));
        }
        
        //heal generals
        criteria = new PieceCriteria();
        criteria.setClass(General.class);
        
        for (Piece piece : board.getPieces(criteria)) {
            General general = (General)piece;
            general.heal();            
        }
        
        //regain actions back based on health for next turn
        player.rest();
        
        //next player
        
        player = getNextPlayer();
    }
    
    public boolean takeMove(final Action action) {
        
        //take the turn for that hero
        action.execute();
        
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
}
