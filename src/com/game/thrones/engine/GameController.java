
package com.game.thrones.engine;

import com.game.thrones.model.Board;

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

}
