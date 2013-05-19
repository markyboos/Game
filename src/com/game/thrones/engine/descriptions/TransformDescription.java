
package com.game.thrones.engine.descriptions;

import com.game.thrones.model.hero.DoctorJekyll.Form;

/**
 *
 * @author James
 */
public class TransformDescription implements ActionDescription {
    
    private final Form oldForm;
    private final Form newForm;            
    
    public TransformDescription(Form oldForm, Form newForm) {
        this.oldForm = oldForm;
        this.newForm = newForm;
    }
    
    public Form getOldForm() {
        return oldForm;
    }
    
    public Form getNewForm() {
        return newForm;
    }
    
    

}
