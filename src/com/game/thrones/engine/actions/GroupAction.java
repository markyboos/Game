
package com.game.thrones.engine.actions;

import com.game.thrones.model.piece.Piece;
import java.util.Set;

/**
 *
 * @author James
 */
public interface GroupAction <P extends Piece> extends Action {
    
    Set<P> getTeam();

}
