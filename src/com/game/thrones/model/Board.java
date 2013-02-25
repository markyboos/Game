package com.game.thrones.model;

import java.util.*;

/**
 * User: Jimmy
 * Date: 24/02/13
 * Time: 23:09
 *
 * Class to model the board, consisting of territories and their borders and ownership. Abstracts these concepts away from
 * the territory themselves.
 *
 * Provides utility methods (such as determining if two territories are bordering)
 * Provides methods for modifying the state of the game (e.g. changing ownership of a territory)
 */
public class Board {

    final private List<Territory> territories;
    final private Map<Territory, House> ownershipMap;
    final private Set<House> houses;
    final private Set<Hero> heroes;

    /**
     * 2D array representing the borders of the territories.
     * 1  = bordering
     * 0  = not bordering
     * -1 = same territory
     */
    private int[][] borders;

    public Board(Map<Territory, Set<Territory>> landMap, Map<Territory, House> ownership, Set<House> houses, Set<Hero> heroes) {

        this.borders = new int[landMap.size()][landMap.size()];
        territories = Collections.unmodifiableList(new ArrayList<Territory>(landMap.keySet()));

        //Populate the 2D array based on the borders.
        for(Territory t : landMap.keySet()) {
            int t1Id = territories.indexOf(t);

            borders[t1Id][t1Id] = -1;

            for(Territory bordering : landMap.get(t)) {
                int t2Id = territories.indexOf(bordering);
                borders[t1Id][t2Id] = 1;
            }
        }

        /**
         * Validate borders; All borders should be two-way.
         * [i][j] should have the same value as [j][i] in all instances.
         */
        for(int i = 0; i < territories.size(); i++) {
            for(int j = 0; j < territories.size(); j++) {
                if(borders[i][j] != borders[j][i]) {
                    throw new IllegalArgumentException("The map defines inconsistent borders");
                }
            }
        }

        this.ownershipMap = ownership;
        this.houses = houses;
        this.heroes = heroes;
    }

    /**
     * Retrieves a list of bordering territories
     * @param territory The territory for whom we're retrieving the bordering ones.
     * @return a list of bordering territories
     */
    public List<Territory> getBorderingTerritories(Territory territory) {
        List<Territory> borderingTerritories = new ArrayList<Territory>();

        int territoryId = territories.indexOf(territory);
        if(territoryId == -1) {
            throw new IllegalArgumentException("Territory is not a part of the board.");
        }

        for(int i = 0; i < territories.size(); i++) {
            if(borders[territoryId][i] == 1) {
                borderingTerritories.add(territories.get(i));
            }
        }

        return borderingTerritories;
    }

    /**
     * Determine if two territories are bordering.
     * @param t1 1st territory to compare
     * @param t2 2nd territory to compare.
     * @return true if the two territories share a border.
     */
    public boolean areBordering(Territory t1, Territory t2) {
        if(territories.indexOf(t1) == -1 || territories.indexOf(t2) == -1) {
            throw new IllegalArgumentException("One of the territories is not part of the board");
        }

        return borders[territories.indexOf(t1)][territories.indexOf(t2)] == 1;
    }

    public House retrieveOwner(Territory t1) {
        return ownershipMap.get(t1);
    }

    public void changeOwnership(Territory t1, House newOwner) {
        ownershipMap.put(t1, newOwner);
    }

    public boolean moveHero(Hero hero, Territory targetLocation) {
        Territory currentLocation = hero.getPosition();

        if(areBordering(currentLocation, targetLocation) ) {
            hero.setPosition(targetLocation);
            return true;
        }

        return false;
    }
}
