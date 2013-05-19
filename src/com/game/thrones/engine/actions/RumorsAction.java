
package com.game.thrones.engine.actions;

import com.game.thrones.engine.GameController;
import com.game.thrones.engine.descriptions.ActionDescription;
import com.game.thrones.engine.descriptions.Describable;
import com.game.thrones.engine.descriptions.RumorsDescription;
import com.game.thrones.engine.descriptions.RumorsDescriptionRenderer;
import com.game.thrones.model.Team;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Item;
import com.game.thrones.model.hero.Rogue;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author James
 */
public class RumorsAction extends AbstractAction<Hero> implements TeamSelectAction, Describable<ActionDescription> {
    
    private Team team;
    
    private RumorsDescription description;
    
    public RumorsAction(final Hero piece) {
        super(piece);
    }
    
    public void setTeam(Team team) {
        this.team = team;        
    }

    public void execute() {
        
        List<Item> collected = new ArrayList<Item> ();
        
        //checks the 2 top items in the deck
        for (int i = 0; i < totalCards(); i++) {
            listen(piece, collected);      
        }
        
        description = new RumorsDescription(collected);
    }
    
    @Override
    public String toString() {
        return "Listen to rumors";
    }
    
    private int totalCards() {
        return piece instanceof Rogue ? 5 : 2;        
    }

    private void listen(final Hero hero, final List<Item> collected) {        
        Item item = GameController.getInstance().getItemController().getTopItem();
        
        //if its the same add it to the stash
        if (item != null && (item.getTeam() == team || item.getTeam() == Team.NO_ONE)) {
            hero.addItem(item);
            collected.add(item);
        } else {
            //otherwise put it on the discard pile
            GameController.getInstance().getItemController().discard(item);
        }
    }

    public ActionDescription summary() {
        return description;
    }

    public String render() {
        return new RumorsDescriptionRenderer().render(description);
    }

}
