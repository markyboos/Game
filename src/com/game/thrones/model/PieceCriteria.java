
package com.game.thrones.model;

import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public class PieceCriteria<P extends Piece> {
    
    private Territory territory;
    private Team owner;
    private Class clazz;
    
    public void setTerritory(Territory territory) {
        this.territory = territory;        
    }
    
    public Territory getTerritory() {
        return territory;
    }

    public void setOwner(Team owner) {
        this.owner = owner;
    }
    
    public Team getOwner() {
        return owner;
    }
    
    public void setClass(final Class clazz) {
        this.clazz = clazz;
    }
    
    public Class getType() {
        return clazz;
    }
    
}
