
package com.game.thrones.engine.actions;

import com.game.thrones.engine.GameController;
import com.game.thrones.model.Team;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Item;

/**
 *
 * @author James
 */
public class RumorsAction extends AbstractAction<Hero> implements TeamSelectAction {
    
    private Team team;
    
    public RumorsAction(final Hero piece) {
        super(piece);
    }
    
    public void setTeam(Team team) {
        this.team = team;        
    }    

    public void execute() {
        
        //checks the 2 top items in the deck
        listen(piece);
        listen(piece);        
    }
    
    @Override
    public String toString() {
        return "Listen to rumors";
    }

    private void listen(final Hero hero) {        
        Item item = GameController.getInstance().getItemController().getTopItem();
        
        //if its the same add it to the stash
        if (item.getTeam() == team || item.getTeam() == Team.NO_ONE) {
            hero.addItem(item);
        }
    }

}
