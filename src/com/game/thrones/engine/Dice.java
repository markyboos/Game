
package com.game.thrones.engine;

import java.util.Random;

/**
 *
 * @author James
 */
public class Dice {
    
    private Random random = new Random();
    
    public int roll() {
        
        return random.nextInt(5) + 1;
    }

}
