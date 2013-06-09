
package com.game.thrones.model.item;

import com.game.thrones.engine.descriptions.ActionDescription;
import com.game.thrones.model.Team;

/**
 * todo this is poop
 * 
 * @author James
 */
public class AbstractItem implements Item {
    
    private final String name;    
    private final String description;
    
    public AbstractItem(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getName() {
        return name;
    }
    
    public int length() {
        return toString().length();
    }

    public char charAt(int arg0) {
        return toString().charAt(arg0);
    }

    public CharSequence subSequence(int arg0, int arg1) {
        return toString().subSequence(arg0, arg1);
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractItem other = (AbstractItem) obj;
        if ((this.name == null) ? (other.getName() != null) : !this.name.equals(other.getName())) {
            return false;
        }
        return true;
    }

}
