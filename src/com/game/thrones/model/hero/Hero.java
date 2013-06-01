
package com.game.thrones.model.hero;

import com.game.thrones.engine.GameController;
import com.game.thrones.engine.actions.Action;
import com.game.thrones.model.Filter;
import com.game.thrones.engine.actions.ItemReward;
import com.game.thrones.model.Quest;
import com.game.thrones.model.Team;
import com.game.thrones.model.piece.Piece;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author James
 */
public abstract class Hero extends Piece {
    
    private static class NoListener implements DamageListener {
        //no op
        public void damageEventFired(Hero hero) {}        
    }
    
    private static final NoListener NO_LISTENER = new NoListener();
    
    final protected int maxHealth;
    
    protected int health;
    
    protected int actionsAvailable;
    
    private List<Item> inventory = new ArrayList<Item>();
    
    private int cardsToRemove;
    
    private Quest quest;
    
    private List<Quest> questsCompleted = new ArrayList<Quest>();
    
    private DamageListener listener = NO_LISTENER;
    
    public Hero(final String name) {
        this.name = name;
        this.maxHealth = 5;
        this.health = maxHealth;
        this.actionsAvailable = maxHealth;
    }
    
    public Hero(final String name, final int maxHealth) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.actionsAvailable = maxHealth;
    }
    
    public void addItem(final Item item) {
        //dont add null items
        if (item == null) {
            return;
        }        
        
        inventory.add(item);
        if (inventory.size() > 10) {
            cardsToRemove ++;
        }
    }
    
    public void useItem(final Item item) {
        inventory.remove(item);
        GameController.getInstance().getItemController().discard(item);
    }
    
    public List<Item> getInventory() {
        return Collections.unmodifiableList(inventory);
    }
    
    public void clearInventory() {
        for (Item item : inventory) {
            GameController.getInstance().getItemController().discard(item);
        }
        inventory.clear();
    }
    
    public <I extends Item> List<I> getItems(final Filter<I> filter, final Class<I> klass) {
        List<I> foundItems = new ArrayList<I>();

        for (Item item : inventory) {
            if (klass.isAssignableFrom(item.getClass())
                    && filter.valid((I) item)) {
                foundItems.add((I) item);
            }
        }

        return foundItems;
    }
    
    public void setCardsToRemove(int cardsToRemove) {
        this.cardsToRemove = Math.min(inventory.size(), cardsToRemove);
    }
    
    public int getCardsToRemove() {
        return cardsToRemove;
    }
    
    public Quest getQuest() {
        return quest;
    }
    
    public void setQuest(Quest quest) {
        this.quest = quest;
    }
    
    public void collectReward(final Quest quest) {
        questsCompleted.add(quest);
        
        Action reward = quest.getReward();
        
        if (reward instanceof ItemReward) {
            ItemReward itemReward = (ItemReward)reward;
            itemReward.setHero(this);
        }
        
        reward.execute();
    }
    
    public List<Quest> getCompletedQuests() {
        return Collections.unmodifiableList(questsCompleted);
    }

    public void useAction() {
        actionsAvailable -= 1;
    }
    
    public void heal() {
        if (getPosition().getOwner() == Team.NO_ONE) {
            health = maxHealth;
        } else {
            health += 2;
            if (health > maxHealth) {
                health = maxHealth;
            }
        }
    }
    
    public void rest() {
        actionsAvailable = health;
    }
    
    public int getActionsAvailable() {
        return actionsAvailable;
    }
    
    public void setDamageListener(DamageListener listener) {
        this.listener = listener;
    }

    /**
     * Take damage from minions.
     * @param minion the minion you are taking damage from
     */
    public void takeDamage(final List<Minion> minions) {
        
        int damage = 0;
        boolean undead = false;
        
        for (Minion minion : minions) {
            damage += takeDamage(minion);
            if (minion.getTeam() == Team.UNDEAD) {
                undead = true;
            }
        }
        
        takeDamage(undead && affectedByUndead() ? damage + 1 : damage);       
        
    }
    
    protected boolean affectedByUndead() {
        return true;
    }
    
    protected int takeDamage(Minion minion) {
        return 1;
    }
    
    public void takeDamage(int damage) {
        
        if (damage <= 0) {
            return;
        }
        
        listener.damageEventFired(this);
        
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }
    
    public boolean isAtMaxHealth() {
        return health == maxHealth;
    }
    
    public boolean isDead() {
        return health == 0;
    }
    
    /**
     * Override this method to modify actions at the begging of the turn.
     */    
    public void modifyActions() {
        //if you have the boots of speed you go mega fast
        for (Item item : inventory) {
            if (item.getItemType() == Item.ItemType.BOOTS_OF_SPEED) {
                actionsAvailable += item.getPower();
            }
        }
    }
    
    /**
     * Override this method for attack modifications against minions.
     * @return 
     */    
    public int modifyAttack(Minion minion) {
        return 0;
    }
    
    /**
     * Override this method for attack modifications against generals.
     * @return 
     */ 
    public int modifyAttack(General general) {
        return 0;
    }
    
    /**
     * Override this method for quest roll modifications.
     * @return 
     */
    public int modifyQuestRoll() {
        return 0;
    }
    
    /**
     * Override this method for finished attack modifications.
     */
    public void finishAttack() {}
    
    /**
     * Override this method to draw extra cards at the end of the turn.
     * @return 
     */
    public int itemsPerTurn() {
        return 2;
    }
    
    @Override
    public String toString() {
        return name + " [" + health + "/" + maxHealth + "]";
    }
}
