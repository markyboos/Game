
package com.game.thrones.engine.actions;

import com.game.thrones.MainActivity;
import com.game.thrones.engine.GameController;
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
        
        playSoundEffect(MainActivity.TELEPORT);
        
        GameController.getInstance().getBoard()
                .movePieceFar(piece, destination);
    }
    
    @Override
    public String toString() {
        return "Teleport to " + destination;
    }

}
