
package com.game.thrones.engine;

import com.game.thrones.model.Team;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Item;
import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public class RumorsAction extends AbstractAction {
    
    private Team team;
    
    public RumorsAction(final Piece piece) {
        super(piece);
    }
    
    public void setTeam(Team team) {
        this.team = team;        
    }    

    public void execute() {
        
        Hero hero = (Hero)piece;
        
        hero.addItem(new Item(1, team));
    }
    
    @Override
    public String toString() {
        return "Listen to rumors";
    }

}
