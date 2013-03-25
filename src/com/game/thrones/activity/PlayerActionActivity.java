
package com.game.thrones.activity;

import com.game.thrones.engine.Action;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.game.thrones.engine.ActionCreator;
import com.game.thrones.engine.GameController;
import com.game.thrones.model.piece.Piece;
import java.util.List;

/**
 * This is an activity which shows a list of actions a piece can do for that turn.
 * Might be better as a pop up or when the graphics are better be put into the map.
 *
 * @author James
 */
public class PlayerActionActivity extends ListActivity {
    
    public static final String PIECE_NAME = "PIECE";
    
    private GameController controller = GameController.getInstance();
    
    private ActionCreator actionCreator = new ActionCreator();
    
    /** Called when the activity is first created. */
    @Override public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //this ensures that the volume control is for music rather than ringtone
        //setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
        Bundle extras = getIntent().getExtras();
        
        if (extras == null) {
            throw new IllegalArgumentException("Failed to get PIECE_NAME, did you forget to add it?");
        }
        
        String name = extras.getString(PIECE_NAME);
        
        if (name == null) {
            throw new IllegalArgumentException("Failed to get PIECE_NAME, did you forget to add it?");
        }
        
        Piece turnItIs = controller.getBoard().getPiece(name);
        
        List<Action> actions = actionCreator.createActions(turnItIs);
        
        setListAdapter(new ActionAdapter(actions));
    }
    
    @Override protected void onListItemClick(ListView l, View v, int position, long id) {
        Action selected = (Action)l.getItemAtPosition(position);
        
        //all actions have finished
        controller.takeMove(selected);
        
        this.finish();
    }
    
    class ActionAdapter extends ArrayAdapter<Action> {
        
        public ActionAdapter(List<Action> options) {
          super(PlayerActionActivity.this, 
                  android.R.layout.simple_list_item_1, options);
        }
        
    }

}
