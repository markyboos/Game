
package com.game.thrones.engine.descriptions;

import java.util.List;

/**
 *
 * @author James
 */
public class DiceDescription implements ActionDescription {
    
    final List<DiceRollResult> rolled;
    final boolean satisfied;
    
    public DiceDescription(List<DiceRollResult> rolled, boolean satisfied) {
        this.rolled = rolled;
        this.satisfied = satisfied;
    }

}
