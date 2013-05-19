
package com.game.thrones.engine.descriptions;

import com.game.thrones.model.hero.DoctorJekyll.Form;

/**
 *
 * @author James
 */
public class TransformationRenderer implements DescriptionRenderer<TransformDescription> {


    public String render(TransformDescription model) {
        StringBuilder buf = new StringBuilder();
        
        buf.append("A horrific feeling travels across your body...\n");
        
        if (model.getNewForm() == model.getOldForm()) {
            
            if (model.getNewForm() == Form.DR_JEKYLL) {            
                buf.append("but the monster inside you is quelled");
            } else {            
                buf.append("the monster continues to take control of your senses");
            }
            
        } else {

            if (model.getNewForm() == Form.DR_JEKYLL) {
                buf.append("you transform back into human form");
            } else {
                buf.append("you transform into a hideous beast");
            }
            
        }
        
        return buf.toString();
    }

}
