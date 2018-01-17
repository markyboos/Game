package com.game.thrones.engine.descriptions;

import com.game.thrones.engine.actions.MinionsAttackAction;

/**
 * Created by James on 14/07/13.
 */
public class DamageDescriptionRenderer implements DescriptionRenderer<MinionsAttackAction> {
    @Override
    public String render(MinionsAttackAction model) {
        StringBuilder buf = new StringBuilder();

        buf.append("The minions spot your camp and attack you");

        buf.append("\nthey do ");

        buf.append(model.getDamage());

        buf.append(" damage");

        return buf.toString();
    }
}
