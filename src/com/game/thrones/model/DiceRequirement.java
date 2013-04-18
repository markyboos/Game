package com.game.thrones.model;

import com.game.thrones.engine.Dice;
import com.game.thrones.model.hero.Hero;

/**
 * 
 * @author James
 */
public class DiceRequirement implements Requirement {

    private int toRoll;
    private int require;
    private Dice dice = new Dice();

    public DiceRequirement(int toRoll, int require) {
        this.toRoll = toRoll;
        this.require = require;
    }

    public boolean satisfied(Hero hero) {
        return roll(toRoll, require, hero);
    }

    private boolean roll(final int toRoll, final int require, final Hero hero) {

        for (int i = 0; i < toRoll; i++) {
            if (dice.roll(require - hero.modifyQuestRoll())) {
                return true;
            }
        }

        return false;
    }
}