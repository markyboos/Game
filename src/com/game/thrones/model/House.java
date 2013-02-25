
package com.game.thrones.model;

import java.util.Map;

/**
 *
 * @author James
 */
public class House implements Comparable<House>{

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
     * @param houseType the houseType to set
     */
    public void setHouseType(Type houseType) {
        this.houseType = houseType;
    }

    /**
     * @return the houseStandings
     */
    public Map<House, Standing> getHouseStandings() {
        return houseStandings;
    }

    /**
     * @param houseStandings the houseStandings to set
     */
    public void setHouseStandings(Map<House, Standing> houseStandings) {
        this.houseStandings = houseStandings;
    }

    @Override
    public int compareTo(House house) {
        return getName().compareTo(house.getName());
    }

    public enum Type {KING, MAJOR, MINOR}
    
    private String name;
    
    private House serves;
    
    private Type houseType;
    
    //how this house sees other houses    
    private Map<House, Standing> houseStandings;
    
    
    
}
