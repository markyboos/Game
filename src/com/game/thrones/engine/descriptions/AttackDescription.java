
package com.game.thrones.engine.descriptions;

import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.Minion;
import java.util.Map;

/**
 *
 * @author James
 */
public class AttackDescription implements ActionDescription {
    
    final Territory attackingTerritory;
    final Map<Minion, DiceRollResult> minionResults;
    final int killed;
    
    public AttackDescription(Territory territory, Map<Minion, DiceRollResult> minions, int killed) {
        attackingTerritory = territory;
        minionResults = minions;
        this.killed = killed;
    }
}