
package com.game.thrones.engine.actions;

import com.game.framework.SoundManager;
import com.game.thrones.MainActivity;
import com.game.thrones.engine.descriptions.Describable;
import com.game.thrones.engine.Dice;
import com.game.thrones.engine.GameController;
import com.game.thrones.engine.descriptions.AttackDescription;
import com.game.thrones.engine.descriptions.AttackDescriptionRenderer;
import com.game.thrones.engine.descriptions.DiceRollResult;
import com.game.thrones.model.Territory;
import com.game.thrones.model.PieceTerritoryFilter;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.InventorySearcher;
import com.game.thrones.model.hero.Minion;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author James
 */
public class AttackAction<H extends Hero> extends AbstractAction<H> implements Describable<AttackDescription> {
    
    protected Territory attackingTerritory;
    
    private InventorySearcher builder = new InventorySearcher();

    public AttackAction(final H piece) {
        super(piece);
        
        attackingTerritory = piece.getPosition();
    }
    
    protected Dice dice = new Dice();
    
    private AttackDescription description;

    public void execute() {
        
        playSoundEffect(MainActivity.SWORDFIGHT);
        
        List<Minion> minions = GameController.getInstance()
                .getBoard().getPieces(new PieceTerritoryFilter(attackingTerritory), Minion.class);
        
        int killed = 0;
        
        Map<Minion, DiceRollResult> attacks = new HashMap<Minion, DiceRollResult>();
        
        for (Minion minion : minions) {
            //roll the dice
            
            boolean slayer = isSlayer(minion);
            DiceRollResult result = new DiceRollResult(getRoll(), rollToDamage(minion),
                    modifyAttack(minion));
            
            attacks.put(minion, result);
            
            if (slayer || 
                    result.success()) {
                //remove the minion
                GameController.getInstance().getBoard().removePiece(minion);
                killed ++;
            }
        }
        
        execute(killed);
        
        piece.finishAttack();
        
        description = new AttackDescription(attackingTerritory, attacks, killed);
    }
    
    private int getRoll() {
        return dice.roll();
    }
    
    protected void execute(int killed) {}
    
    protected int modifyAttack(Minion minion) {
        return piece.modifyAttack(minion);
    }
    
    protected boolean isSlayer(Minion minion) {        
        return builder.isSlayer(piece, minion.getTeam());
    }
    
    protected int rollToDamage(Minion minion) {
        return minion.getRollToDamage();
    }
    
    @Override
    public String toString() {
        return "Attack the minions";
    }

    public AttackDescription summary() {
        return description;
    }

    public String render() {
        return new AttackDescriptionRenderer().render(description);
    }
    
}
