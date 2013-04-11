package com.game.thrones.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author James
 */
public class DijkstraPathFinder implements PathFinder {

    private Set<Territory> settledNodes = new HashSet<Territory>();
    private Set<Territory> unSettledNodes = new HashSet<Territory>();
    private Map<Territory, Territory> predecessors = new HashMap<Territory, Territory>();
    private Map<Territory, Integer> distance = new HashMap<Territory, Integer>();
    
    private Board board;

    public DijkstraPathFinder(final Board board) {
        this.board = board;    
    }

    public List<Territory> getPathToTerritory(Territory start, Territory finish) {
        
        if (start.equals(finish)) {
            return Collections.emptyList();
        }
        
        distance.put(start, 0);
        unSettledNodes.add(start);
        
        while (unSettledNodes.size() > 0) {
            Territory node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
        
        return getPath(finish);
    }

    private void findMinimalDistances(Territory node) {
        List<Territory> adjacentNodes = board.getBorderingTerritories(node);
        for (Territory target : adjacentNodes) {
            if (settledNodes.contains(target)) {
                continue;
            }            
            
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private int getDistance(Territory node, Territory target) {
        return 1;
    }

    private Territory getMinimum(Set<Territory> vertexes) {
        Territory minimum = null;
        for (Territory vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private int getShortestDistance(Territory destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Territory> getPath(Territory target) {
        LinkedList<Territory> path = new LinkedList<Territory>();
        Territory step = target;
        // Check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        path.remove(0);
        return path;
    }
}
