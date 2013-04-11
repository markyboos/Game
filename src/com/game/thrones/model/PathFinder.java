
package com.game.thrones.model;

import java.util.List;

/**
 *
 * @author James
 */
public interface PathFinder {
    
    public List<Territory> getPathToTerritory(Territory start, Territory finish);

}
