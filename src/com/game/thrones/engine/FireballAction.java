
package com.game.thrones.engine;

import com.game.framework.SoundManager;
import com.game.thrones.MainActivity;
import com.game.thrones.model.hero.Item;
import com.game.thrones.model.hero.Minion;
import com.game.thrones.model.hero.Wizard;

/**
 *
 * @author James
 */
public class FireballAction extends AttackAction implements ItemSelectAction {

    private Item item;

    public FireballAction(final Wizard wizard) {
        super(wizard);
    }
    
    public void setItem(final Item item) {
        this.item = item;
    }

    @Override
    public void execute() {
        piece.useItem(item);
        
        super.execute();
    }
    
    @Override
    protected void playSoundEffect() {
        SoundManager.getSingleton().playSound(MainActivity.FIREBALL);        
    }

    @Override
    protected int rollToDamage(Minion minion) {
        return 2;
    }
    
    @Override
    public String toString() {
        return "Burn the muthas";
    }
}
