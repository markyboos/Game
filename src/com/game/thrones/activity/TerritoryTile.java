
package com.game.thrones.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.game.thrones.engine.actions.MoveAction;
import com.game.thrones.model.Team;
import com.game.thrones.model.Territory;
import com.game.thrones.model.piece.Piece;
import java.util.List;

/**
 * Tile for rendering on the map canvas view.
 *
 * @author James
 */
public class TerritoryTile {
    
    public static final int SIZE = 100;
    
    private final static Paint WHITE = new Paint();
    private final static Paint HIGHLIGHT = new Paint();
    private final static Paint HIGHLIGHT_SELF = new Paint();
    private final static Paint BLACK = new Paint();
    private final static Paint TERRITORY_TEXT = new Paint();
    private final static Paint TAINTED = new Paint();

    static {
        WHITE.setColor(Color.WHITE);
        WHITE.setAntiAlias(true);

        BLACK.setColor(Color.BLACK);

        HIGHLIGHT.setColor(Color.rgb(176,224,230));
        HIGHLIGHT.setAntiAlias(true);

        HIGHLIGHT_SELF.setColor(Color.rgb(250, 224, 176));
        HIGHLIGHT_SELF.setAntiAlias(true);

        //brown
        //rgb(139,69,19)

        TAINTED.setColor(Color.rgb(139, 69, 19));
        TAINTED.setFakeBoldText(true);
        TAINTED.setAntiAlias(true);
        TAINTED.setTextSize(SIZE / 10);

        TERRITORY_TEXT.setColor(Color.BLACK);
        TERRITORY_TEXT.setFakeBoldText(true);
        TERRITORY_TEXT.setAntiAlias(true);
        TERRITORY_TEXT.setTextSize(SIZE / 10);
    }
    
    private final int x;
    private final int y;
    
    private final Territory territory;
    
    private final List<Piece> pieces;
    
    public TerritoryTile(final Territory territory, int x, int y, final List<Piece> pieces) {
        this.territory = territory;
        this.x = x;
        this.y = y;
        this.pieces = pieces;
    }
    
    public int getX() {
        return x * SIZE + SIZE / 2;
    }

    public int getXScaled(float scale) {
        int sizeToScale = (int)(SIZE * scale);
        return x * sizeToScale + sizeToScale / 2;
    }
    
    public int getY() {

        return y * SIZE + SIZE / 2;
    }

    public int getYScaled(float scale) {
        int sizeToScale = (int)(SIZE * scale);
        return y * sizeToScale + sizeToScale / 2;
    }
    
    public Territory getTerritory() {
        return territory;
    }
    
    public void draw(final Canvas canvas, int cameraX, int cameraY, boolean shouldHighlightMove,
                     boolean shouldHighlightAction) {
        //Territory Team Colour
        canvas.drawCircle(x * SIZE + cameraX + (SIZE / 2), y * SIZE + cameraY + (SIZE / 2), SIZE / 2, getHouseColour(territory.getOwner()));

        Paint circleColor = WHITE;

        if (shouldHighlightMove) {
            circleColor = HIGHLIGHT;
        }
        if (shouldHighlightAction) {
            circleColor = HIGHLIGHT_SELF;
        }

        //Territory
        canvas.drawCircle(x * SIZE + cameraX + (SIZE / 2), y * SIZE + cameraY + (SIZE / 2), SIZE / 2.1f, circleColor);

        //HIGHLIGHT

        //Name
        canvas.drawText(territory.getName(), x * SIZE + (SIZE / 4) + cameraX, (y+1) * SIZE + cameraY + (SIZE / 10), TERRITORY_TEXT);

        if (territory.getTainted() > 0) {
            canvas.drawText(Integer.toString(territory.getTainted()), x * SIZE + (SIZE / 4) + cameraX, (y+1) * SIZE + cameraY + (SIZE / 5), TAINTED);
        }
        
        int i = 20;
        
        for (Piece piece : pieces) {
            canvas.drawText(piece.toString(), this.x * SIZE + cameraX, i + y * SIZE + cameraY, getHouseColour(piece.getTeam()));
            i += 20;
        }
    }

    public boolean hasClicked(final int clickedx, final int clickedy, final float scale) {

        if (territory.getName().equals(Territory.CENTRAL_TERRITORY)) {
            Log.d("clicked", clickedx + "," + clickedy);
        }

        int sizeToScale = (int)(SIZE * scale);
        
        Rect bounds = new Rect(x * sizeToScale, y * sizeToScale , x * sizeToScale + sizeToScale, y * sizeToScale + sizeToScale);
        //bounds.
        if (territory.getName().equals(Territory.CENTRAL_TERRITORY)) {
            Log.d("bounds", bounds.toShortString());
        }
        
        return bounds.contains(clickedx, clickedy);        
    }
    
    private Paint getHouseColour(final Team team) {        
        final Paint paint = new Paint();
        
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        
        if (team == Team.DRAGONS) {
            paint.setColor(Color.BLUE);
        } else if (team == Team.ORCS) {
            paint.setColor(Color.GREEN);
        } else if (team == Team.OLD_DEMONS) {
            paint.setColor(Color.RED);
        } else if (team == Team.UNDEAD) {
            paint.setColor(Color.BLACK);
        } else if (team == Team.NO_ONE) {
            paint.setColor(Color.GRAY);
        }
                
        return paint;
    }
}
