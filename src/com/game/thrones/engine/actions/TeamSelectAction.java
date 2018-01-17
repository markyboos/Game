
package com.game.thrones.engine.actions;

import com.game.thrones.model.Team;

/**
 * This interface is for actions that require a team selection.
 * 
 * @author James
 */
public interface TeamSelectAction extends Action {

    void setTeam(Team team);
}
