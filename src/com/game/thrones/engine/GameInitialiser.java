
package com.game.thrones.engine;

import com.game.thrones.model.*;
import com.game.thrones.model.House.HouseType;
import com.game.thrones.model.House.PlayerType;
import com.game.thrones.model.hero.General;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.piece.Piece;

import java.util.*;

/**
 *
 * @author James
 */
public class GameInitialiser {
    
    private Set<House> houses = new HashSet<House>();
    private Set<Piece> pieces = new HashSet<Piece>();
    private Map<Territory, Set<Territory>> borderMap = new HashMap<Territory, Set<Territory>>();
    
    private void initialise() {
        
        // territories        
        Territory kingsLanding = createTerritory(Territory.KINGS_LANDING);
        Territory winterfell = createTerritory("Winterfell");
        Territory rock = createTerritory("Castle rock");
        Territory bogland = createTerritory("Bogland");
        Territory desert = createTerritory("Desert");
        Territory coast = createTerritory("Coastal city");
        Territory forest = createTerritory("Forest town");
        Territory outlands = createTerritory("Outlands");
        
        //conecting territories
        addBorder(kingsLanding, bogland);
        addBorder(kingsLanding, desert);
        addBorder(kingsLanding, outlands);
        addBorder(coast, desert);
        addBorder(coast, rock);
        addBorder(forest, bogland);
        addBorder(forest, winterfell);
        
        createGeneral(General.FATTY, rock);
        
        createHero("godBoy", kingsLanding);
        
        //start with the king and 2 other major houses
        //to see how this goes
        /**
        House kingsHouse = createHouse("The kings house", HouseType.KING);                
        House minorOne = createHouse("Scumbag Minor One", HouseType.MINOR, kingsHouse);        
        House minorTwo = createHouse("Honorable Minor One", HouseType.MINOR, kingsHouse);
                
        House majorOne = createHumanHouse(House.PLAYER_ONE, HouseType.MAJOR, kingsHouse);        
        House minorThree = createHouse("Honorable Minor Two", HouseType.MINOR, majorOne);
                
        House majorTwo = createHouse("Player Two", HouseType.MAJOR, kingsHouse);
        House minorFour = createHouse("Honorable Minor Three", HouseType.MINOR, majorTwo);  
        
        //this house is completly neutral
        House neutralMinor = createHouse("Wild men", HouseType.MINOR);
        
        // territories        
        Territory kingsLanding = createTerritory(Territory.KINGS_LANDING, 20, kingsHouse);
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
        createLord("The King", kingsHouse, kingsLanding);
        createEmissary("Sneaky", minorOne, kingsLanding);
        createKnight("Champy", minorTwo, kingsLanding);
        
        createLord("Player one", majorOne, winterfell);
        createKnight("The cruncher", minorThree, winterfell);
        
        createLord("Player two", majorTwo, rock);
        createKnight("Goldy", minorTwo, rock);
        
        createKnight("smelly", neutralMinor, outlands);
        
        //standings
        for (House house : houses) {
            for (House other : houses) {
                if (house.equals(other)) {
                    continue;
                }
                
                house.addHouseStanding(other, new Standing());                
            }
        }
        */
        
        
    }
    
    private House createHumanHouse(String name, HouseType hType, House serves) {
        House house = new House(name, hType, PlayerType.HUMAN);
        house.setServes(serves);

        houses.add(house);
        
        return house;
    }
    
    private House createHouse(String name, HouseType hType, House serves) {
        House house = new House(name, hType);
        house.setServes(serves);

        houses.add(house);
        
        return house;
    }
    
    private House createHouse(String name, HouseType type) {
        return createHouse(name, type, null);        
    }

    private Territory createTerritory(String name) {
        Territory t1 = new Territory(name, 0, House.NO_ONE);

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

    public Board createBoard() {
        initialise();

        return new Board(borderMap, houses, pieces);
    }

    private void createGeneral(final String name, Territory position) {
        General general = new General(name);
        
        general.setPosition(position);
        
        pieces.add(general);
    }

    private void createHero(final String name, Territory position) {
        Hero hero = new Hero(name);
        
        hero.setPosition(position);
        
        pieces.add(hero);
    }



}
