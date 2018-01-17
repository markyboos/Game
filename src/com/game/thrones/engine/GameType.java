package com.game.thrones.engine;

/**
 * Created by James on 30/12/2017.
 */
public enum GameType {
    BIG(4),
    SMALL(2);

    public int maxHeros;

    GameType(int heros) {
        this.maxHeros = heros;
    }
}
