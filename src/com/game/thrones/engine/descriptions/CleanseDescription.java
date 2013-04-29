
package com.game.thrones.engine.descriptions;

import java.util.List;

/**
 *
 * @author James
 */
public class CleanseDescription implements ActionDescription {
    
    private List<DiceRollResult> diceRolls;
    
    public CleanseDescription(List<DiceRollResult> diceRolls) {
        this.diceRolls = diceRolls;
    }

    public List<DiceRollResult> getRolls() {
        return diceRolls;
    }

}
