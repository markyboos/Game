
package com.game.thrones.engine.actions;

import com.game.thrones.engine.GameController;
import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.Hero;
import java.util.List;

/**
 *
 * @author James
 */
public class TeleportAnyoneAction implements Action, SingleTerritorySelectAction, PlayerSelectAction {
    
    private Hero hero;
    private Territory territory;
    
    public void setPlayer(Hero hero) {
        this.hero = hero;
    }
    
    public void setTerritory(Territory territory) {
        this.territory = territory;
    }

    public List<Territory> getOptions() {
        //can go anywhere
        return GameController.getInstance().getBoard().getTerritories();
    }

    public void execute() {
        GameController.getInstance().getBoard().movePieceFar(hero, territory);
    }
    
    public boolean chosenTerritory() {
        return territory != null;
    }
}
