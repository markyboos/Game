
package com.game.thrones.engine;

import com.game.thrones.model.Board;
import com.game.thrones.model.House;
import com.game.thrones.model.House.PlayerType;

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
        player = board.getHouse(House.PLAYER_ONE);
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
    
    private House player;
    
    public House getPlayer() {
        return player;
    }
    
    private Orders orders = new Orders();
    
    public Orders getOrders() {
        return orders;
    }
    
    public void takeTurn() {
        
        for (House house : board.getHouses()) {            
            if (house.getPlayerType() == PlayerType.AI) {
                aiController.takeTurn(house);                
            }            
        }
        
        for (Action action : orders.getActions()) {
            action.execute();            
        }
        
        orders = new Orders();
        
        //calculate funds        
        board.calculateFunds();
    }

}
