
package com.game.thrones.model;

/**
 *
 * @author James
 */
public class Territory {
    
    private String name;
    
    //money generated per turn
    private int goldPerTurn;
    
    private House ownedBy;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the goldPerTurn
     */
    public int getGoldPerTurn() {
        return goldPerTurn;
    }

    /**
     * @param goldPerTurn the goldPerTurn to set
     */
    public void setGoldPerTurn(int goldPerTurn) {
        this.goldPerTurn = goldPerTurn;
    }

    /**
     * @return the ownedBy
     */
    public House getOwnedBy() {
        return ownedBy;
    }

    /**
     * @param ownedBy the ownedBy to set
     */
    public void setOwnedBy(House ownedBy) {
        this.ownedBy = ownedBy;
    }
    
}
