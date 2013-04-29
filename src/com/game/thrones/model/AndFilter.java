
package com.game.thrones.model;

/**
 *
 * @author James
 */
public class AndFilter<T> implements Filter<T> {
    
    private Filter[] filters;
    
    public AndFilter(Filter... filters) {
        this.filters = filters;                
    }

    public boolean valid(T t) {
        for (Filter filter : filters) {
            if (!filter.valid(t)) {
                return false;
            }
        }
        
        return true;
    }

}
