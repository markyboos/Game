
package com.game.thrones.model;

/**
 *
 * @author James
 */
public class TerritoryCriteria {
    
    public enum Operator {LESS_THAN, MORE_THAN, EQUAL};
    
    private Integer minions;
    
    private Team minionTeam;
    
    private Team owner;
    
    private Territory bordering;
    
    private Operator minionCountOperator = Operator.EQUAL;
    
    private Boolean tainted;

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
    
    public Operator getMinionCountOperator() {
        return minionCountOperator;
    }
    
    public void setMinionCountOperator(Operator operator) {
        minionCountOperator = operator;
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

    public Territory getBordering() {
        return bordering;
    }

    /**
     * @param bordering the bordering to set
     */
    public void setBordering(Territory bordering) {
        this.bordering = bordering;
    }
    
    public void setTainted() {
        this.tainted = true;
    }
    
    public Boolean isTainted() {
        return tainted;
    }
}
