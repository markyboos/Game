
package com.game.thrones.activity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.game.thrones.engine.GameController;
import com.game.thrones.engine.actions.Action;
import com.game.thrones.model.hero.ActionItem;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.hero.Item;
import java.util.List;

/**
 *
 * @author James
 */
public class InventoryActivity extends ListActivity {
    
    private Hero hero;
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //this ensures that the volume control is for music rather than ringtone
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        this.hero = GameController.getInstance().getPlayer();

        setListAdapter(new ItemAdapter(hero.getInventory()));
    }

    private class ItemAdapter extends ArrayAdapter<Item> {

        public ItemAdapter(List<Item> options) {
            super(InventoryActivity.this,
                    android.R.layout.simple_list_item_1, options);
        }
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Item selected = (Item) l.getItemAtPosition(position);
        
        if (selected instanceof ActionItem) {
            
            final ActionItem actionItem = (ActionItem)selected;
        
            //show a toast, with the option to use the card.
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this); 
            // set dialog message
            alertDialogBuilder.setMessage((actionItem).getDescription());
            alertDialogBuilder.setPositiveButton("Use it?", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface arg0, int arg1) {
                    Action action = actionItem.getAction();
                    
                    ActionTaker actionTaker = new ActionTaker(hero, InventoryActivity.this);
                    
                    actionTaker.takeAction(action);
                    
                    hero.useItem(actionItem);
                }
            });
            alertDialogBuilder.show();
        }
    }

}
