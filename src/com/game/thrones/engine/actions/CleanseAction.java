
package com.game.thrones.engine.actions;

import com.game.thrones.engine.descriptions.Describable;
import com.game.thrones.engine.Dice;
import com.game.thrones.engine.descriptions.CleanseDescription;
import com.game.thrones.engine.descriptions.CleanseDescriptionRenderer;
import com.game.thrones.engine.descriptions.DiceRollResult;
import com.game.thrones.model.Filter;
import com.game.thrones.model.hero.DoctorJekyll;
import com.game.thrones.model.hero.DoctorJekyll.Form;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.item.AbstractItem;
import com.game.thrones.model.item.ItemTeamFilter;
import com.game.thrones.model.hero.Sorceress;
import com.game.thrones.model.item.AttackGeneralItem;
import com.game.thrones.model.item.Item;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author James
 */
public class CleanseAction extends AbstractAction<Hero> implements ItemSelectAction, Describable<CleanseDescription> {
    
    private AttackGeneralItem itemToUse;
    
    private CleanseDescription description;
    
    public CleanseAction(final Hero piece) {
        super(piece);
    }
    
    Dice dice = new Dice();
    
    public void setItem(AttackGeneralItem item) {
        itemToUse = item;        
    }
    
    public Filter<AttackGeneralItem> getItemFilter() {
        return new ItemTeamFilter(piece.getPosition().getOwner());
    }

    public void execute() {
        
        piece.useAction();
        
        //use a card
        piece.disposeItem(itemToUse);
        
        int toRoll = 5;
        
        if (piece instanceof Sorceress) {
            toRoll = 4;
        } else if (piece instanceof DoctorJekyll) {
            DoctorJekyll jekyll = (DoctorJekyll)piece;
            if (jekyll.getCurrentForm() == Form.DR_JEKYLL) {
                toRoll = 0;
            }
        }
        
        DiceRollResult rollOne = dice.roll(toRoll);
        DiceRollResult rollTwo = dice.roll(toRoll);
        
        if (rollOne.success() || rollTwo.success()) {
            piece.getPosition().removeTaint();                        
        }
        
        List<DiceRollResult> rolls = new ArrayList<DiceRollResult>();
        rolls.add(rollOne);
        rolls.add(rollTwo);
        
        description = new CleanseDescription(rolls);
    }
    
    @Override
    public String toString() {
        return "Heal the land";
    }

    public CleanseDescription summary() {
        return description;
    }

    public String render() {
        return new CleanseDescriptionRenderer().render(description);
    }

}
