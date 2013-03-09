
package com.game.thrones.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.game.thrones.engine.GameController;
import com.game.thrones.model.House;
import com.game.thrones.model.Territory;

/**
 *
 * @author James
 */
public class MapView extends View {
    
    private final static Paint WHITE;
    private final static Paint BLACK;
    
    static {
        WHITE = new Paint();
        WHITE.setColor(Color.WHITE);
        BLACK = new Paint();
        BLACK.setColor(Color.BLACK);
    }
        
    private GameController controller;    
    
    public MapView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        
        controller = GameController.getInstance();
    }
    
    @Override
    public void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        
        Territory territory = controller.getBoard().getFirstTerritory();
        
        controller.getBoard().getBorderingTerritories(territory);
        
        drawTerritory(canvas, territory);
        
        Paint houseColor = getHouseColour(null);
        
        //canvas.drawCircle(2f, 2f, 10f, houseColor);
    }
    
    private void drawTerritory(final Canvas canvas, final Territory territory) {
        
        canvas.drawRect(new Rect(0, 0, 100, 100), WHITE);
        
        canvas.drawText(territory.getName(), 0, 10, BLACK);
        
        //controller.getBoard().get;
        
        
    }

    private Paint getHouseColour(final House house) {        
        final Paint paint = new Paint();
        
        paint.setColor(Color.RED);
        
        return paint;
    }

}
