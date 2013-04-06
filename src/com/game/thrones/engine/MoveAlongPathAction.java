
package com.game.thrones.engine;

import com.game.thrones.model.Territory;
import com.game.thrones.model.piece.Piece;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 *
 * @author James
 */
public class MoveAlongPathAction extends AbstractAction {
    
    private int distance;
    private Territory finish;
    
    public MoveAlongPathAction(Piece piece, Territory finish, int distance) {
        super(piece);
        this.distance = distance;
        this.finish = finish;
    }

    public void execute() {
        
        if (finish == piece.getPosition()) {
            return;
        }        
        
        final GameController instance = GameController.getInstance();
        
        Set<Territory> path = instance.getBoard()
                .getPathToTerritory(piece.getPosition(), finish);
        
        //todo this will only ever work for a distance of one
        for (int i = 0; i < distance; i ++) {
            
            Collection<Territory> bordering = instance.getBoard()
                                                    .getBorderingTerritories(piece.getPosition());
            
            Collection<Territory> territories = new ArrayList<Territory>(bordering);
            
            territories.retainAll(path);
                        
            Territory destination = territories.iterator().next();
            
            instance
                .getBoard().movePiece(piece, destination);
            
        }
    }

}
