package com.game.thrones.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.game.thrones.engine.GameController;
import com.game.thrones.engine.Orders;
import com.game.thrones.engine.actions.Action;
import com.game.thrones.engine.descriptions.Describable;
import com.game.thrones.model.hero.Hero;

import java.util.Queue;
import java.util.Set;

/**
 * Created by James on 29/12/2017.
 */
public class AIActionTaker {

    private final Activity activity;
    private final Queue<Action> orders;

    private Handler.Callback callback;

    private final GameController controller = GameController.getInstance();

    public AIActionTaker(Activity activity, Handler.Callback callback) {
        this.activity = activity;
        this.orders = null;
        this.callback = callback;
    }

    public AIActionTaker(Activity activity, Queue<Action> orders, Handler.Callback callback) {
        this.activity = activity;
        this.orders = orders;
        this.callback = callback;
    }

    public void takeAction(Action selected) {
        takeMove(selected);

    }

    private void showSummary(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Summary")
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        builder.show();

    }

    private void takeMove(final Action action) {
        controller.takeMove(action);

        if (action instanceof Describable) {
            Describable describable = (Describable) action;
            showSummary(describable.render());
            return;
        }

        finish();
    }

    private void finish() {

        if (callback != null) {
            callback.handleMessage(null);
        }
    }

    public void takeAITurn() {
        Action nextOrders = orders.poll();
        if (nextOrders == null) {
            finish();
            return;
        }

        takeNextMove(nextOrders);
    }

    private void takeNextMove(final Action action) {
        controller.takeMove(action);

        if (action instanceof Describable) {
            Describable describable = (Describable) action;
            Toast.makeText(activity, describable.render(), Toast.LENGTH_SHORT).show();

            takeAITurn();
        } else {
            takeAITurn();
        }
    }
}
