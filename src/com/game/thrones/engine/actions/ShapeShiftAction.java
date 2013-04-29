
package com.game.thrones.engine.actions;

import com.game.thrones.model.Team;
import com.game.thrones.model.hero.Sorceress;

/**
 *
 * @author James
 */
public class ShapeShiftAction extends AbstractAction<Sorceress> implements TeamSelectAction {
    
    private Team team = Team.NO_ONE;

    public ShapeShiftAction(Sorceress sorceress) {
        super(sorceress);
    }
    
    public void setTeam(final Team team) {
        this.team = team;        
    }

    public void execute() {
        piece.setShape(team);
    }
    
    @Override
    public String toString() {
        return "Shape shift";
    }

}
