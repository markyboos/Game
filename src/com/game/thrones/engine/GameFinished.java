/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.thrones.engine;

/**
 *
 * @author James
 */
public enum GameFinished {
    
    GENERAL_REACHED_CENTRE("A general has reached kings landing"),
    TOO_MANY_MINIONS("There are too many minions across the land"),
    TOO_MUCH_TAINTED_LAND("Too much land is tainted"),
    CENTRE_OVERRUN("The centre has been overrun"),
    GENERALS_ALL_DEAD("All generals are dead, you win!");
    
    private String description;
    
    GameFinished(String s) {
        description = s;
    }
    
    public String getDescription() {
        return description;
    }
    
    
    
}
