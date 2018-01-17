package com.game.thrones.model;

import com.game.thrones.engine.Dice;
import com.game.thrones.engine.descriptions.DiceDescription;
import com.game.thrones.engine.descriptions.DiceDescriptionRenderer;
import com.game.thrones.engine.descriptions.DiceRollResult;
import com.game.thrones.model.hero.Hero;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author James
 */
public class DiceRequirement implements Requirement<DiceDescription> {

    private int toRoll;
    private int require;
    private Dice dice = new Dice();
    private List<DiceRollResult> diceResults;
    private DiceDescription description;

    public DiceRequirement(int numberToRoll, int require) {
        this.toRoll = numberToRoll;
        this.require = require;
    }

    public boolean satisfied(Hero hero) {
        
        diceResults = new ArrayList<DiceRollResult>();
        
        boolean satisfied = roll(toRoll, require, hero);
        
        description = new DiceDescription(diceResults, satisfied);
        
        return satisfied;
    }

    //this is the result, rather than what you have to do for it
    public DiceDescription summary() {
        return description;
    }
    
    public String render() {
        return DiceDescriptionRenderer.INSTANCE.render(summary());
    }

    private boolean roll(final int toRoll, final int require, final Hero hero) {

        for (int i = 0; i < toRoll; i++) {
            DiceRollResult result = dice.roll(require, hero.modifyQuestRoll());
            diceResults.add(result);
            
            if (result.success()) {
                return true;
            }
        }

        return false;
    }
}