
package com.game.thrones.engine.descriptions;

/**
 *
 * @author James
 */
public class SlayerAttackResult implements AttackResult {
    
    public boolean success() {
        return true;
    }
    
    @Override
    public String toString() {
        return "You slaughter them";        
    }

}
