
package com.game.thrones.engine.descriptions;

/**
 *
 * @author James
 */
public class CleanseDescriptionRenderer implements DescriptionRenderer<CleanseDescription> {
    
    public String render(final CleanseDescription model) {
        StringBuilder buf = new StringBuilder();
        
        for (DiceRollResult roll : model.getRolls()) {
            
            buf.append(roll.toString());
            
            if (!roll.success()) {                
                DiceRollResult last = model.getRolls().get(model.getRolls().size() - 1);                
                if (roll.equals(last)) {
                    buf.append("You failed to cleanse the land.");                        
                } else {
                    buf.append("You roll again.\n");
                }                
            } else {
                buf.append(" You cleansed the land.\n");
                break;
            }
        }
        
        return buf.toString();
    }

}
