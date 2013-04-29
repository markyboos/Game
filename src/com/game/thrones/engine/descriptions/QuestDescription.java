
package com.game.thrones.engine.descriptions;

import com.game.thrones.model.Requirement;

/**
 *
 * @author James
 */
public class QuestDescription implements ActionDescription {
    
    final Requirement requirement;

    /**
     * Constructor for failed quest.
     * 
     * @param requirement 
     */
    public QuestDescription(Requirement requirement) {
        this.requirement = requirement;
    }

}
