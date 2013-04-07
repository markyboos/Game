
package com.game.thrones.model.hero;

import com.game.thrones.model.Team;

/**
 *
 * @author James
 */
public class Item implements CharSequence{
    
    private final int power;
    
    private final Team team;
    
    public Item(int power, Team team) {
        this.power = power;
        this.team = team;
    }

    public int getPower() {
        return power;
    }
    
    public Team getTeam() {
        return team;
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
        return team + " " + power;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.power;
        hash = 53 * hash + (this.team != null ? this.team.hashCode() : 0);
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
        final Item other = (Item) obj;
        if (this.power != other.power) {
            return false;
        }
        if (this.team != other.team) {
            return false;
        }
        return true;
    }
    

}
