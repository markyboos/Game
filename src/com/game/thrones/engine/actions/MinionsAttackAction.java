package com.game.thrones.engine.actions;

import com.game.thrones.engine.GameController;
import com.game.thrones.engine.descriptions.ActionDescription;
import com.game.thrones.engine.descriptions.DamageDescriptionRenderer;
import com.game.thrones.engine.descriptions.Describable;
import com.game.thrones.model.PieceTerritoryFilter;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Minion;

import java.util.List;

/**
 * Created by James on 14/07/13.
 */
public class MinionsAttackAction extends AbstractAction<Hero> implements Describable<MinionsAttackAction>, ActionDescription {

    private int damage = 0;

    public MinionsAttackAction(Hero hero) {
        super(hero);
    }

    public boolean shouldExecute() {
        List<Minion> minionsAtHero = GameController.getInstance().getBoard()
                .getPieces(new PieceTerritoryFilter(piece.getPosition()),
                        Minion.class);
        return minionsAtHero.size() > 0;
    }

    @Override
    public void execute() {
        //if the hero is in a place with monsters then take life off
        List<Minion> minionsAtHero = GameController.getInstance().getBoard()
                .getPieces(new PieceTerritoryFilter(piece.getPosition()),
                        Minion.class);

        damage = piece.takeDamage(minionsAtHero);
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public MinionsAttackAction summary() {
        return this;
    }

    @Override
    public String render() {
        return new DamageDescriptionRenderer().render(this);
    }
}
