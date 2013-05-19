
package com.game.thrones.engine.descriptions;

/**
 *
 * @author James
 */
public class DiceDescriptionRenderer implements DescriptionRenderer<DiceDescription> {
    
    public static final DiceDescriptionRenderer INSTANCE = new DiceDescriptionRenderer();

    public String render(final DiceDescription model) {
        StringBuilder buf = new StringBuilder();
        buf.append("\n");
        for (DiceRollResult roll : model.rolled) {
            buf.append(roll.toString());
        }
        
        return buf.toString();
    }

}
