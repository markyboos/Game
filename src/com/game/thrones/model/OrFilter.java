
package com.game.thrones.model;

/**
 *
 * @author James
 */
public class OrFilter<T> implements Filter<T> {
    
    private Filter[] filters;
    
    public OrFilter(Filter... filters) {
        this.filters = filters;                
    }

    public boolean valid(T t) {
        for (Filter filter : filters) {
            if (filter.valid(t)) {
                return true;
            }
        }
        
        return false;
    }

}
