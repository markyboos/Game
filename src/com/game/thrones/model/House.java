
package com.game.thrones.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author James
 */
public class House {
    
    public enum Type {
        
        KING(5), MAJOR(10), MINOR(0);        
        
        private int startingTaxRate;
        
        Type(int startingTaxRate) {
            this.startingTaxRate = startingTaxRate;
        }
    }

    final private String name;
    final private Type houseType;

    private House serves;
    //how this house sees other houses
    final private Map<House, Standing> houseStandings = new HashMap<House,Standing>();
    
    private int funds;    
    private int taxRate;

    public House(String name, Type houseType) {
        this.name = name;
        this.houseType = houseType;
        this.taxRate = houseType.startingTaxRate;
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
        return serves;
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
    public Type getHouseType() {
        return houseType;
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
}
