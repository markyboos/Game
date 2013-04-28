
package com.game.thrones.engine;

import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.Wizard;

/**
 *
 * @author James
 */
public class TeleportAction extends AbstractAction<Wizard> {
    
    private Territory destination;
    
    public TeleportAction(Wizard wizard, Territory territory) {
        super(wizard);
        this.destination = territory;
    }

    public void execute() {
        GameController.getInstance().getBoard()
                .movePieceFar(piece, destination);
    }
    
    @Override
    public String toString() {
        return "Teleport to " + destination;
    }

}
