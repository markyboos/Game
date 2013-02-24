
package com.game.thrones.engine;

import com.game.thrones.model.Hero;
import com.game.thrones.model.House;
import com.game.thrones.model.House.Type;
import com.game.thrones.model.Territory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author James
 */
public class GameInitialiser {
    
    List<House> houses = new ArrayList<House>();
    
    List<Territory> territories = new ArrayList<Territory>();
    
    List<Hero> heroes = new ArrayList<Hero>();
    
    void create() {
        
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
        createTerritory("Bogland", 2, minorOne);
        createTerritory("Desert", 2, minorTwo);
        createTerritory("Coastal city", 4, minorThree);
        createTerritory("Forest town", 3, minorFour);    
        Territory outlands = createTerritory("Outlands", 1, neutralMinor);
        
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
    
    House createHouse(String name, Type type, House serves) {
        House house = new House();
        house.setName(name);
        house.setHouseType(type);
        house.setServes(serves);
        
        houses.add(house);
        
        return house;
    }
    
    House createHouse(String name, Type type) {
        return createHouse(name, type, null);        
    }
    
    Territory createTerritory(String name, int goldPerTurn, House house) {
        
        Territory territory = new Territory();
        
        territory.setGoldPerTurn(goldPerTurn);
        territory.setOwnedBy(house);
        territory.setName(name);
        
        territories.add(territory);
        
        return territory;
    }
    
    Hero createHero(String name, House house, Territory position) {
        
        Hero hero = new Hero();
        
        hero.setName(name);
        hero.setHouse(house);
        hero.setPosition(position);
        
        heroes.add(hero);
        
        return hero;
        
    }

}
