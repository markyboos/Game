
package com.game.thrones.activity;

import com.game.thrones.engine.MoveAction;
import com.game.thrones.engine.Action;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.game.thrones.engine.GameController;
import com.game.thrones.engine.RecruitAction;
import com.game.thrones.model.Territory;
import com.game.thrones.model.piece.IKnight;
import com.game.thrones.model.piece.Piece;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an activity which shows a list of actions a piece can do for that turn.
 * Might be better as a pop up or when the graphics are better be put into the map.
 *
 * @author James
 */
public class PlayerActionActivity extends ListActivity {
    
    public static final String PIECE_NAME = "PIECE";
    
    /** Called when the activity is first created. */
    @Override public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //this ensures that the volume control is for music rather than ringtone
        //setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
        String name = getIntent().getExtras().getString(PIECE_NAME);
        
        GameController controller = GameController.getInstance();
        
        Piece turnItIs = controller.getBoard().getPiece(name);
        
        //all possible moves  
        List<Territory> borderingTerritories = controller.getBoard().getBorderingTerritories(turnItIs.getPosition());
        
        //generic actions
        List<Action> actions = createActions(turnItIs, borderingTerritories);
        
        //piece specific actions
        if (turnItIs instanceof IKnight) {
            actions.add(new RecruitAction((IKnight)turnItIs));
        }
        
        
        setListAdapter(new ActionAdapter(actions));
    }
    
    @Override protected void onListItemClick(ListView l, View v, int position, long id) {
        Action selected = (Action)l.getItemAtPosition(position);
        
        selected.execute();
        
        this.finish();
    }

    private List<Action> createActions(Piece piece, List<Territory> territorys) {
        List<Action> actions = new ArrayList<Action>();
        
        for (Territory territory : territorys) {
            actions.add(new MoveAction(piece, territory));
        }
        
        return actions;
    }
    
    class ActionAdapter extends ArrayAdapter<Action> {
        
        public ActionAdapter(List<Action> options) {
          super(PlayerActionActivity.this, 
                  android.R.layout.simple_list_item_1, options);
        }
        
    }

}
