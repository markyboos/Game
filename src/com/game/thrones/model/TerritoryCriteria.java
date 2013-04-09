
package com.game.thrones.model;

/**
 *
 * @author James
 */
public class TerritoryCriteria {
    
    private Integer minions;
    
    private Team minionTeam;
    
    private Team owner;

    /**
     * @return the minions
     */
    public Integer getMinionCount() {
        return minions;
    }

    /**
     * @param minions the minions to set
     */
    public void setMinionCount(int minions) {
        this.minions = minions;
    }

    /**
     * @return the minionTeam
     */
    public Team getMinionTeam() {
        return minionTeam;
    }

    /**
     * @param minionTeam the minionTeam to set
     */
    public void setMinionTeam(Team minionTeam) {
        this.minionTeam = minionTeam;
    }

    public void setOwner(Team team) {
        this.owner = team;
    }
    
    public Team getOwner() {
        return owner;
    }

}
