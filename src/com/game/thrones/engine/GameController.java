
package com.game.thrones.engine;

import android.util.Log;
import com.game.thrones.model.Board;
import com.game.thrones.model.House;
import com.game.thrones.model.House.PlayerType;
import com.game.thrones.model.Standing;
import com.game.thrones.model.Territory;
import com.game.thrones.model.piece.IKnight;
import com.game.thrones.model.piece.Piece;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Singleton of the board
 * 
 * @author James
 */
public class GameController {
    
    private static GameController instance;
    
    private GameController(){
        
        GameInitialiser initialiser = new GameInitialiser();
        board = initialiser.createBoard();
        player = board.getHouse(House.PLAYER_ONE);
        aiController = new AIController();
    }
    
    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }        
        return instance;
    }
    
    private AIController aiController;
    
    private Board board;
    
    public Board getBoard() {
        return board;
    }
    
    private House player;
    
    public House getPlayer() {
        return player;
    }
    
    private Orders orders = new Orders();
    
    public Orders getOrders() {
        return orders;
    }
    
    public void takeTurn() {
        
        //gather all orders        
        for (House house : board.getHouses()) {            
            if (house.getPlayerType() == PlayerType.AI) {
                aiController.takeTurn(house);                
            }
        }
        
        boolean attacked = false;        
        //execute orders
        for (Action action : orders.getOrderedActions()) {
            Log.d("action executed", action.toString());
            
            if (!attacked && action.executionStep() > AbstractAction.ATTACK_ACTION) {
                resolveAttacks();
                attacked = true;
            }
            action.execute();
        }
        
        //if attack action is the last action in the list
        if (!attacked) {
            resolveAttacks();
        }
        
        //update standings              
        for (House house : board.getHouses()) {            
            
            for (House otherHouse : getBoard().getHouses()) {
                if (otherHouse.equals(house)) {
                    continue;
                }

                if (house.getPlayerType() == PlayerType.AI) {                    
                    aiController.updateStandingBasedOnAction(house, otherHouse);
                }
            }
        }
        
        //resolve move orders
        
        //reset orders
        orders = new Orders();
        
        //reset attacks
        attackingPieces.clear();
        
        //calculate funds        
        board.calculateFunds();
    }
    
    private Map<Territory, Set<IKnight>> attackingPieces = new HashMap<Territory, Set<IKnight>>();

    public void addAttackingPiece(final IKnight piece, final Territory target) {        
        if (attackingPieces.containsKey(target)) {
            attackingPieces.get(target).add(piece);
        } else {
            Set<IKnight> pieces = new HashSet<IKnight>();
            pieces.add(piece);
            attackingPieces.put(target, pieces);            
        }
    }
    
    private void resolveAttacks() {
        //resolve attacks
        for (Territory territory : attackingPieces.keySet()) {
            //attacking units
            Set<IKnight> attackPieces = attackingPieces.get(territory);
            
            //defending units
            Set<Piece> defendingPieces = board.getPieces(territory);
            
            resolveFight(territory, attackPieces, defendingPieces);
        }
    }

    //todo need to think about this
    private void resolveFight(final Territory territory, final Set<IKnight> attackPieces, final Set<Piece> defendingPieces) {
        
        if (attackPieces.isEmpty()) {
            throw new IllegalArgumentException("Cant have a fight with no attackers");
        }
        
        //what happens when there is more than one house attacking the same place?
        
        Log.d("fight", "resolving a fight..");
                
        //easy win
        if (defendingPieces.isEmpty()) {
            for (IKnight knight : attackPieces) {
                board.movePiece((Piece)knight, territory);
            }
            
            territory.setOwner(((Piece)attackPieces.iterator().next()).getHouse());
            
            return;
        }
        
        int attackingDamage = 0;
        //need to fight        
        for (IKnight piece : attackPieces) {            
            attackingDamage += piece.getCombatEffectiveness() * piece.getTroopSize();                        
        }
        
        int defendingDamage = 0;
        
        for (Piece piece : defendingPieces) {
            if (!(piece instanceof IKnight)) {
                continue;
            }
            
            IKnight defender = (IKnight)piece;
            defendingDamage += defender.getCombatEffectiveness() * defender.getTroopSize();
        }
        
        boolean allAttackersDead = true;
        
        int toLose = defendingDamage / attackPieces.size();        
        for (IKnight attacker : attackPieces) {
            if (!attacker.kill(toLose)) {
                allAttackersDead = false;
            }
        }
        
        boolean allDefendersDead = true;
        
        toLose = attackingDamage / defendingPieces.size();        
        for (Piece piece : defendingPieces) {
            if (!(piece instanceof IKnight)) {
                continue;
            }
            
            IKnight defender = (IKnight)piece;
            if (!defender.kill(toLose)) {
                allDefendersDead = false;
            }
        }
        
        if (allAttackersDead && !allDefendersDead) {
            //defenders have won
            
            //add prisoners            
            IKnight knight = (IKnight)defendingPieces.iterator().next();
            
            for (IKnight piece : attackPieces) {
                knight.addPrisoner((Piece)piece);
            }
            
        } else if (allDefendersDead && !allAttackersDead) {
            //attackers have won
            for (IKnight knight : attackPieces) {
                board.movePiece((Piece)knight, territory);
            }
            
            IKnight knight = attackPieces.iterator().next();
            
            for (Piece defender : defendingPieces) {
                knight.addPrisoner(defender);
            }
            
            territory.setOwner(((Piece)knight).getHouse());
            
        } else {
            //everyone died!
        }      
    }
}
