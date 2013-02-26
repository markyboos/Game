package com.game.thrones.model;

import com.game.thrones.model.piece.Piece;

import java.util.*;

/**
 * User: Jimmy
 * Date: 24/02/13
 * Time: 23:09
 *
 * Class to model the board, consisting of territories and their borders. Abstracts these concepts away from
 * the territory themselves.
 *
 * Provides utility methods (such as determining if two territories are bordering)
 * Provides methods for modifying the state of the game (e.g. moving pieces)
 */
public class Board {

    final private List<Territory> territories;
    final private Set<House> houses;
    final private Set<Piece> pieces;

    /**
     * 2D array representing the borders of the territories.
     * 1  = bordering
     * 0  = not bordering
     * -1 = same territory
     */
    private int[][] borders;

    /**
     * Construct the board; the main model of the game state.
     *
     * @param landMap A map of all the territories and their borders.
     * @param houses A set of all the houses on the board.
     * @param pieces A set of all the pieces on the board.
     */
    public Board(Map<Territory, Set<Territory>> landMap, Set<House> houses, Set<Piece> pieces) {

        this.borders = new int[landMap.size()][landMap.size()];
        territories = Collections.unmodifiableList(new ArrayList<Territory>(landMap.keySet()));

        //Populate the 2D array based on the borders.
        for(Territory t : landMap.keySet()) {
            int t1Id = territories.indexOf(t);

            borders[t1Id][t1Id] = -1;

            for(Territory bordering : landMap.get(t)) {
                int t2Id = territories.indexOf(bordering);

                if(t2Id == -1) {
                    throw new IllegalArgumentException("Bordering territory is not part of the board");
                }

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

        this.houses = houses;
        this.pieces = pieces;
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

        return Collections.unmodifiableList(borderingTerritories);
    }

    /**
     * Retrieves a list of territories allied to a given house.
     * An allied territory is either owned by the house, or owned by a house that serves it.
     * @param house The house to retrieve a list of owned territories for.
     * @return A list of owned territories.
     */
    public List<Territory> getAlliedTerritories(House house) {
        List<Territory> alliedTerritories = new ArrayList<Territory>();

        for(Territory t : territories) {
            if(t.getOwner() == house || t.getOwner().getServes() == house) {
                alliedTerritories.add(t);
            }
        }

        return Collections.unmodifiableList(alliedTerritories);
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

    /**
     * Utility function to move a piece; checks the two territories are bordering.
     * @param piece Piece to move.
     * @param targetLocation Territory to move to
     * @return true if the piece was moved; false otherwise.
     */
    public boolean movePiece(Piece piece, Territory targetLocation) {
        Territory currentLocation = piece.getPosition();

        if(areBordering(currentLocation, targetLocation)) {

            piece.setPosition(targetLocation);
            return true;
        }

        return false;
    }
    
    //
    //The following methods are just in so i can start working on some activities.
    //They should be removed when we decide how to do them properly.
    //
    public Set<House> getHouses() {
        return houses;
    }
    
    public Piece getPiece(final String name) {
        for (Piece piece : pieces) {
            if (piece.getName().equals(name)) {
                return piece;
            }
        }
        
        throw new IllegalArgumentException("Invalid piece");        
    }
    
    public Set<Piece> getPieces(House house) {        
        Set<Piece> housePieces = new HashSet<Piece>();
        
        for (Piece piece : pieces) {
            if (piece.getHouse().equals(house)) {
                housePieces.add(piece);                
            }
        }        
        
        return housePieces;
    }
}
