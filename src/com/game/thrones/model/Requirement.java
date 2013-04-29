package com.game.thrones.model;

import com.game.thrones.engine.descriptions.ActionDescription;
import com.game.thrones.engine.descriptions.Describable;
import com.game.thrones.model.hero.Hero;

/**
 * A quest requirement.
 *
 * @author James
 */
public interface Requirement<R extends ActionDescription> extends Describable<R> {
    
    public boolean satisfied(Hero hero);
}
