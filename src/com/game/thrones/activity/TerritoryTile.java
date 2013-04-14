
package com.game.thrones.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
    private final static Paint BLACK = new Paint();
    private final static Paint TERRITORY_TEXT = new Paint();

    static {
        WHITE.setColor(Color.WHITE);
        WHITE.setAntiAlias(true);

        BLACK.setColor(Color.BLACK);

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
    
    public int getY() {
        return y * SIZE + SIZE / 2;
    }
    
    public Territory getTerritory() {
        return territory;
    }
    
    public void draw(final Canvas canvas, int cameraX, int cameraY) {
        //Territory Team Colour
        canvas.drawCircle(x * SIZE + cameraX + (SIZE / 2), y * SIZE + cameraY + (SIZE / 2), SIZE / 2, getHouseColour(territory.getOwner()));

        //Territory
        canvas.drawCircle(x * SIZE + cameraX + (SIZE / 2), y * SIZE + cameraY + (SIZE / 2), SIZE / 2.1f, WHITE);

        //Name
        canvas.drawText(territory.getName(), x * SIZE + (SIZE / 4) + cameraX, (y+1) * SIZE + cameraY + (SIZE / 10), TERRITORY_TEXT);

        
        int i = 20;
        
        for (Piece piece : pieces) {
            canvas.drawText(piece.toString(), this.x * SIZE + cameraX, i + y * SIZE + cameraY, getHouseColour(piece.getTeam()));
            i += 20;
        }
    }

    public boolean hasClicked(final int clickedx, final int clickedy) {
        
        //System.out.println(clickedx + "," + clickedy);
        
        Rect bounds = new Rect(x * SIZE, y * SIZE, x * SIZE + SIZE, y * SIZE + SIZE);

        
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
        } else if (team == Team.NO_ONE) {
            paint.setColor(Color.GRAY);
        }
                
        return paint;
    }
}
