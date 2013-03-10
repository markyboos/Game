
package com.game.thrones.model;

/**
 *
 * @author James
 */
public class PieceCriteria {
    
    private Territory territory;
    private House owner;
    
    public void setTerritory(Territory territory) {
        this.territory = territory;        
    }
    
    public Territory getTerritory() {
        return territory;
    }

    public void setOwner(House owner) {
        this.owner = owner;
    }
    
    public House getOwner() {
        return owner;
    }

}
