
package com.game.thrones.engine.descriptions;

import com.game.thrones.model.hero.Minion;

/**
 *
 * @author James
 */
public class AttackDescriptionRenderer implements DescriptionRenderer<AttackDescription> {
    
    public String render(AttackDescription model) {
        StringBuilder buf = new StringBuilder();
        buf.append("You attacked the minions at ");
        buf.append(model.attackingTerritory);
        buf.append("\n");
        
        for (Minion minion : model.minionResults.keySet()) {
            
            SingleAttackResult result = model.minionResults.get(minion);
        
            buf.append("You go for a ");
            buf.append(minion.getTeam());
            buf.append("\n");

            if (result.slayer) {
                buf.append("The ");
                buf.append(minion.getTeam());
                buf.append(" sees you and your mighty weapon and runs away!");
            } else {
                buf.append("You needed to roll a ");
                buf.append(result.needed);
                buf.append(" and rolled a ");
                buf.append(result.rolled);                

                if (result.rolled >= result.needed) {
                    buf.append(", killing it.");                    
                } else {
                    buf.append(", failing to kill it.");
                }

                buf.append("\n");
            }
        }
            
        if (model.killed == model.minionResults.size()) {
            buf.append("You killed everything!");
        }
        
        return buf.toString();
    }

}
