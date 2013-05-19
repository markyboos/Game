
package com.game.thrones.activity;

import com.game.thrones.engine.GamePhase;

/**
 *
 * @author James
 */
public class GamePhaseChangeEvent {
    
    private final GamePhase phase;
    
    public GamePhaseChangeEvent(GamePhase phase) {
        this.phase = phase;
    }
    
    public GamePhase getGamePhase() {
        return phase;
    }

}
