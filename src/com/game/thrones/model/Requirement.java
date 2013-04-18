package com.game.thrones.model;

import com.game.thrones.model.hero.Hero;

/**
 * A quest requirement.
 *
 * @author James
 */
public interface Requirement {

    public boolean satisfied(Hero hero);
}
