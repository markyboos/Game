
package com.game.thrones.engine;

import com.game.thrones.activity.CameraChangeEvent;
import com.game.thrones.model.Board;
import com.game.thrones.model.PieceCriteria;
import com.game.thrones.model.Territory;
import com.game.thrones.model.hero.Minion;
import com.game.thrones.model.piece.Piece;
import java.util.List;

/**
 *
 * @author James
 */
public class AddMinionAction implements Action {
    
    private Territory territory;
    
    private int number;
    
    private int type;
    
    public AddMinionAction(Territory territory, int number) {
        this.territory = territory;
        this.number = number;        
    }
    
    public void execute() {
        
        final Board board = GameController.getInstance().getBoard();
        
        GameController.getInstance().fireCameraChangeEvent(new CameraChangeEvent(territory));
        
        for (int i = 0; i < number; i++) {
            PieceCriteria criteria = new PieceCriteria();
            criteria.setClass(Minion.class);
            criteria.setTerritory(territory);
            
            List<Piece> pieces = board.getPieces(criteria);
            
            if (pieces.size() < 3) {
                
                Minion minion = new Minion(3);
            
                board.addPiece(minion);
                
                minion.setPosition(territory);
            } else {
                //taint the board
                territory.taint();
                
                //and spread the minions (cause an overrun)
                List<Territory> territories = board.getBorderingTerritories(territory);
                
                for (Territory bordering : territories) {
                    
                    criteria = new PieceCriteria();
                    criteria.setClass(Minion.class);
                    criteria.setTerritory(bordering);

                    pieces = board.getPieces(criteria);

                    if (pieces.size() < 3) {

                        Minion minion = new Minion(3);

                        board.addPiece(minion);

                        minion.setPosition(bordering);
                    } else {
                        //taint the board
                        bordering.taint();
                        return;
                        //dont do an overrun
                    }                  
                }
                
                return;
            }                        
        }
    }

    public Piece getPiece() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Integer executionStep() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
