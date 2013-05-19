
package com.game.thrones.engine;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author James
 */
public class Deck<T> {
    
    private LinkedList<T> deck = new LinkedList<T>();    
    private Set<T> discardPile = new HashSet<T>();
    
    public Deck(final List<T> initial) {
        deck.addAll(initial);
        Collections.shuffle(deck);
    }
    
    public T takeTopCard() {        
        T card = deck.poll();
        
        if (card == null) {
            reshuffleDeck();
            card = deck.poll();
        }
        
        return card;        
    }
    
    public void discard(T t) {
        discardPile.add(t);
    }
    
    public void reshuffleDeck() {
        
        deck.addAll(discardPile);
        
        discardPile.clear();
        
        Collections.shuffle(deck); 
    }

}
