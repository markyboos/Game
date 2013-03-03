
package com.game.thrones.engine;

import com.game.thrones.model.Board;
import com.game.thrones.model.House;

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
        player = board.getHouses().iterator().next();
    }
    
    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }        
        return instance;
    }
    
    private Board board;
    
    public Board getBoard() {
        return board;
    }
    
    private House player;
    
    public House getPlayer() {
        return player;
    }
    
    private Orders orders = new Orders();
    
    public Orders getOrders() {
        return orders;
    }
    
    public void takeTurn() {
        
        for (Action action : orders.getActions()) {
            action.execute();            
        }
        
        orders = new Orders();
    }

}
