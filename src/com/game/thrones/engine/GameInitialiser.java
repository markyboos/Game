
package com.game.thrones.engine;

import com.game.thrones.model.*;
import com.game.thrones.model.hero.Barbarian;
import com.game.thrones.model.hero.DemonKing;
import com.game.thrones.model.hero.Dragon;
import com.game.thrones.model.hero.Dwarf;
import com.game.thrones.model.hero.Fatty;
import com.game.thrones.model.hero.General;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Minion;
import com.game.thrones.model.hero.Paladin;
import com.game.thrones.model.hero.Ranger;
import com.game.thrones.model.hero.Sorceress;
import com.game.thrones.model.hero.UndeadKing;
import com.game.thrones.model.hero.Wizard;
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
        Territory kingsLanding = createTerritory(Territory.CENTRAL_TERRITORY, 1, Team.NO_ONE);
        Territory marshland = createTerritory("Marshland", 1, Team.ORCS);
        Territory winterfell = createTerritory(Territory.DRAGON_RANGE, 2, Team.DRAGONS);
        Territory village = createTerritory("Village", 2, Team.ORCS);
        Territory rock = createTerritory(Territory.THORNY_WOODS, 2, Team.ORCS);
        Territory bogland = createTerritory("Bogland", 1, Team.DRAGONS);
        Territory desert = createTerritory("Desert", 1, Team.ORCS);
        Territory coast = createTerritory("Coastal City", 1, Team.DRAGONS);
        Territory forest = createTerritory(Territory.OLD_FATHER_WOOD, 2, Team.ORCS);
        Territory outlands = createTerritory("Outlands", 2, Team.DRAGONS);

        Territory inn = createTerritory("Stinky Tavern", 1, Team.NO_ONE);
        Territory innTwo = createTerritory("Smelly Tavern", 1, Team.NO_ONE);

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

        createGeneral(new Fatty(), rock);
        createGeneral(new Dragon(), winterfell);

        //createHero(new Barbarian(), kingsLanding);
        createHero(new Wizard(), kingsLanding);
        //createHero(new Sorceress(), kingsLanding);
        createHero(new Paladin(), kingsLanding);
    }
    
    private void initialiseBigMap() {
        // territories
        Territory centre = createTerritory(Territory.CENTRAL_TERRITORY, 1, Team.NO_ONE);

        Territory inn = createTerritory("Stinky Tavern", 1, Team.NO_ONE);
        Territory innTwo = createTerritory("Smelly Tavern", 1, Team.NO_ONE);
        Territory innThree = createTerritory("Fragrent Tavern", 1, Team.NO_ONE);

        //orcs
        Territory goldenOakForest = createTerritory("Golden Oak Forest", 1, Team.ORCS);
        Territory fatherOakForest = createTerritory(Territory.OLD_FATHER_WOOD, 1, Team.ORCS);
        Territory ravenForest = createTerritory("Raven Forest", 1, Team.ORCS);
        Territory unicornForest = createTerritory("Unicorn Forest", 1, Team.ORCS);
        Territory minotaurForest = createTerritory("Minotaur Forest", 2, Team.ORCS);
        Territory gryphonForest = createTerritory("Gryphon Forest", 2, Team.ORCS);
        Territory whisperingWoods = createTerritory("Whispering Woods", 1, Team.ORCS);
        Territory heavensGlade = createTerritory("Heavens Glade", 2, Team.ORCS);
        Territory thornyWoods = createTerritory(Territory.THORNY_WOODS, 2, Team.ORCS);
        Territory greenLeafVillage = createTerritory("Green Leaf Village", 2, Team.ORCS);
        Territory wyvernForest = createTerritory("Wyvern Forest", 2, Team.ORCS);

        //undead
        Territory darkWoods = createTerritory("Dark Woods", 2, Team.UNDEAD);
        Territory enchantedGlade = createTerritory("Enchanted Glade", 1, Team.UNDEAD);
        Territory seaBirdPort = createTerritory("Seabird Port", 1 , Team.UNDEAD);
        Territory brookDaleVillage = createTerritory("Brook Dale Village", 1, Team.UNDEAD);
        Territory angelTearFalls = createTerritory("Angel Tear Falls", 2, Team.UNDEAD);        
        Territory fireRiver = createTerritory("Fire River", 1 , Team.UNDEAD);
        Territory mcCornHighlands = createTerritory("Mc'Corn Highlands", 1, Team.UNDEAD);
        Territory dancingStone = createTerritory("Dancing Stone", 2, Team.UNDEAD);        
        Territory landOfAmazons = createTerritory("Land of Amazons", 1, Team.UNDEAD);
        Territory mermaidHarbour = createTerritory("Mermaid Harbour", 2, Team.UNDEAD);

        //demons
        Territory windyPass = createTerritory("Windy Pass", 2, Team.DEMONS);
        Territory bloodFlats = createTerritory("Blood Flats", 1, Team.DEMONS);
        Territory pleasentHills = createTerritory("Pleasent Hills", 1, Team.DEMONS);
        Territory scorpionCanyon = createTerritory("Scorpion Canyon", 1, Team.DEMONS);        
        Territory orcValley = createTerritory("Orc Valley", 1, Team.DEMONS);
        Territory serpentSwamp = createTerritory("Serpent Swamp", 1, Team.DEMONS);
        Territory ghostMarsh = createTerritory("Ghost Marsh", 2, Team.DEMONS);
        Territory ancientRuins = createTerritory("Ancient Ruins", 2, Team.DEMONS);
        Territory witheredHills = createTerritory("Withered Hills", 2, Team.DEMONS);
        Territory cursedPlateau = createTerritory("Cursed Plateau", 2, Team.DEMONS);        
        
        //dragons
        Territory rockBridgePass = createTerritory("Rock Bridge Pass", 1, Team.DRAGONS);        
        Territory bountyBay = createTerritory("Bounty Bay", 2, Team.DRAGONS);        
        Territory dragonsTeethRange = createTerritory(Territory.DRAGON_RANGE, 2, Team.DRAGONS);
        Territory wolfPass = createTerritory("Wolf Pass", 1, Team.DRAGONS);
        Territory crystalHills = createTerritory("Crystal Hills", 2, Team.DRAGONS);
        Territory mountainsOfMist = createTerritory("Mountains Of Mist", 1, Team.DRAGONS);
        Territory blizzardMountains = createTerritory("Blizzard Moutains", 1, Team.DRAGONS);
        Territory amarakPeak = createTerritory("Amarak Peak", 2, Team.DRAGONS);
        Territory eaglePeakPass = createTerritory("Eagle Peak Pass ", 2, Team.DRAGONS);
        Territory seagullLagoon = createTerritory("Seagull Lagoon", 1, Team.DRAGONS);
        
        addBorder(centre, fatherOakForest);
        addBorder(centre, wolfPass);
        addBorder(centre, orcValley);
        addBorder(centre, dancingStone);
        addBorder(centre, greenLeafVillage);
        addBorder(centre, bountyBay);
        
        addBorder(fatherOakForest, pleasentHills);
        addBorder(fatherOakForest, brookDaleVillage);
        addBorder(fatherOakForest, seaBirdPort);
        addBorder(fatherOakForest, wolfPass);
        
        addBorder(wolfPass, minotaurForest);
        addBorder(wolfPass, seagullLagoon);
        addBorder(wolfPass, orcValley);
        
        addBorder(orcValley, dancingStone);
        addBorder(orcValley, eaglePeakPass);
        
        addBorder(dancingStone, whisperingWoods);
        addBorder(dancingStone, greenLeafVillage);
        
        addBorder(greenLeafVillage, ancientRuins);
        addBorder(greenLeafVillage, mountainsOfMist);
        addBorder(greenLeafVillage, bountyBay);
        
        addBorder(bountyBay, mermaidHarbour);
        addBorder(bountyBay, angelTearFalls);
        
        addBorder(pleasentHills, angelTearFalls);
        addBorder(pleasentHills, ravenForest);
        addBorder(pleasentHills, brookDaleVillage);
        
        addBorder(brookDaleVillage, rockBridgePass);
        addBorder(brookDaleVillage, seaBirdPort);
        addBorder(brookDaleVillage, unicornForest);
        
        addBorder(seaBirdPort, rockBridgePass);
        addBorder(seaBirdPort, windyPass);
        
        addBorder(minotaurForest, seagullLagoon);
        
        addBorder(seagullLagoon, gryphonForest);
        
        addBorder(eaglePeakPass, whisperingWoods);
        addBorder(eaglePeakPass, amarakPeak);
        
        addBorder(whisperingWoods, heavensGlade);
        addBorder(whisperingWoods, ancientRuins);
        
        addBorder(ancientRuins, heavensGlade);
        
        addBorder(mountainsOfMist, witheredHills);
        addBorder(mountainsOfMist, landOfAmazons);
        
        addBorder(mermaidHarbour, landOfAmazons);
        addBorder(mermaidHarbour, wyvernForest);
        addBorder(mermaidHarbour, crystalHills);
        addBorder(mermaidHarbour, fireRiver);
        
        addBorder(angelTearFalls, dragonsTeethRange);
        addBorder(angelTearFalls, fireRiver);
        addBorder(angelTearFalls, ravenForest);
        
        addBorder(ravenForest, scorpionCanyon);
        addBorder(ravenForest, bloodFlats);
        
        addBorder(rockBridgePass, enchantedGlade);
        addBorder(rockBridgePass, goldenOakForest);
        addBorder(rockBridgePass, windyPass);
        
        addBorder(unicornForest, bloodFlats);
        addBorder(unicornForest, enchantedGlade);
        
        addBorder(windyPass, darkWoods);
        
        addBorder(gryphonForest, inn);
        addBorder(gryphonForest, serpentSwamp);
        
        addBorder(amarakPeak, mcCornHighlands);
        addBorder(amarakPeak, ghostMarsh);
        addBorder(amarakPeak, thornyWoods);
        
        addBorder(heavensGlade, thornyWoods);
        addBorder(heavensGlade, blizzardMountains);
        
        addBorder(witheredHills, blizzardMountains);
        addBorder(witheredHills, innTwo);
        addBorder(witheredHills, cursedPlateau);
        
        addBorder(landOfAmazons, cursedPlateau);
        addBorder(landOfAmazons, wyvernForest);
        
        addBorder(wyvernForest, cursedPlateau);
        addBorder(wyvernForest, crystalHills);
        
        addBorder(crystalHills, fireRiver);
        
        addBorder(scorpionCanyon, bloodFlats);
        
        addBorder(enchantedGlade, innThree);
        
        addBorder(goldenOakForest, darkWoods);
        
        addBorder(serpentSwamp, mcCornHighlands);

        createGeneral(new Fatty(), thornyWoods);
        createGeneral(new Dragon(), blizzardMountains);
        createGeneral(new UndeadKing(), darkWoods);
        createGeneral(new DemonKing(), scorpionCanyon);

        createHero(new Barbarian(), centre);
        createHero(new Ranger(), centre);
        createHero(new Sorceress(), centre);
        createHero(new Dwarf(), centre);
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

    private void createGeneral(final General general, Territory position) {
        general.setPosition(position);
        pieces.add(general);
        
        Team team = general.getTeam();

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
        
        if (team == Team.DEMONS) {
            position.taint();
        }
    }

    private void createHero(Hero hero, Territory position) {

        hero.setPosition(position);

        pieces.add(hero);
    }



}
