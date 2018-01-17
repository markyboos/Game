
package com.game.thrones.engine;

/**
 *
 * @author James
 */
public enum GamePhase {
    
    MORNING(2),
    EVENING(0),
    NIGHT(1);

    public final int previousPhase;

    GamePhase(int nextPhase) {
        this.previousPhase = nextPhase;
    }
    
}
