
package com.game.thrones.engine;

import com.game.framework.SoundManager;
import com.game.thrones.MainActivity;
import com.game.thrones.engine.descriptions.AttackDescription;
import com.game.thrones.engine.descriptions.SingleAttackResult;
import com.game.thrones.model.Territory;
import com.game.thrones.model.TerritoryFilter;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Minion;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author James
 */
public class AttackAction extends AbstractAction<Hero> implements Describable<AttackDescription> {
    
    protected Territory attackingTerritory;

    public AttackAction(final Hero piece) {
        super(piece);
        
        attackingTerritory = piece.getPosition();
    }
    
    private Dice dice = new Dice();
    
    private AttackDescription description;

    public void execute() {
        
        playSoundEffect();
        
        List<Minion> minions = GameController.getInstance()
                .getBoard().getPieces(new TerritoryFilter(attackingTerritory), Minion.class);
        
        int killed = 0;
        
        Map<Minion, SingleAttackResult> attacks = new HashMap<Minion, SingleAttackResult>();
        
        for (Minion minion : minions) {
            //roll the dice
            
            boolean slayer = isSlayer(minion);
            int roll = getRoll(minion);
            int needed = rollToDamage(minion);
            
            attacks.put(minion, new SingleAttackResult(slayer, roll, needed));
            
            if (slayer || 
                    roll >= needed) {
                //remove the minion
                GameController.getInstance().getBoard().removePiece(minion);
                killed ++;
            }
        }
        
        execute(killed);
        
        piece.finishAttack();
        
        description = new AttackDescription(attackingTerritory, attacks, killed);
    }
    
    private int getRoll(Minion minion) {
        return dice.roll() + modifyAttack(minion);
    }
    
    protected void execute(int killed) {}
    
    protected int modifyAttack(Minion minion) {
        return piece.modifyAttack(minion);
    }
    
    protected boolean isSlayer(Minion minion) {
        return piece.isSlayer(minion.getTeam());
    }
    
    protected int rollToDamage(Minion minion) {
        return minion.getRollToDamage();
    }
    
    protected void playSoundEffect() {
        SoundManager.getSingleton().playSound(MainActivity.SWORDFIGHT);        
    }
    
    @Override
    public String toString() {
        return "Attack the minions";
    }

    public AttackDescription summary() {
        return description;
    }
    
}
