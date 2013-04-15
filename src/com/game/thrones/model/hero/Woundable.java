
package com.game.thrones.model.hero;

/**
 *
 * @author James
 */
public interface Woundable {
    
    public boolean isHeavilyWounded();
    
    public void setAttackedBy(Hero attackedBy);
    
    public Hero getAttackedBy();

}
