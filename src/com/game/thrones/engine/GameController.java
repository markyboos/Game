
package com.game.thrones.engine;

import java.util.ArrayList;
import java.util.List;

import com.game.thrones.model.Board;
import com.game.thrones.model.PieceCriteria;
import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.General;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Item;
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
    }
    
    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }        
        return instance;
    }
    
    private AIController aiController;
    
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
    
    public boolean endTurn() {
                
        //collect items
        player.addItem(null);        
        
        //take evil players turn
        aiController.takeTurn();
        
        //check loss conditions
        //if a general reaches the centre,
        //the centre has taint
        //or the taint has spread too far then its game over
        Territory centralTerritory = board.getCentralTerritory();
        
        if (centralTerritory.getTainted() > 0) {
            return true;
        }
        
        int tainted = 0;
        
        for (Territory territory : board.getTerritories()) {
            tainted = territory.getTainted();
        }
        
        if (tainted > 10) {
            return true;
        }
        
        PieceCriteria criteria = new PieceCriteria();
        criteria.setTerritory(centralTerritory);
        criteria.setClass(General.class);
        
        if (!board.getPieces(criteria).isEmpty()) {
            return true;
        }
        
        player.rest();
        
        //next player
        
        player = getNextPlayer();
        
        
        return false;
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
}
