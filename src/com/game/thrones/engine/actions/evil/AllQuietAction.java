package com.game.thrones.engine.actions.evil;

import com.game.thrones.engine.actions.Action;
import com.game.thrones.engine.descriptions.ActionDescription;
import com.game.thrones.engine.descriptions.Describable;

/**
 * Created by James on 01/01/2018.
 */
public class AllQuietAction implements Action, Describable {
    @Override
    public void execute() {
        //NOOP
    }

    @Override
    public ActionDescription summary() {
        return null;
    }

    @Override
    public String render() {
        return "Nothing happens";
    }
}
