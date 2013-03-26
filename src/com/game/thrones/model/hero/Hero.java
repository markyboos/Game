
package com.game.thrones.model.hero;

import com.game.thrones.model.Territory;
import com.game.thrones.model.piece.Piece;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author James
 */
public class Hero extends Piece {
    
    private int maxHealth = 5;
    
    private int health = maxHealth;
    
    private int actionsAvailable = health;
    
    private List<Item> inventory = new ArrayList<Item>();
    
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

}
