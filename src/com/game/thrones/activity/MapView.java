
package com.game.thrones.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.*;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.game.thrones.engine.ActionCreator;
import com.game.thrones.engine.GameController;
import com.game.thrones.engine.actions.AbstractAction;
import com.game.thrones.engine.actions.Action;
import com.game.thrones.engine.actions.AttackAction;
import com.game.thrones.engine.actions.AttackGeneralAction;
import com.game.thrones.engine.actions.HealAction;
import com.game.thrones.engine.actions.MoveAction;
import com.game.thrones.engine.actions.QuestAction;
import com.game.thrones.model.AllFilter;
import com.game.thrones.model.Territory;
import com.game.thrones.model.PieceTerritoryFilter;
import com.game.thrones.model.hero.Hero;
import com.game.thrones.model.piece.Piece;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

/**
 *
 * @author James
 */
public class MapView extends View implements CameraChangeListener {
    
    private final static Paint GREEN = new Paint();
    private final static Paint BACKGROUND = new Paint();
    private final static Paint BLACK = new Paint();

    static {
        GREEN.setColor(Color.GREEN);
        BACKGROUND.setColor(Color.rgb(200, 180, 120));
        BLACK.setColor(Color.BLACK);
    }
        
    private GameController controller;

    private ScaleGestureDetector mScaleDetector;
    private int cameraX = 0;
    private int cameraY = 0;
    private float mScaleFactor = 2f;
    
    private final Point startPosition = new Point();
    
    public MapView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        
        controller = GameController.getInstance();
        
        territoryTiles = createTerritoryTiles();

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    private HudUpdateListener listener;

    public void setHUDListener(HudUpdateListener hul) {
        listener = hul;
    }
    
    Territory[][] map = new Territory[20][20];
    Queue<Territory> itemsVisited = new LinkedList<Territory>();
    
    public List<TerritoryTile> createTerritoryTiles() {
        
        List<TerritoryTile> tiles = new ArrayList<TerritoryTile>();
        itemsVisited.clear();
        
        map[10][10] = controller.getBoard().getCentralTerritory();
        populateTree(map[10][10]);
        
        List<Piece> pieces = controller.getBoard().getPieces(AllFilter.INSTANCE, Piece.class);
        
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

    private Comparator<Territory> territoryComparator = new Comparator<Territory>() {
        @Override
        public int compare(Territory lhs, Territory rhs) {
            Integer lSize = controller.getBoard().getBorderingTerritories(lhs).size();
            Integer rSize = controller.getBoard().getBorderingTerritories(lhs).size();
            return lSize.compareTo(rSize);
        }
    };
    
    private void populateTree(Territory territory ) {

        itemsVisited.add(territory);

        while (!itemsVisited.isEmpty()) {
            Territory item = itemsVisited.poll();
            List<Territory> neighbours = controller.getBoard().getBorderingTerritories(item);

            //Collections.sort(neighbours, territoryComparator);
            Territory child;
            while((child= getUnvisitedTerritory(neighbours))!=null) {
                Point point = getMapPoint(item);
                populateClosestTile(point.x, point.y, child);
                itemsVisited.add(child);
            }
        }
    }

    private Territory getUnvisitedTerritory(List<Territory> neighbours) {
        for (Territory t : neighbours) {
            if (!alreadyInMap(t)) {
                return t;
            }
        }
        return null;
    }

    private Point populateClosestTile(int x, int y, Territory territory) {
        
        for (int i = x - 2; i < x + 3; i += 2) {
            for (int j = y - 2; j < y + 3; j += 2) {
                if (i < 0 || j < 0 || i >= map.length || j >= map[0].length) {
                    continue;
                }
                
                if (map[i][j] == null) {
                    map[i][j] = territory;

                    return new Point(i, j);
                }                
            }
        }

        //here you are stuffed, just put it wherever
        for (int i = 0; i < map.length; i ++) {
            for (int j = 0; j < map.length; j++) {
                if (map[i][j] == null) {
                    map[i][j] = territory;

                    return new Point(i, j);
                }
            }
        }

        throw new IllegalStateException("Unable to put territory " + territory.getName() + " on map");
    }

    private Point getMapPoint(Territory t) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == null) {
                    continue;
                }
                if (map[i][j].equals(t)) {
                    return new Point(i, j);
                }
            }
        }
        return null;
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
    private List<MoveAction> moveActions = new ArrayList<MoveAction>();
    private List<Action> atHeroActions = new ArrayList<Action>();
    
    @Override
    public void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);

        canvas.drawRect(new Rect(0, 0, canvas.getWidth(), canvas.getHeight()), BACKGROUND);
        
        territoryTiles = createTerritoryTiles();

        // todo draw the borders first, then the tiles so that they display correctly.
        // This bit looks really inefficient, should be re-done.
        for (TerritoryTile tile : territoryTiles) {
            for (Territory t : controller.getBoard().getBorderingTerritories(tile.getTerritory())) {

                for (TerritoryTile border : territoryTiles)  {
                    if (t.equals(border.getTerritory())) {
                        canvas.drawLine(border.getX() + cameraX, border.getY() + cameraY, tile.getX() + cameraX, tile.getY() + cameraY, BLACK);
                    }
                }
            }
        }

        for (TerritoryTile tile : territoryTiles) {
            tile.draw(canvas, cameraX, cameraY, IsTerritoryOneOfMoves(tile.getTerritory()),
                    IsActionable(tile.getTerritory()));
        }

        canvas.restore();
    }

    private boolean IsTerritoryOneOfMoves(Territory t) {
        for (MoveAction ma : moveActions) {
            if (ma.getMovingTo().equals(t)) {
                return true;
            }
        }
        return false;
    }

    private boolean IsActionable(Territory t) {
        for (Action ma : atHeroActions) {
            if (ma instanceof AbstractAction) {
                if (((AbstractAction)ma).getPiece().getPosition().equals(t)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        
        if (!isEnabled()) {
            return false;
        }

        mScaleDetector.onTouchEvent(event);
        
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            startPosition.x = (int)event.getX();
            startPosition.y = (int)event.getY();
            return true;
        } else if(event.getAction() == MotionEvent.ACTION_MOVE) {

            if (!mScaleDetector.isInProgress()) {

                cameraX = (int) event.getX() - startPosition.x + cameraX;
                cameraY = (int) event.getY() - startPosition.y + cameraY;

                startPosition.x = (int) event.getX();
                startPosition.y = (int) event.getY();

                invalidate();
            }

            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            
            TerritoryTile clicked = null;
            Log.d("scale", Float.toString(mScaleFactor));
            Log.d("positionx", Float.toString(event.getX()));
            Log.d("positiony", Float.toString(event.getY()));
            Log.d("camx", Integer.toString(cameraX));
            Log.d("camy", Integer.toString(cameraY));

            
            for (TerritoryTile territory : territoryTiles) {
                int x = (int)event.getX() - (int)(cameraX * mScaleFactor);
                int y =(int)event.getY() - (int)(cameraY * mScaleFactor);

                if (territory.hasClicked(x, y, mScaleFactor)) {
                    clicked = territory;
                    break;
                }                
            }
            
            if (clicked != null) {
                final List<Piece> pieceOptions = controller.getBoard().getPieces(
                        new PieceTerritoryFilter(clicked.getTerritory()), Piece.class);
                
                if (pieceOptions.contains(controller.getPlayer())) {
                    if (controller.getPlayer().getActionsAvailable() == 0) {
                        showNoMovesLeftDialog();
                    } else {
                        if (!atHeroActions.isEmpty()) {
                            startPieceMoveActivity(controller.getPlayer());
                        } else {
                            resetMoves();
                        }
                    }
                } else {
                    if (!moveActions.isEmpty()) {
                        for (MoveAction action : moveActions) {
                            if (action.getMovingTo().equals(clicked.getTerritory())) {
                                takeMove(action);
                                break;
                            }
                        }

                        return true;
                    }
                }

            }
            
            return true;            
        }

        return true;
    }

    public void resetMoves() {
        List<Action> actions = new ActionCreator().createActions(controller.getPlayer());

        moveActions.clear();
        atHeroActions.clear();

        for (Action a : actions) {
            if (a instanceof MoveAction) {
                moveActions.add((MoveAction)a);
            }
        }

        for (Action a : actions) {
            if (!(a instanceof MoveAction)) {
                atHeroActions.add(a);
            }
        }
    }

    private void takeMove(Action selected) {
        ActionTaker actionTaker = new ActionTaker(controller.getPlayer(), getContext(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                resetMoves();
                ((Activity)getContext()).onWindowFocusChanged(true);
                listener.UpdateHUD();
                return true;
            }
        });

        actionTaker.takeAction(selected);
    }

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(1.5f, Math.min(mScaleFactor, 3.0f));

            invalidate();
            return true;
        }
    }
    
    private void showNoMovesLeftDialog() {
        AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
        ab.setTitle("No moves left");
        ab.setNeutralButton("OK", null);
        
        ab.show();
    }

    private void showChooseUnitDialog(final List<Piece> pieceOptions) {
        List<String> options = new ArrayList<String>();
        
        for (Piece piece : pieceOptions) {
            options.add(piece.toString());
        }
        
        AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
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

    public void fireCameraChangeEvent(final CameraChangeEvent e) {
        Log.d("fire camera", "camera change fired....");

        Point center = centerOfScreen();

        Log.d("screen center", center.x + " " + center.y);
        
        for (TerritoryTile tile : territoryTiles) {
            if (tile.getTerritory().equals(e.getFocus())) {

                Log.d("tile xy", tile.getX() + " " + tile.getY());
                Log.d("sf", "" + mScaleFactor);

                //center the screen rather than the territory size
                //should be -700,-600
                cameraX = (int)(TerritoryTile.SIZE * mScaleFactor) - tile.getX();
                cameraY = (int)(TerritoryTile.SIZE * mScaleFactor) - tile.getY();
                return;
            }
        }
        
        this.postInvalidate();
    }

    public Point centerOfScreen() {
        return new Point((int)(this.getX() + this.getWidth()  / 2), (int)(this.getY() + this.getHeight() / 2) - 400);
    }
}
