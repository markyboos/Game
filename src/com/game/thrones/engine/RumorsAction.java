
package com.game.thrones.engine;

import com.game.thrones.model.Team;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Item;

/**
 *
 * @author James
 */
public class RumorsAction extends AbstractAction<Hero> {
    
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
        if (item.getTeam() == team) {
            hero.addItem(item);
        }
    }

}
