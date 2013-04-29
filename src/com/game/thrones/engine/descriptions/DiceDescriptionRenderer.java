
package com.game.thrones.engine.descriptions;

/**
 *
 * @author James
 */
public class DiceDescriptionRenderer implements DescriptionRenderer<DiceDescription> {

    public String render(final DiceDescription model) {
        StringBuilder buf = new StringBuilder();
        buf.append("\n");
        for (DiceRollResult result : model.rolled) {
            buf.append("You need a ");
            buf.append(result.needed);
            buf.append(" and rolled a ");
            buf.append(result.rolled);
            buf.append(".\n");
        }
        if (model.satisfied) {
            buf.append("You succeeded!");
        } else {
            buf.append("You failed.");
        }
        
        return buf.toString();
    }

}
