package intersection;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ui.Map;

public class TrafficLight {

    public static final double RADIUS = 9.0;
    public static final double OFFSET = 12.0;

    private static void drawLight( GraphicsContext gc, double x, double y, Color color ) {
        gc.setFill(color);
        gc.fillOval( x, y, RADIUS * 2, RADIUS * 2 );
    }

    public static void draw(GraphicsContext gc) {

        double x = Map.X;
        double y = Map.Y;
        double half = Map.INTERSECTION_HALF;

        // north west
        drawLight(gc, x - half - OFFSET - RADIUS, y - half - OFFSET - RADIUS, Color.RED);
        
        // north east
        drawLight(gc, x + half + OFFSET - RADIUS, y - half - OFFSET - RADIUS, Color.RED);

        // south east 
        drawLight(gc, x + half + OFFSET - RADIUS, y + half + OFFSET - RADIUS, Color.GREEN);

        // south west
        drawLight(gc, x - half - OFFSET - RADIUS, y + half + OFFSET - RADIUS, Color.RED);

    }

}