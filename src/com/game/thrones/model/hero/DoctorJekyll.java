
package com.game.thrones.model.hero;

import com.game.thrones.engine.actions.Action;
import com.game.thrones.engine.actions.TransformAction;

/**
 *
 * @author James
 */
public class DoctorJekyll extends Hero implements StartActionHero {
    
    private static final String DOCTOR_JEKYLL = "Doctor Jekyll";
    
    public enum Form {MR_HYDE, DR_JEKYLL};
    
    private Form currentForm = Form.DR_JEKYLL;
    
    public DoctorJekyll() {
        super(DOCTOR_JEKYLL, 5);
        
        //potions
        for (int i = 0; i < 3 ; i++) {
            addItem(createPotion());
        }
    }
    
    public void transform(Form transformation) {
        this.currentForm = transformation;
        
        this.name = Form.DR_JEKYLL == currentForm ? DOCTOR_JEKYLL : "Mr Hyde";        
    }
    
    public Form getCurrentForm() {
        return currentForm;
    }
    
    //hyde is super strong
    @Override
    public int modifyAttack(Minion minion) {        
        return modifyAttack();
    }
    
    @Override
    public int modifyAttack(General general) {        
        return modifyAttack();  
    }

    private int modifyAttack() {
        return currentForm == Form.MR_HYDE ? 2 : 0;
    }
    
    public Action getStartingAction() {
        return new TransformAction(this, false);
    }
    
    private ActionItem createPotion() {
        return new ActionItem("Foul Potion", "Transform yourself", new TransformAction(this, true));
    }
}
