package com.game.thrones.model;

import android.util.Log;
import com.game.thrones.model.piece.Piece;
import java.lang.annotation.Target;

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
     * Should return a path.
     * 
     * @param start
     * @param finish
     * @return 
     */    
    public Set<Territory> getPathToTerritory(Territory start, Territory finish) {
        
        Queue<Territory> queue = new LinkedList<Territory>();
        Set<Territory> visited = new HashSet<Territory>();
        Map<Territory, Territory> route = new LinkedHashMap<Territory, Territory>();
        queue.add(start);
        
        while (queue.isEmpty() == false) {
            
            Territory current = queue.poll();
            
            if (current == finish) {
                return extractPathFromMap(route, finish);
            }
            
            visited.add(current);
        
            for (Territory border : getBorderingTerritories(current)) {
                if (visited.contains(border)) {
                    continue;
                }
                
                queue.add(border);
                route.put(border, current);
            }
        }
        
        throw new IllegalStateException("No path found");
    }
    
    private Set<Territory> extractPathFromMap(final Map<Territory, Territory> route, final Territory finish) {
        Set<Territory> path = new LinkedHashSet<Territory>();
        
        Territory current = finish;
        path.add(current);
        
        while(true) {
            current = route.get(current);
            if (current == null) {
                break;
            }
            path.add(current);
        }
        
        System.out.println("found path....");
        for (Territory visit : path) {
            System.out.println("visited :" + visit);
        }
        
        return path;
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
    
    public Set<Piece> getPieces(final Territory territory) {
        Set<Piece> territoryPieces = new HashSet<Piece>();
        
        for (Piece piece : pieces) {
            if (piece.getPosition().equals(territory)) {
                territoryPieces.add(piece);
            }
        }
        
        return Collections.unmodifiableSet(territoryPieces);
    }
    
    public List<Piece> getPieces(final PieceCriteria criteria) {
        List<Piece> foundPieces = new ArrayList<Piece>();
        
        for (Piece piece : pieces) {
            
            boolean found = true;
            
            if (criteria.getTerritory() != null && !piece.getPosition().equals(criteria.getTerritory())) {
                found = false;
            }
            
            if (criteria.getType() != null && !criteria.getType().isAssignableFrom(piece.getClass())) {
                found = false;            
            }
            
            if (criteria.getOwner() != null && !piece.getHouse().equals(criteria.getOwner())) {
                found = false;
            }
            
            if (found) {
                Log.d("Board:getPieces", "Found " + piece);
                foundPieces.add(piece);
            }
        }
        
        return Collections.unmodifiableList(foundPieces);        
    }
    
    public void removePiece(final Piece pice) {
        pieces.remove(pice);
    }
    
    public void addPiece(final Piece piece) {
        pieces.add(piece);
    }
    
    //
    //todo
    //The following methods are just in so i can start working on some activities.
    //They should be removed when we decide how to do them properly.
    //
    
    public Territory getRandomTerritory() {
        List<Territory> randomChoice = new ArrayList(territories);
        Collections.shuffle(randomChoice);
        
        return randomChoice.get(0);
    }
    
    public List<Territory> getTerritories() {
        return Collections.unmodifiableList(territories);
    }
    
    public Territory getCentralTerritory() {
        for (Territory territory : territories) {
            if (territory.getName().equals(Territory.KINGS_LANDING)) {
                return territory;                
            }
        }
        
        throw new IllegalStateException("No kings landing territory");
    }
            
    public Set<House> getHouses() {
        return Collections.unmodifiableSet(houses);
    }
    
    public House getHouse(final String name) {
        for (House house : houses) {
            if (house.getName().equals(name)) {
                return house;
            }
        }
        throw new IllegalArgumentException("Invalid house name");
    }
    
    public Piece getPiece(final String name) {
        for (Piece piece : pieces) {
            if (piece.getName().equals(name)) {
                return piece;
            }
        }
        
        throw new IllegalArgumentException("Invalid piece name");        
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
