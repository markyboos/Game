
package com.game.thrones.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import com.game.thrones.model.House;
import com.game.thrones.model.Territory;
import com.game.thrones.model.piece.Piece;
import java.util.List;

/**
 * Tile for rendering on the map canvas view.
 *
 * @author James
 */
public class TerritoryTile {
    
    public static final int SIZE = 150;
    
    private final static Paint WHITE;
    private final static Paint BLACK;
    
    static {
        WHITE = new Paint();
        WHITE.setColor(Color.WHITE);
        BLACK = new Paint();
        BLACK.setColor(Color.BLACK);
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
    
    public void draw(final Canvas canvas, int camerax, int cameray) {
        
        canvas.drawRect(new Rect(x * SIZE + camerax, y * SIZE + cameray, x * SIZE + SIZE + camerax, y * SIZE + SIZE + cameray), WHITE);
        
        canvas.drawText(territory.getName() + " [" + territory.getTainted() + "]", x * SIZE + camerax, 10 + y * SIZE + cameray, getHouseColour(territory.getOwner()));
        
        canvas.drawCircle(getX() + camerax, getY() + cameray, 2, BLACK);
        
        int i = 20;
        
        for (Piece piece : pieces) {
            canvas.drawText(piece.toString(), this.x * SIZE + camerax, i + y * SIZE + cameray, getHouseColour(piece.getHouse()));
            i += 20;
        }
                
    }

    public boolean hasClicked(final int clickedx, final int clickedy) {
        
        System.out.println(clickedx + "," + clickedy);
        
        Rect bounds = new Rect(x * SIZE, y * SIZE, x * SIZE + SIZE, y * SIZE + SIZE );
        
        System.out.println(bounds.toShortString());
        
        return bounds.contains(clickedx, clickedy);        
    }
    
    private Paint getHouseColour(final House house) {        
        final Paint paint = new Paint();
        
         paint.setColor(Color.BLACK);
        
        
        if (house.getName().equals(House.PLAYER_ONE)) {
            paint.setColor(Color.BLUE);
        }
                
        return paint;
    }
}
