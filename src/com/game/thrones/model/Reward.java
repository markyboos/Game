package com.game.thrones.model;

import com.game.thrones.model.hero.Hero;

/**
 * A Quest reward.
 *
 * @author James
 */
public interface Reward {

    public void collect(Hero hero);
}
