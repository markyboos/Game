
package com.game.thrones.engine;

import com.game.thrones.model.*;
import com.game.thrones.model.hero.Barbarian;
import com.game.thrones.model.hero.Dragon;
import com.game.thrones.model.hero.Fatty;
import com.game.thrones.model.hero.General;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Minion;
import com.game.thrones.model.hero.Ranger;
import com.game.thrones.model.piece.Piece;

import java.util.*;

/**
 *
 * @author James
 */
public class GameInitialiser {
    
    private Set<Piece> pieces = new HashSet<Piece>();
    private Map<Territory, Set<Territory>> borderMap = new HashMap<Territory, Set<Territory>>();
    
    private void initialise() {
        
        // territories        
        Territory kingsLanding = createTerritory(Territory.KINGS_LANDING, 0, Team.NO_ONE);
        Territory marshland = createTerritory("Marshland", 1, Team.ORCS);
        Territory winterfell = createTerritory("Winterfell", 2, Team.DRAGONS);
        Territory village = createTerritory("Village", 2, Team.ORCS);
        Territory rock = createTerritory("Castle Rock", 2, Team.ORCS);
        Territory bogland = createTerritory("Bogland", 1, Team.DRAGONS);
        Territory desert = createTerritory("Desert", 1, Team.ORCS);
        Territory coast = createTerritory("Coastal City", 1, Team.DRAGONS);
        Territory forest = createTerritory("Forest Town", 2, Team.ORCS);
        Territory outlands = createTerritory("Outlands", 2, Team.DRAGONS);
        
        Territory inn = createTerritory("Stinky Tavern", 0, Team.NO_ONE);
        Territory innTwo = createTerritory("Smelly Tavern", 0, Team.NO_ONE);
        
        //conecting territories
        addBorder(kingsLanding, bogland);
        addBorder(kingsLanding, desert);
        addBorder(kingsLanding, outlands);
        addBorder(coast, desert);
        addBorder(coast, rock);
        addBorder(coast, marshland);
        addBorder(marshland, rock);
        addBorder(outlands, inn);
        addBorder(forest, bogland);
        addBorder(bogland, village);
        addBorder(village, winterfell);
        addBorder(forest, winterfell);
        addBorder(innTwo, forest);
        
        createGeneral(new Fatty(), Team.ORCS, rock);
        createGeneral(new Dragon(), Team.DRAGONS, winterfell);
        
        createHero(new Barbarian(), kingsLanding);
        createHero(new Ranger(), kingsLanding);
        
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

    private Territory createTerritory(String name, int value, Team team) {
        Territory t1 = new Territory(name, value, team);

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

        return new Board(borderMap, pieces);
    }

    private void createGeneral(final General general, final Team team, Territory position) {
        general.setPosition(position);
        pieces.add(general);
        
        //add 3 minions to the general
        Minion minion = new Minion(team);
        minion.setPosition(position);
        
        pieces.add(minion);
        
        minion = new Minion(team);
        minion.setPosition(position);
        
        pieces.add(minion);
        
        minion = new Minion(team);
        minion.setPosition(position);
        
        pieces.add(minion);
    }

    private void createHero(Hero hero, Territory position) {
        
        hero.setPosition(position);
        
        pieces.add(hero);
    }



}
