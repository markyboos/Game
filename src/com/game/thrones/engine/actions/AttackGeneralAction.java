package com.game.thrones.engine.actions;

import com.game.thrones.activity.GameFinishedEvent;
import com.game.thrones.engine.Dice;
import com.game.thrones.engine.GameController;
import com.game.thrones.engine.GameFinished;
import com.game.thrones.engine.descriptions.ActionDescription;
import com.game.thrones.engine.descriptions.AttackGeneralDescriptionRenderer;
import com.game.thrones.engine.descriptions.Describable;
import com.game.thrones.engine.descriptions.DiceRollResult;
import com.game.thrones.model.AllFilter;
import com.game.thrones.model.Board;
import com.game.thrones.model.Team;
import com.game.thrones.model.hero.General;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.item.AbstractItem;
import com.game.thrones.model.hero.Woundable;
import com.game.thrones.model.item.AttackGeneralItem;
import com.game.thrones.model.item.SlayerItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author James
 */
public class AttackGeneralAction implements GroupAction<Hero>, ActionDescription, Describable<AttackGeneralAction> {

    private Map<Hero, List<AttackGeneralItem>> team = new LinkedHashMap<Hero, List<AttackGeneralItem>>();
    private General target;
    private Dice dice = new Dice();
    private Board board = GameController.getInstance().getBoard();
    
    private Map<Hero, List<DiceRollResult>> rolls = new HashMap<Hero, List<DiceRollResult>>();

    public AttackGeneralAction(final Hero hero, final General target) {
        team.put(hero, Collections.<AttackGeneralItem>emptyList());

        this.target = target;
    }

    public void putItems(Hero hero, List<AttackGeneralItem> items) {
        if (items.isEmpty()) {
            throw new IllegalStateException("You cannot attack a general without items");
        }
        team.put(hero, items);
    }

    public Set<Hero> getTeam() {
        return team.keySet();
    }

    public General getTarget() {
        return target;
    }
    
    public Map<Hero, List<DiceRollResult>> getRolls() {
        return rolls;
    }

    public void execute() {

        final Hero initiator = team.keySet().iterator().next();
        
        initiator.useAction();

        Map<Hero, Integer> attacks = new HashMap<Hero, Integer>();

        //all items get used immediately
        for (Hero hero : team.keySet()) {
            List<AttackGeneralItem> itemsToUse = team.get(hero);
            
            int totalAttacks = 0;

            for (AttackGeneralItem item : itemsToUse) {
                if (item.getTeam() != target.getTeam() && item.getTeam() != Team.NO_ONE) {
                    throw new AssertionError("Cannot use items against a general that arent of that team");
                }

                //red cards get corrupted for the demon
                if (item.getTeam() == Team.OLD_DEMONS && target.getTeam() == Team.OLD_DEMONS
                        && dice.roll(1).success()) {
                    continue;
                }
                totalAttacks += item.getAttackValue();
            }
            
            attacks.put(hero, totalAttacks);
            
            List<AttackGeneralItem> items = new ArrayList<AttackGeneralItem>(itemsToUse);

            for (AttackGeneralItem item : items) {
                hero.disposeItem(item);
            }
        }

        for (Hero hero : team.keySet()) {
            boolean dead = attackGeneral(hero, attacks.get(hero));

            if (dead) {
                getReward(hero);
                return;
            }
        }

        //all team are dead
        if (target instanceof Woundable) {
            ((Woundable) target).setAttackedBy(initiator);
        }

        for (Hero hero : team.keySet()) {
            target.inflictPenalty(hero);

            hero.setPosition(board.getCentralTerritory());
        }
    }

    private boolean attackGeneral(Hero hero, int attacks) {
        
        List<DiceRollResult> heroRolls = new ArrayList<DiceRollResult>();

        for (int i = 0; i < attacks; i++) {
            
            final DiceRollResult roll = dice.roll(target.getRollToDamage(), hero.modifyAttack(target));
            
            heroRolls.add(roll);
            
            if (roll.success()) {
                target.damage();
            }
        }
        
        rolls.put(hero, heroRolls);

        return target.isDead();

    }

    private void getReward(Hero slayer) {

        board.removePiece(target);

        //make the hero brilliant
        //slayer
        slayer.addSlayer(new SlayerItem("You are the slayer of beasts, any minion of that team you attack will flee from your mightyness", target));

        for (Hero hero : team.keySet()) {
            //add 3 hero cards
            for (int i = 0; i < 3; i++) {
                hero.addItem(GameController.getInstance().getItemController().getTopItem());
            }
        }

        //victory condition check
        if (board.getPieces(new AllFilter<General>(), General.class).isEmpty()) {
            GameController.getInstance().fireGameFinishedEvent(new GameFinishedEvent(GameFinished.GENERALS_ALL_DEAD));
        }

        //increase the war status
        GameController.getInstance().getAIController().increaseWarStatus();

    }

    @Override
    public String toString() {
        return "Attack the general [" + target.getName() + "]";
    }

    public AttackGeneralAction summary() {
        return this;
    }

    public String render() {
        return new AttackGeneralDescriptionRenderer().render(summary());
    }
}
