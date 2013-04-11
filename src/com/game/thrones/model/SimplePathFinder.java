
package com.game.thrones.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author James
 */
public class SimplePathFinder implements PathFinder {
    
    private final Board board;
    
    public SimplePathFinder(final Board board) {
        this.board = board;
    }

    public List<Territory> getPathToTerritory(Territory start, Territory finish) {
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
        
            for (Territory border : board.getBorderingTerritories(current)) {
                if (visited.contains(border)) {
                    continue;
                }
                
                queue.add(border);
                route.put(border, current);
            }
        }
        
        throw new IllegalStateException("No path found");
    }
    
    private List<Territory> extractPathFromMap(final Map<Territory, Territory> route, final Territory finish) {
        List<Territory> path = new ArrayList<Territory>();
        
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

}
