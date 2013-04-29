
package com.game.thrones.model;

import com.game.thrones.model.piece.Piece;

/**
 *
 * @author James
 */
public class TeamFilter implements Filter<Piece> {
    
    private Team team;
    
    public TeamFilter(Team team) {
        this.team = team;        
    }

    public boolean valid(Piece piece) {
        return piece.getTeam().equals(team);
    }
    

}
