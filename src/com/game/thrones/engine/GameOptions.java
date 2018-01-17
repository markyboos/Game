package com.game.thrones.engine;

import com.game.thrones.model.hero.Hero;

import java.util.List;

/**
 * Created by James on 30/12/2017.
 */
public class GameOptions {

    public GameType type;
    public List<Hero> startingHeros;

    public GameOptions(GameType t, List<Hero> heros) {
        type = t;
        startingHeros = heros;
    }
}
