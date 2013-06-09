
package com.game.thrones.engine.actions;

import com.game.thrones.activity.CameraChangeEvent;
import com.game.thrones.engine.GameController;
import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.Woundable;
import com.game.thrones.model.piece.Piece;
import java.util.List;

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
        
        if (piece instanceof Woundable && ((Woundable)piece).isHeavilyWounded()) {
            return;            
        }
        
        final GameController instance = GameController.getInstance();
        
        List<Territory> path = instance.getBoard()
                .getPathToTerritory(piece.getPosition(), finish);
        
        instance.fireCameraChangeEvent(new CameraChangeEvent(piece.getPosition()));
        
        //todo this will only ever work for a distance of one
        for (int i = 0; i < distance; i ++) {
            
            instance
                .getBoard().movePiece(piece, path.get(i));
            
        }
    }

}
