
package com.game.thrones.engine;

import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Minion;

/**
 *
 * @author James
 */
public class RangedAttackAction extends AttackAction {
    
    public RangedAttackAction(Hero hero, Territory territory) {
        super(hero);
        this.attackingTerritory = territory;
    }
    
    @Override
    protected int modifyAttack(Minion minion) {
        return 0;
    }
    
    @Override
    protected boolean isSlayer(Minion minion) {
        return false;
    }
    
    @Override
    public String toString() {
        return "Fire at the minions at " + attackingTerritory.getName();
    }

}
