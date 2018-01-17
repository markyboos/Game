
package com.game.thrones.engine.actions;

import com.game.thrones.model.hero.Hero;

/**
 *
 * @author James
 */
public interface PlayerSelectAction extends Action {

    void setPlayer(Hero hero);
    
}
