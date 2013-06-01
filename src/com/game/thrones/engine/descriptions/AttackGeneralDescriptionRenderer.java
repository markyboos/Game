
package com.game.thrones.engine.descriptions;

import com.game.thrones.engine.actions.AttackGeneralAction;
import com.game.thrones.model.hero.Hero;
import java.util.List;

/**
 *
 * @author James
 */
public class AttackGeneralDescriptionRenderer implements DescriptionRenderer<AttackGeneralAction> {

    public String render(final AttackGeneralAction model) {
        StringBuilder buf = new StringBuilder();
        
        buf.append("You attack the general\n");
        
        for (Hero hero : model.getRolls().keySet()) {
            buf.append(hero.getName());
            buf.append(" attacks the general\n");
            
            List<DiceRollResult> rolls = model.getRolls().get(hero);
            
            int hits = 0;
            
            for (DiceRollResult roll : rolls) {
                if (roll.success()) {
                    hits ++;
                }
            }
            
            buf.append(" and got ");
            buf.append(hits);
            buf.append(" total hits\n");
        }
        
        if (model.getTarget().isDead()) {
            buf.append("You slayed the general");
        } else {
            buf.append("You failed");
        }
        
        return buf.toString();
    }

}
