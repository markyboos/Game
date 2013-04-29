
package com.game.thrones.model;

/**
 *
 * @author James
 */
public interface Filter<T> {
    
    public boolean valid(T t);

}
