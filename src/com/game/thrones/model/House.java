
package com.game.thrones.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author James
 */
public class House {
    
    public static final String PLAYER_ONE = "Player One";
    
    private static final House NO_ONE = new House("null", HouseType.MINOR);
    
    public enum HouseType {
        
        KING(5), MAJOR(10), MINOR(0);        
        
        private int startingTaxRate;
        
        HouseType(int startingTaxRate) {
            this.startingTaxRate = startingTaxRate;
        }
    }
    
    public enum PlayerType {AI, HUMAN}

    final private String name;
    final private HouseType houseType;
    final private PlayerType playerType;

    private House serves;
    //how this house sees other houses
    //this is only useful for AI players
    final private Map<House, Standing> houseStandings = new HashMap<House,Standing>();
    
    private int funds;    
    private int taxRate;
    
    public House(String name, HouseType houseType) {
        this.name = name;
        this.houseType = houseType;
        this.taxRate = houseType.startingTaxRate;
        this.playerType = PlayerType.AI;
    }

    public House(String name, HouseType houseType, PlayerType playerType) {
        this.name = name;
        this.houseType = houseType;
        this.taxRate = houseType.startingTaxRate;
        this.playerType = playerType;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the serves
     */
    public House getServes() {
        return serves == null ? NO_ONE : serves;
    }

    /**
     * @param serves the serves to set
     */
    public void setServes(House serves) {
        this.serves = serves;
    }

    /**
     * @return the houseType
     */
    public HouseType getHouseType() {
        return houseType;
    }
    
    public PlayerType getPlayerType() {
        return playerType;
    }

    /**
     * @return the houseStandings
     */
    public Map<House, Standing> getHouseStandings() {
        return houseStandings;
    }
    
    /**
     * Funds are the money that a house has.
     * @return the funds
     */
    public int getFunds() {
        return funds;
    }
        
    /**
     * @param money monies
     */
    public void addFunds(int money) {
        funds += money;
    }
    
    /**
     * @param money monies
     */
    public void removeFunds(int money) {
        funds -= money;
        if (funds <= 0) {
            funds = 0;
        }
    }

    /**
     * Tax rate is not used on servant houses.
     * @return the taxRate
     */
    public int getTaxRate() {
        return taxRate;
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
        final House other = (House) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
