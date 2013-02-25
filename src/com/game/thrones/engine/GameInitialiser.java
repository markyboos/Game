
package com.game.thrones.engine;

import com.game.thrones.model.Board;
import com.game.thrones.model.Hero;
import com.game.thrones.model.House;
import com.game.thrones.model.House.Type;
import com.game.thrones.model.Territory;

import java.util.*;

/**
 *
 * @author James
 */
public class GameInitialiser {
    
    private Set<House> houses = new HashSet<House>();
    private Set<Hero> heroes = new HashSet<Hero>();

    private Map<Territory, House> ownershipMap = new HashMap<Territory, House>();
    private Map<Territory, Set<Territory>> borderMap = new HashMap<Territory, Set<Territory>>();
    
    private void initialise() {
        
        //start with the king and 2 other major houses
        //to see how this goes
        House kingsHouse = createHouse("The kings house", Type.KING);                
        House minorOne = createHouse("Scumbag Minor One", Type.MINOR, kingsHouse);        
        House minorTwo = createHouse("Honorable Minor One", Type.MINOR, kingsHouse);
                
        House majorOne = createHouse("Player One", Type.MAJOR);        
        House minorThree = createHouse("Honorable Minor Two", Type.MINOR, majorOne);
                
        House majorTwo = createHouse("Player Two", Type.MAJOR);
        House minorFour = createHouse("Honorable Minor Three", Type.MINOR, majorTwo);  
                
        House neutralMinor = createHouse("Wild men", Type.MINOR);
        
        // territories        
        Territory kingsLanding = createTerritory("Kings landing", 20, kingsHouse);
        Territory winterfell = createTerritory("Winterfell", 10, majorOne);
        Territory rock = createTerritory("Castle rock", 10, majorTwo);
        Territory bogland = createTerritory("Bogland", 2, minorOne);
        Territory desert = createTerritory("Desert", 2, minorTwo);
        Territory coast = createTerritory("Coastal city", 4, minorThree);
        Territory forest = createTerritory("Forest town", 3, minorFour);
        Territory outlands = createTerritory("Outlands", 1, neutralMinor);
        
        //conecting territories
        addBorder(kingsLanding, bogland);
        addBorder(kingsLanding, desert);
        addBorder(kingsLanding, outlands);
        addBorder(coast, desert);
        addBorder(coast, rock);
        addBorder(forest, bogland);
        addBorder(forest, winterfell);
        
        //heroes
        createHero("The King", kingsHouse, kingsLanding);
        createHero("Stinky", minorOne, kingsLanding);
        createHero("Champy", minorTwo, kingsLanding);
        
        createHero("Player one", majorOne, winterfell);
        createHero("The cruncher", minorThree, winterfell);
        
        createHero("Player two", majorTwo, rock);
        createHero("Goldy", minorThree, rock);
        
        createHero("smelly", neutralMinor, outlands);        
        
        
    }
    
    private House createHouse(String name, Type type, House serves) {
        House house = new House();
        house.setName(name);
        house.setHouseType(type);
        house.setServes(serves);
        
        houses.add(house);
        
        return house;
    }
    
    private House createHouse(String name, Type type) {
        return createHouse(name, type, null);        
    }

    private Territory createTerritory(String name, int value, House ownedBy) {
        Territory t1 = new Territory(name, value);

        ownershipMap.put(t1, ownedBy);
        borderMap.put(t1, new HashSet<Territory>());

        return t1;
    }

    private void addBorder(Territory t1, Territory t2) {
        if(!borderMap.containsKey(t1) || !borderMap.containsKey(t2)) {
            throw new IllegalArgumentException("Trying to add a border to an unknown territory");
        }

        borderMap.get(t1).add(t2);
        borderMap.get(t2).add(t1);
    }

    Board createBoard() {
        initialise();

        return new Board(borderMap, ownershipMap, houses, heroes);
    }
    
    private Hero createHero(String name, House house, Territory position) {
        
        Hero hero = new Hero();
        
        hero.setName(name);
        hero.setHouse(house);
        hero.setPosition(position);
        
        heroes.add(hero);
        
        return hero;
        
    }

}
