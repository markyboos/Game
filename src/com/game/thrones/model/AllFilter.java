
package com.game.thrones.model;

/**
 *
 * @author James
 */
public class AllFilter<T> implements Filter<T> {
    
    public static final AllFilter INSTANCE = new AllFilter();

    public boolean valid(T piece) {
        return true;
    }
}
