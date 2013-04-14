
package com.game.thrones.engine;

import com.game.thrones.model.Team;

/**
 * This interface is for actions that require a team selection.
 * 
 * @author James
 */
public interface TeamSelectAction extends Action {

    public void setTeam(Team team);
}
