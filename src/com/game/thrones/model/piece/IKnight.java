package com.game.thrones.model.piece;

/**
 * Author: Jimmy
 * Date: 26/02/13
 * Time: 00:49
 * An interface representing a unit that can perform combat-related duties.
 */
public interface IKnight {
    public int getCombatEffectiveness();
    
    /**
     * This is the total number of troops under this knights command.
     * 
     * @return the total number of troops
     */
    int getTroopSize();
    
    /**
     * Adds more troops to this knights army.
     * 
     * @param total the number of troops that have been recruited
     */    
    void recruit(int total);
    
    /**
     * Disbands troops from this knights army.
     * 
     * @param total the number of troops that have been disbanded
     */    
    void disband(int total);
}
