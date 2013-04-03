
package com.game.thrones.activity;

import com.game.thrones.model.Territory;

/**
 *
 * @author James
 */
public class CameraChangeEvent {
    
    //where to focus on the screen
    private final Territory focus;
    
    public CameraChangeEvent(Territory t) {
        this.focus = t;
    }

    public Territory getFocus() {
        return focus;
    }
}
