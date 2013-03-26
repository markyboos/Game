
package com.game.thrones.activity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.game.thrones.R;
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
        
        setContentView(R.layout.dashboard);
        
        //this ensures that the volume control is for music rather than ringtone
        //setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
        Button buttonOne = (Button) findViewById(R.id.endTurnButton);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                GameController controller = GameController.getInstance();
                controller.endTurn();
                
                notifyDataChanged();
            }
        });
        
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
        
        notifyDataChanged();
        
    }
    
    @Override protected void onListItemClick(ListView l, View v, int position, long id) {
        
        Piece selected = (Piece)l.getItemAtPosition(position);
        
        GameController controller = GameController.getInstance();
        if (!selected.equals(controller.getPlayer())) {
            
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Not your piece!");
            
            dialog.show();
            
            return;
        }
        
        Intent intent = new Intent(this, PlayerActionActivity.class);
        intent.putExtra(PlayerActionActivity.PIECE_NAME, selected.getName());
        
        this.startActivity(intent);
    }

    private void notifyDataChanged() {
        adapter.notifyDataSetChanged();
    }
    
    class PieceAdapter extends ArrayAdapter<Piece> {        
        
        public PieceAdapter(final List<Piece> options) {
          super(MapActivity.this, android.R.layout.simple_list_item_1, options);
        }
    }

}
