
package com.game.thrones.engine;

import android.util.Log;
import com.game.thrones.engine.descriptions.DiceRollResult;
import java.util.Random;

/**
 *
 * @author James
 */
public class Dice {
    
    private Random random = new Random();
    
    public int roll() {
        
        return random.nextInt(6) + 1;
    }
    
    public DiceRollResult roll(int amount) {
        
        final int rolled = roll();
        
        Log.d("Dice", "Require [" + amount + "], rolled [" + rolled + "]");
        
        return new DiceRollResult(rolled, amount);
    }

}
