
package com.game.thrones.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.game.thrones.engine.GameController;
import com.game.thrones.model.PieceCriteria;
import com.game.thrones.model.Territory;
import com.game.thrones.model.piece.Piece;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author James
 */
public class MapView extends View {
    
    private final static Paint GREEN;
    
    static {
        GREEN = new Paint();
        GREEN.setColor(Color.GREEN);
    }
        
    private GameController controller;
    
    private int camerax = 0;
    private int cameray = 0;    
    
    private final Point startPosition = new Point();
    
    public MapView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        
        controller = GameController.getInstance();        
    }
    
    Territory[][] map = new Territory[6][6];
    
    public List<TerritoryTile> createTerritoryTiles(List<Territory> territory) {
        
        List<TerritoryTile> tiles = new ArrayList<TerritoryTile>();
        
        map[3][3] = controller.getBoard().getFirstTerritory();
        
        populateNeighbours(3, 3, map[3][3]);
        
        List<Piece> pieces = controller.getBoard().getVisiblePieces(controller.getPlayer());
        
        for (int i = 0 ; i < map.length ; i ++) {
            for (int j = 0; j < map[0].length; j ++) {
                if (map[i][j] == null) {
                    continue;
                }
                
                List<Piece> piecesAt = new ArrayList<Piece>();
                
                for (Piece piece : pieces) {
                    if (map[i][j].equals(piece.getPosition())) {
                        piecesAt.add(piece);
                    }
                }
                        
                TerritoryTile tile = new TerritoryTile(map[i][j], i, j, piecesAt);
                
                
                tiles.add(tile);
            }
        }
        
        return tiles;        
    }
    
    private void populateNeighbours(int x, int y, Territory territory ) {
        
        List<Territory> neighbours = controller.getBoard().getBorderingTerritories(territory);
                
        for (Territory t : neighbours) {
            
            if (alreadyInMap(t)) {
                continue;
            }
            
            //System.out.println(t.getName());
            
            populateClosestTile(x, y, t);
            
        }
        
    }
    
    private void populateClosestTile(int x, int y, Territory territory) {
        
        for (int i = x - 1; i < x + 2; i += 2) {
            for (int j = y - 1; j < y + 2; j += 2) {
                if (i < 0 || j < 0 || i >= map.length || j >= map[0].length) {
                    continue;
                }
                
                if (map[i][j] == null) {
                    map[i][j] = territory;
                    
                    populateNeighbours(i, j, territory);
                    return;
                }                
            }
        }   
    }
    
    private boolean alreadyInMap(Territory territory) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == null) {
                    continue;
                }
                if (map[i][j].equals(territory)) {
                    return true;
                }
            }
        }        
        return false;
    }
    
    private List<TerritoryTile> territoryTiles;
    
    @Override
    public void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        
        territoryTiles = createTerritoryTiles(controller.getBoard().getTerritories());
                
        for (TerritoryTile tile : territoryTiles) {
            tile.draw(canvas, camerax, cameray);
                        
            for (Territory t : controller.getBoard().getBorderingTerritories(tile.getTerritory())) {
                
                for (TerritoryTile border : territoryTiles)  {
                    if (t.equals(border.getTerritory())) {
                        canvas.drawLine(border.getX() + camerax, border.getY() + cameray, tile.getX() + camerax, tile.getY() + cameray, GREEN);
                    }
                }
            }
        }
    }
    
    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            startPosition.x = (int)event.getX();
            startPosition.y = (int)event.getY();
            return true;
        } else if(event.getAction() == MotionEvent.ACTION_MOVE) {

            camerax = (int)event.getX() - startPosition.x + camerax;
            cameray = (int)event.getY() - startPosition.y + cameray;
            
            startPosition.x = (int)event.getX();            
            startPosition.y = (int)event.getY();

            invalidate();

            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            
            TerritoryTile clicked = null;
            
            for (TerritoryTile territory : territoryTiles) {
                if (territory.hasClicked((int)event.getX() - camerax, (int)event.getY() - cameray)) {
                    clicked = territory;
                    System.out.println("clicked");
                    break;
                }                
            }
            
            if (clicked != null) {
                
                PieceCriteria criteria = new PieceCriteria();
                criteria.setTerritory(clicked.getTerritory());
                criteria.setOwner(controller.getPlayer());
                
                final List<Piece> pieceOptions = controller.getBoard().getPieces(criteria);
                
                if (pieceOptions.size() == 1) {
                    startPieceMoveActivity(pieceOptions.get(0));                    
                } else if (!pieceOptions.isEmpty()) {
                    showChooseUnitDialog(pieceOptions);
                }
                
            }
            
            return true;            
        } else {
            return super.onTouchEvent(event);
        }
    }

    private void showChooseUnitDialog(final List<Piece> pieceOptions) {
        List<String> options = new ArrayList<String>();
        
        for (Piece piece : pieceOptions) {
            options.add(piece.toString());
        }
        
        AlertDialog.Builder ab=new AlertDialog.Builder(getContext());
        ab.setTitle("Choose a unit");
        ab.setItems(options.toArray(new String[options.size()]), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg, int choice) {
                Piece selected = pieceOptions.get(choice);
                
                startPieceMoveActivity(selected);
            }
        });
        
        ab.show();
    }
    
    private void startPieceMoveActivity(final Piece selected) {
        Intent intent = new Intent(getContext(), PlayerActionActivity.class);
        intent.putExtra(PlayerActionActivity.PIECE_NAME, selected.getName());

        getContext().startActivity(intent);
    }

}
