package com.game.thrones.model;

import android.util.Log;
import com.game.thrones.model.hero.Minion;
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
    public Board(Map<Territory, Set<Territory>> landMap, Set<Piece> pieces) {

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
    
    public Set<Territory> getTerritoriesDistanceAway(Territory territory, int distance) {
        Map<Territory, Integer> away = getTerritoriesAway(territory, 0, distance);
        
        Set<Territory> gathered = new HashSet<Territory>();
        
        for (Map.Entry<Territory, Integer> entry : away.entrySet()) {
            Log.d("getTerritoriesAway", "[" +entry.getKey() + "] distance away[" +  entry.getValue() + "]");
            if (entry.getValue() == distance) {
                gathered.add(entry.getKey());
            }            
        }
        
        return Collections.unmodifiableSet(gathered);
    }
    
    public Map<Territory, Integer> getTerritoriesAway(Territory territory, int current, int distance) {
        if (current == distance) {
            return Collections.emptyMap();
        }
        
        Map<Territory, Integer> away = new HashMap<Territory, Integer>();
        
        if (current == 0) {
            //starting position
            away.put(territory, 0);
        }
        
        for (Territory bordering : getBorderingTerritories(territory)) {
            away.put(bordering, current + 1);
        }
        
        Iterator<Territory> iter = away.keySet().iterator();
        
        Map<Territory, Integer> found = new HashMap<Territory, Integer>();
        
        while(iter.hasNext()) {
            found.putAll(getTerritoriesAway(iter.next(), current + 1, distance));
        }
        
        for (Territory key : found.keySet()) {
            if (!away.containsKey(key)) {
                away.put(key, found.get(key));                
            }            
        }
        
        return away;
        
    }
    
    private PathFinder pathFinder = new DijkstraPathFinder(this);
    
    /**
     * Should return a path.
     * 
     * @param start
     * @param finish
     * @return 
     */    
    public List<Territory> getPathToTerritory(Territory start, Territory finish) {      
        
        List<Territory> path = pathFinder.getPathToTerritory(start, finish);
        
        //Log.d("board:getPathToTerritory", start.getName() + " -> " + finish.getName());
        //for (Territory p : path) {
        //    Log.d("board:getPathToTerritory", p.getName());
        //}
        
        return path;
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
    
    /**
     * Utility function to move a piece some distance on the board.     * 
     * @param piece Piece to move
     * @param destination Territory to move to
     */
    public void movePieceFar(Piece piece, Territory destination) {
        piece.setPosition(destination);
    }
    
    public <P extends Piece> List<P> getPieces(final PieceFilter filter, final Class<P> klass) {
        List<P> foundPieces = new ArrayList<P>();
        
        //Log.d("getPieces", "Doing search....");

        for (Piece piece : pieces) {
            if (klass.isAssignableFrom(piece.getClass())
                    && filter.valid(piece)) {
                //Log.d("getPieces", piece + " is valid  at position [" + piece.getPosition() + "] and team [" + piece.getTeam() + "]");
                foundPieces.add((P) piece);
            }
        }

        return foundPieces;
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
    
    public List<Territory> getTerritories(TerritoryCriteria criteria) {
        
        List<Territory> foundTerritories = new ArrayList<Territory>();        
        
        for (Territory territory : territories) {
            
            List<Minion> piecesAtTerritory = getPieces(new TerritoryFilter(territory), Minion.class);
                        
            if (criteria.getMinionCount() != null) {                
                int actual = piecesAtTerritory.size();
                int shouldBe = criteria.getMinionCount();
                
                //Log.d("Board:getTerritories", territory.getName() + " " + actual + " " + criteria.getMinionCountOperator() + " " + shouldBe);
                switch (criteria.getMinionCountOperator()) {
                    case LESS_THAN:
                        if (shouldBe <= actual) {
                            continue;
                        }
                        break;
                    case MORE_THAN:
                        //Log.d("Board:getTerritories", "checking more than");
                        if (shouldBe >= actual) {
                            continue;
                        }
                        break;
                    case EQUAL:
                        if (actual != shouldBe) {
                            continue;
                        }
                        break;
                }
            }
            
            if (criteria.getOwner() != null && territory.getOwner() != criteria.getOwner()) {
                continue;
            }
            
            if (criteria.getMinionTeam() != null) {
                       
                for (Piece piece : piecesAtTerritory) {
                    if (piece.getTeam() != criteria.getMinionTeam()) {
                        continue;
                    }
                }
            }
            
            if (criteria.getBordering() != null && 
                    !getBorderingTerritories(territory).contains(criteria.getBordering())) {
                continue;                                                
            }
            
            //Log.d("Board:getTerritories", "Found " + territory);
            foundTerritories .add(territory);
        }
        
        return Collections.unmodifiableList(foundTerritories );  
        
    }
    
    public List<Territory> getTerritories() {
        return Collections.unmodifiableList(territories);
    }
    
    public Territory getCentralTerritory() {
        return getTerritory(Territory.CENTRAL_TERRITORY);
    }
    
    public Territory getTerritory(final String name) {
        for (Territory territory : territories) {
            if (territory.getName().equals(name)) {
                return territory;                
            }
        }
        
        throw new IllegalStateException("No territory called [" + name + "]");
        
    }
    
    public Piece getPiece(final String name) {
        for (Piece piece : pieces) {
            if (piece.getName().equals(name)) {
                return piece;
            }
        }
        
        throw new IllegalArgumentException("Invalid piece name");        
    }
    
    public void addMinionToTerritory(final Territory territory, final Team team,
            final boolean overrun) {
        
        List<Minion> minions = getPieces(new TerritoryFilter(territory), Minion.class);
        boolean isCentre = territory.equals(getCentralTerritory());
        
        if (minions.size() < 3 || isCentre) {
            Minion minion = new Minion(team);
        
            addPiece(minion);
            
            minion.setPosition(territory);
            
            if (minions.size() == 2 && allMinionsAreDemons(minions, minion) 
                    && !isCentre) {
                territory.taint();
            }
            
        } else {
            //taint the board
            territory.taint();
            
            if (!overrun) {
                return;
            }
            
            //and spread the minions (cause an overrun)
            List<Territory> bordering = getBorderingTerritories(territory);
            for (Territory border : bordering) {
                
                if (border.getOwner() == Team.NO_ONE && !border.equals(getCentralTerritory())) {
                    continue;
                }
                
                addMinionToTerritory(border, team, false);
            }
        }
    }

    private boolean allMinionsAreDemons(final List<Minion> minions, Minion added) {
                
        //if all the minions are demons taint it
        for (Piece p : minions) {
            if (p.getTeam() != Team.DEMONS) {
                return false;
            }
        }
        
        if (added.getTeam() != Team.DEMONS) {
            return false;
        }
        
        return true;
    }
}
