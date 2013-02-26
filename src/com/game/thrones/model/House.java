
package com.game.thrones.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author James
 */
public class House {
    public enum Type {KING, MAJOR, MINOR}

    final private String name;
    final private Type houseType;

    private House serves;
    //how this house sees other houses
    final private Map<House, Standing> houseStandings = new HashMap<House,Standing>();

    public House(String name, Type houseType) {
        this.name = name;
        this.houseType = houseType;
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
}
