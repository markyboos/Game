
package com.game.thrones.engine.actions;

import com.game.framework.SoundManager;
import com.game.thrones.MainActivity;
import com.game.thrones.engine.GameController;
import com.game.thrones.model.Filter;
import com.game.thrones.model.OrFilter;
import com.game.thrones.model.PieceTerritoryFilter;
import com.game.thrones.model.Team;
import com.game.thrones.model.hero.Item;
import com.game.thrones.model.hero.ItemTeamFilter;
import com.game.thrones.model.hero.Minion;
import com.game.thrones.model.hero.Wizard;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author James
 */
public class FireballAction extends AttackAction implements ItemSelectAction {

    private Item item;

    public FireballAction(final Wizard wizard) {
        super(wizard);
    }
    
    public void setItem(final Item item) {
        this.item = item;
    }
    
    public Filter<Item> getItemFilter() {
        List<Minion> minions = GameController.getInstance().getBoard().getPieces(
                new PieceTerritoryFilter<Minion>(piece.getPosition()), Minion.class);
        
        Set<Team> teams = new HashSet<Team>();
        
        for (Minion minion : minions) {
            teams.add(minion.getTeam());
        }
        
        Filter<Item>[] filters = new Filter[teams.size()];
        int i = 0;
        for (Team team : teams) {
            filters[i] = new ItemTeamFilter(team);
            i ++;                    
        }
        
        return new OrFilter<Item>(filters);
        
    }

    @Override
    public void execute() {
        piece.useItem(item);
        
        playSoundEffect(MainActivity.FIREBALL);
        
        super.execute();
    }
    
    @Override
    protected boolean isSlayer(Minion minion) {        
        return false;
    }

    @Override
    protected int rollToDamage(Minion minion) {
        return 2;
    }
    
    @Override
    public String toString() {
        return "Burn the muthas";
    }
}
