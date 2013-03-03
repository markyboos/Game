
package com.game.thrones.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.game.thrones.engine.GameController;
import com.game.thrones.model.House;
import com.game.thrones.model.piece.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Shows where pieces are on the map.
 *
 * @author James
 */
public class MapActivity extends ListActivity {
    
    private BaseAdapter adapter;
    
    /** Called when the activity is first created. */
    @Override public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //this ensures that the volume control is for music rather than ringtone
        //setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
        GameController controller = GameController.getInstance();
        
        Set<House> houses = controller.getBoard().getHouses();
        
        List<Piece> pieces = new ArrayList<Piece>();
        
        for (House house : houses) {
            pieces.addAll(controller.getBoard().getPieces(house));
        }
        
        adapter = new PieceAdapter(pieces);
        
        setListAdapter(adapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        
        adapter.notifyDataSetChanged();
        
    }
    
    @Override protected void onListItemClick(ListView l, View v, int position, long id) {
        
        Piece selected = (Piece)l.getItemAtPosition(position);
        
        Intent intent = new Intent(this, PlayerActionActivity.class);
        intent.putExtra(PlayerActionActivity.PIECE_NAME, selected.getName());
        
        this.startActivity(intent);
    }
    
    class PieceAdapter extends ArrayAdapter<Piece> {        
        
        public PieceAdapter(List<Piece> options) {
          super(MapActivity.this, 
                  android.R.layout.simple_list_item_1, options);
        }
        
        @Override public View getView(int position, View convertView, ViewGroup parent) {
            
            View view = super.getView(position, convertView, parent);
            
            Piece piece = super.getItem(position);

            View textView = (View)view.findViewById(android.R.id.text1);
            
            //todo this is pretty rubbish
            if (piece.getHouse().getName().equals("The King")) {
                textView.setBackgroundColor(Color.YELLOW);                    
            }

            return view;
        }
    }

}
