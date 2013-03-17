
package com.game.thrones.engine;

import java.util.Comparator;

/**
 *
 * @author James
 */
public class ActionComparator implements Comparator<Action> {

    public int compare(Action actionOne, Action actionTwo) {
        int sort = actionOne.executionStep()
                .compareTo(actionTwo.executionStep());
        
        if (sort == 0) {
            sort = actionOne.getPiece().getName().compareTo(actionTwo.getPiece().getName());
        }
        
        return sort;
    }

}
