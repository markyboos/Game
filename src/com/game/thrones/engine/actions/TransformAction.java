
package com.game.thrones.engine.actions;

import com.game.thrones.engine.Dice;
import com.game.thrones.engine.descriptions.ActionDescription;
import com.game.thrones.engine.descriptions.Describable;
import com.game.thrones.engine.descriptions.TransformDescription;
import com.game.thrones.engine.descriptions.TransformationRenderer;
import com.game.thrones.model.hero.DoctorJekyll;
import com.game.thrones.model.hero.DoctorJekyll.Form;

/**
 *
 * @author James
 */
public class TransformAction extends AbstractAction<DoctorJekyll> implements Describable<ActionDescription> {
    
    private boolean potion;
    
    private TransformDescription description;
    
    public TransformAction(DoctorJekyll hero, boolean potion) {
        super(hero);
        this.potion = potion;
    }

    public void execute() {
        
        Form oldForm = piece.getCurrentForm();
        
        Form newForm = potion ? usePotion() : rollDice();
        
        piece.transform(newForm);
        
        description = new TransformDescription(oldForm, newForm);
    }

    public ActionDescription summary() {
        return description;
    }

    public String render() {
        return new TransformationRenderer().render(description);
    }

    private Form rollDice() {        
        Dice dice = new Dice();
        
        return dice.roll() % 2 == 0 ? 
            DoctorJekyll.Form.MR_HYDE : DoctorJekyll.Form.DR_JEKYLL;
    }

    private Form usePotion() {
        return piece.getCurrentForm() == Form.MR_HYDE ? Form.DR_JEKYLL : Form.MR_HYDE;
    }

}
