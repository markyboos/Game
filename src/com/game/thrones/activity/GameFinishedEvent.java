
package com.game.thrones.activity;

import com.game.thrones.engine.GameFinished;

/**
 *
 * @author James
 */
public class GameFinishedEvent {
    
    private GameFinished finished;
    
    public GameFinishedEvent(GameFinished finished) {
        this.finished = finished;
    }
    
    public GameFinished getFinished() {
        return finished;
    }

}
