package ui;

import car.Car;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Renderer {

    public void drawCar(GraphicsContext gc, Car car) {
        double x = 100; //car.getX();
        double y = 100; //car.getY();

        gc.setFill(Color.AZURE); // hna ghadi nrej3ohcolor random 3la 7sab subject
        gc.fillRoundRect(x, y, 28, 50, 7, 7);

        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(x + 4, y + 9, 20, 12);
        gc.fillRect(x + 4, y + 29, 20, 12);

        gc.setFill(Color.YELLOW);
        gc.fillOval(x + 3, y + 1, 7, 7);
        gc.fillOval(x + 18, y + 1, 7, 7);

        gc.setFill(Color.RED);
        gc.fillOval(x + 3, y + 43, 7, 5);
        gc.fillOval(x + 18, y + 43, 7, 5);
    }
}