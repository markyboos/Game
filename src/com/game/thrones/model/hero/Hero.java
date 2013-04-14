
package com.game.thrones.model.hero;

import com.game.thrones.model.Team;
import com.game.thrones.model.Territory;
import com.game.thrones.model.piece.Piece;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author James
 */
public abstract class Hero extends Piece {
    
    protected int maxHealth = 5;
    
    protected int health = maxHealth;
    
    protected int actionsAvailable = health;
    
    private List<Item> inventory = new ArrayList<Item>();
    
    private int cardsToRemove;
    
    public Hero(final String name) {
        this.name = name;
    }
    
    public void addItem(final Item item) {        
        inventory.add(item);        
    }
    
    public void useItem(final Item item) {
        inventory.remove(item);
    }
    
    public List<Item> getInventory() {
        return Collections.unmodifiableList(inventory);
    }
    
    public void clearInventory() {
        inventory.clear();
    }
    
    public boolean isSlayer(final Team team) {
        for (Item item : inventory) {
            if (item.getItemType() == Item.ItemType.SLAYER && item.getTeam() == team) {
                return true;
            }
        }
        
        return false;
    }
    
    public List<Item> getItemsForTeam(final Team team) {
        List<Item> items = new ArrayList<Item>();
        for (Item item : inventory) {
            
            if (item.getItemType() != Item.ItemType.CARD) {
                continue;                
            }
            
            if (item.getTeam() == team || item.getTeam() == Team.NO_ONE) {
                items.add(item);
            }
        }
        
        return Collections.unmodifiableList(items);        
    }
    
    public void setCardsToRemove(int cardsToRemove) {
        this.cardsToRemove = cardsToRemove;        
    }
    
    public int getCardsToRemove() {
        return cardsToRemove;
    }

    public void useAction() {
        actionsAvailable -= 1;
    }
    
    public void heal() {
        if (getPosition().getName().equals(Territory.KINGS_LANDING)) {
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

    /**
     * Take damage from minions.
     * @param minion the minion you are taking damage from
     */
    public void takeDamage(final List<Minion> minions) {
        
        int damage = 0;
        
        for (Minion minion : minions) {
            damage += takeDamage(minion);            
        }
        
        takeDamage(damage);
    }
    
    protected int takeDamage(Minion minion) {
        return 1;
    }
    
    public void takeDamage(int damage) {
        
        if (damage <= 0) {
            return;
        }
        
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
    public void modifyActions() {}
    
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
     * Override this method for finished attack modifications.
     */
    public void finishAttack() {}
    
    @Override
    public String toString() {
        return name + " [" + health + "/" + maxHealth + "]";
    }
}
