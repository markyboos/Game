
package com.game.thrones.engine.actions;

import com.game.thrones.engine.GameController;
import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.Paladin;

/**
 *
 * @author James
 */
public class SteedAction extends AbstractAction<Paladin> {
    
    private final Territory destination;
    
    public SteedAction(Paladin paladin, Territory destination) {
        super(paladin);
        this.destination = destination;
    }

    public void execute() {
        GameController.getInstance().getBoard()
                .movePieceFar(piece, destination);
    }
    
    @Override
    public String toString() {
        return "Ride your steed to " + destination;
    }

}
