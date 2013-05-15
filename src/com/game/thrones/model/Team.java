/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.thrones.model;

/**
 *
 * @author James
 */
public enum Team {
    
    //original teams
    ORCS(true),
    DRAGONS(true),
    OLD_DEMONS(false),
    UNDEAD(false),
    
    //new teams
    VAMPIRES(false),
    WOLVES(false),
    GHOULS(false),
    DEMONS(false),
    
    NO_ONE(true);
    
    private boolean enabled;
    
    Team(boolean enabled) {
        this.enabled = enabled;    
    }
    
    public boolean enabled() {
        return enabled;        
    }
    
    private static int total() {
        int sum = 0;
        
        for (Team team : Team.values()) {
            if (team.enabled()) {
                sum ++;
            }
        }
        
        return sum;
    }
    
    public static String[] getTeams(boolean includeNoone) {
        String[] items = new String[includeNoone ? Team.total() : Team.total() - 1];
        
        int i = 0;
        
        for (Team team : values()) {            
            if (team == Team.NO_ONE && !includeNoone || !team.enabled()) {
                continue;
            }
            
            items[i] = team.name();
            i ++;
        }
        
        return items;
    }
}
