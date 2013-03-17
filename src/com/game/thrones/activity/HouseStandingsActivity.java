
package com.game.thrones.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import com.game.thrones.engine.GameController;
import com.game.thrones.model.House;
import com.game.thrones.model.Standing;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author James
 */
public class HouseStandingsActivity extends ListActivity {
    
    private BaseAdapter adapter;
    
    /** Called when the activity is first created. */
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //this ensures that the volume control is for music rather than ringtone
        //setVolumeControlStream(AudioManager.STREAM_MUSIC);
                
        GameController controller = GameController.getInstance();
        
        House currentPlayer = controller.getPlayer();
        
        List<String> standings = new ArrayList<String>();
        
        for (House house : controller.getBoard().getHouses()) {
            if (house.equals(currentPlayer)) {
                continue;
            }
            
            Standing standing = house.getHouseStandings().get(currentPlayer);
            
            standings.add(house.getName() + " " + standing.toString());            
            
        }
        
        adapter = new StandingsAdapter(standings);
        
        setListAdapter(adapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        
        //notifyDataChanged();
        
    }
    
    class StandingsAdapter extends ArrayAdapter<String> {        
        
        public StandingsAdapter(final List<String> options) {
          super(HouseStandingsActivity.this, android.R.layout.simple_list_item_1, options);
        }
    }

}
