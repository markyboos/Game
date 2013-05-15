package com.game.thrones.engine.actions;

import com.game.thrones.activity.GameFinishedEvent;
import com.game.thrones.engine.Dice;
import com.game.thrones.engine.GameController;
import com.game.thrones.engine.GameFinished;
import com.game.thrones.model.AllFilter;
import com.game.thrones.model.Board;
import com.game.thrones.model.Team;
import com.game.thrones.model.hero.General;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Item;
import com.game.thrones.model.hero.Woundable;
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
public class AttackGeneralAction implements GroupAction<Hero> {

    private Map<Hero, List<Item>> team = new LinkedHashMap<Hero, List<Item>>();
    private General target;
    private Dice dice = new Dice();
    private Board board = GameController.getInstance().getBoard();

    public AttackGeneralAction(final Hero hero, final General target) {
        team.put(hero, Collections.<Item>emptyList());

        this.target = target;
    }

    public void putItems(Hero hero, List<Item> items) {
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

    public void execute() {

        final Hero initiator = team.keySet().iterator().next();

        Map<Hero, Integer> attacks = new HashMap<Hero, Integer>();

        //all items get used immediately
        for (Hero hero : team.keySet()) {
            List<Item> itemsToUse = team.get(hero);
            
            int totalAttacks = 0;

            for (Item item : itemsToUse) {
                if (item.getTeam() != target.getTeam() && item.getTeam() != Team.NO_ONE) {
                    throw new AssertionError("Cannot use items against a general that arent of that team");
                }

                //red cards get corrupted for the demon
                if (item.getTeam() == Team.OLD_DEMONS && target.getTeam() == Team.OLD_DEMONS
                        && dice.roll(1).success()) {
                    continue;
                }
                totalAttacks += item.getPower();
            }
            
            attacks.put(hero, totalAttacks);
            
            List<Item> items = new ArrayList<Item>(itemsToUse);

            for (Item item : items) {
                hero.useItem(item);
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

        for (int i = 0; i < attacks; i++) {
            if (dice.roll() + hero.modifyAttack(target) >= target.getRollToDamage()) {
                target.damage();
            }
        }

        return target.isDead();

    }

    private void getReward(Hero slayer) {

        board.removePiece(target);

        //make the hero brilliant
        //slayer
        slayer.addItem(new Item(target.getTeam()));

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
}
