
package com.game.thrones.model;

/**
 *
 * @author James
 */
public class Standing {
    
    //this is a 0-10 scale 0 being all out war and 10 being complete peace
    int relationship;
    
    public Standing() {
        relationship = 5;
    }
    
    public Standing(int relationship) {
        this.relationship = relationship;
    }
    
    public void ruined() {
       relationship = 0;
    }
    
    public void didBadThing() {
        relationship --;
        if (relationship < 0) {
            relationship = 0;
        }
    }
    
    public void didGoodThing() {
        relationship ++;
        if (relationship > 10) {
            relationship = 10;
        }
    }
    
    public int getRelationship() {
        return relationship;
    }
    
    public boolean atWar() {
        return relationship < 2;
    }
    
    @Override
    public String toString() {
        return Integer.toString(relationship);
    }

}
