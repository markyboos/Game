
package com.game.thrones.engine;

import com.game.thrones.engine.actions.MoveAction;
import com.game.thrones.engine.actions.CleanseAction;
import com.game.thrones.engine.actions.AttackAction;
import com.game.thrones.engine.actions.FireballAction;
import com.game.thrones.engine.actions.RangedAttackAction;
import com.game.thrones.engine.actions.TeleportAction;
import com.game.thrones.engine.actions.QuestAction;
import com.game.thrones.engine.actions.SteedAction;
import com.game.thrones.engine.actions.RumorsAction;
import com.game.thrones.engine.actions.HealAction;
import com.game.thrones.engine.actions.AttackGeneralAction;
import com.game.thrones.engine.actions.BarbarianAttackAction;
import com.game.thrones.engine.actions.Action;
import android.util.Log;
import com.game.thrones.engine.actions.FeedAttackAction;
import com.game.thrones.model.Filter;
import com.game.thrones.model.Team;
import com.game.thrones.model.Territory;
import com.game.thrones.model.TerritoryCriteria;
import com.game.thrones.model.PieceTerritoryFilter;
import com.game.thrones.model.hero.Barbarian;
import com.game.thrones.model.hero.Daywalker;
import com.game.thrones.model.hero.General;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.InventorySearcher;
import com.game.thrones.model.hero.Minion;
import com.game.thrones.model.hero.Paladin;
import com.game.thrones.model.hero.Ranger;
import com.game.thrones.model.hero.Sorceress;
import com.game.thrones.model.hero.Wizard;
import com.game.thrones.model.item.Item;
import com.game.thrones.model.piece.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Creates actions for pieces
 *
 * @author James
 */
public class ActionCreator {
    
    private GameController controller;
    
    private InventorySearcher searcher = new InventorySearcher();
    
    public List<Action> createActions(final Piece piece) {
        
        controller = GameController.getInstance();
         
        //generic actions
        List<Action> actions = createMoveActions(piece);
        
        Filter filter = new PieceTerritoryFilter(piece.getPosition());
                
        boolean minionsAtHero = !controller.getBoard().getPieces(
                filter, Minion.class).isEmpty();
        
        List<General> pieces = controller.getBoard().getPieces(filter, General.class);
        
        boolean generalAtHero = !pieces.isEmpty();
        
        if (piece instanceof Hero) {
            
            Hero hero = (Hero)piece;
            
            Log.d("ActionCreator:createActions", hero.getQuest() == null ? "No quest" : hero.getQuest().toString());
            
            if (hero.getQuest() != null && hero.getPosition().equals(hero.getQuest().getTerritory())) {
                actions.add(new QuestAction(hero));
            }
            
            if (minionsAtHero) {
                if (piece instanceof Barbarian) {
                    
                    actions.add(new BarbarianAttackAction((Barbarian)hero));                    
                } else if (piece instanceof Daywalker) {
                    
                    actions.add(new FeedAttackAction((Daywalker)hero));                    
                } else {
                    
                    actions.add(new AttackAction(hero));
                }
            }
            
            if (generalAtHero && !minionsAtHero) {
                General general = pieces.get(0);
                
                //not allowed to attack the general if they have no items
                if (searcher.hasTeamOrNooneItems(hero, general.getTeam())) {
                    actions.add(new AttackGeneralAction(hero, general));
                }                
            }
            
            Territory position = piece.getPosition();
            
            if (!minionsAtHero && !generalAtHero && !hero.isAtMaxHealth() && !(hero instanceof Daywalker)) {
                actions.add(new HealAction(hero));
            }
            
            if (position.getTainted() > 0 
                    && searcher.hasTeamItems(hero, hero.getPosition().getOwner())) {
                actions.add(new CleanseAction(hero));
            }
                        
            if (actionsSoFar(RumorsAction.class) < 2 && position.getOwner() == Team.NO_ONE && 
                    !position.getName().equals(Territory.CENTRAL_TERRITORY)) {            
                actions.add(new RumorsAction(hero));
            }
            
            //hero specific actions            
            if (piece instanceof Ranger) {
                actions.addAll(createRangedAttackActions(hero));
            }
            
            if (piece instanceof Paladin) {
                actions.addAll(createSteedActions((Paladin)hero));                
            }
            
            if (piece instanceof Wizard) {
                
                if (actionsSoFar(TeleportAction.class) == 0) {
                    actions.addAll(createTeleportActions((Wizard)hero));
                }
                
                FireballAction fireball = new FireballAction((Wizard)piece);
                
                if (minionsAtHero 
                        && !hero.getItems(fireball.getItemFilter(), Item.class).isEmpty()) {                
                    actions.add(fireball);
                }
            }
        }
        
        return actions;
        
    }
    
    private boolean isFirstAction() {
        return controller.getActionsTaken().isEmpty();
    }
    
    private int actionsSoFar(Class clazz) {
        
        int actions = 0;
            
        for (Action action : controller.getActionsTaken()) {
            if (clazz.isAssignableFrom(action.getClass())) {
                actions ++;
            }                
        }
        
        return actions;
    }
    
    private List<Action> createMoveActions(final Piece piece) {
                
        //all possible moves  
        List<Territory> territories = controller.getBoard().getBorderingTerritories(piece.getPosition());
           
        List<Action> actions = new ArrayList<Action>();
        
        for (Territory territory : territories) {
            if (piece instanceof Sorceress) {
                if (territory.getOwner() == Team.NO_ONE && ((Sorceress)piece).getShape() != Team.NO_ONE) {
                    continue;
                }
            }
            actions.add(new MoveAction(piece, territory));
        }
        
        return actions;
    }

    private List<Action> createRangedAttackActions(final Hero piece) {
        
        List<Action> actions = new ArrayList<Action>();
        
        TerritoryCriteria criteria = new TerritoryCriteria();
        criteria.setBordering(piece.getPosition());
        criteria.setMinionCountOperator(TerritoryCriteria.Operator.MORE_THAN);
        criteria.setMinionCount(0);
        
        List<Territory> territories = controller.getBoard().getTerritories(criteria);
        
        for (Territory territory : territories) {
            actions.add(new RangedAttackAction(piece, territory));
        }
        
        return actions;
    }

    private List<Action> createSteedActions(final Paladin hero) {
        
        List<Action> actions = new ArrayList<Action>();
        
        Set<Territory> territories = controller.getBoard()
                .getTerritoriesDistanceAway(hero.getPosition(), 2);
        
        for (Territory territory : territories) {
            actions.add(new SteedAction(hero, territory));
        }
        
        return actions;
    }

    private List<Action> createTeleportActions(Wizard wizard) {
        List<Action> actions = new ArrayList<Action>();
        
        for (Territory territory : controller.getBoard().getTerritories()) {
            if (territory.equals(wizard.getPosition())) {
                continue;
            }
            actions.add(new TeleportAction(wizard, territory));
        }
        
        return actions;
    }

}
