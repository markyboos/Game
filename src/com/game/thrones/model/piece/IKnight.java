package com.game.thrones.model.piece;

import java.util.List;

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
    
    /**
     * Kill the number of troops from this army.
     * 
     * @param total 
     * @return true if the army has been destroyed
     */
    boolean kill(int total);

    /**
     * Improves the defence of a army but if they move then they are no longer fortified.
     */
    void fortify();
    
    /**
     * Gets the prisoners of the knight
     * @return 
     */
    List<Piece> getPrisoners();
    
    /**
     * Add a prisoner, prisoners will move along with this piece and can make no actions.
     * @param piece 
     */
    void addPrisoner(Piece piece);
}
