package ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Map {

    public static final double SCREEN_SIZE = 900.0;
    public static final double STOP_LINE_THICKNESS = 4.0;
    public static final double INTERSECTION_HALF = 60.0;

    public static final double X = SCREEN_SIZE / 2;
    public static final double Y = SCREEN_SIZE / 2;


    public static void drawMap(GraphicsContext gc) {

        // Roads
        gc.setFill(Color.DARKGRAY);
        gc.fillRect( X - INTERSECTION_HALF, 0, INTERSECTION_HALF * 2, SCREEN_SIZE);
        gc.fillRect(0,Y - INTERSECTION_HALF,SCREEN_SIZE, INTERSECTION_HALF * 2);

        // Center lines
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(2.0);

        gc.strokeLine(X, 0, X, Y - INTERSECTION_HALF);
        gc.strokeLine(X, Y + INTERSECTION_HALF, X, SCREEN_SIZE);
        gc.strokeLine(0, Y, X - INTERSECTION_HALF, Y);
        gc.strokeLine(X + INTERSECTION_HALF, Y, SCREEN_SIZE,  Y);

        // Intersection
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2.0);

        gc.strokeRect(X - INTERSECTION_HALF,Y - INTERSECTION_HALF,INTERSECTION_HALF * 2,INTERSECTION_HALF * 2);

        // Stop lines
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(STOP_LINE_THICKNESS);

        gc.strokeLine(X, Y + INTERSECTION_HALF, X + INTERSECTION_HALF, Y + INTERSECTION_HALF);
        gc.strokeLine(X - INTERSECTION_HALF, Y - INTERSECTION_HALF, X, Y - INTERSECTION_HALF);
        gc.strokeLine(X - INTERSECTION_HALF, Y, X - INTERSECTION_HALF, Y + INTERSECTION_HALF);
        gc.strokeLine(X + INTERSECTION_HALF, Y - INTERSECTION_HALF, X + INTERSECTION_HALF, Y);
    }
}