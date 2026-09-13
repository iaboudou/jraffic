package ui;

import car.Car;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class Renderer {

    public void drawCar(GraphicsContext gc, Car car) {
        double x = car.getX();
        double y = car.getY();

        gc.save();
        
        Rotate r = new Rotate(car.getHeadingDegrees(), x, y);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());

        double width = 16;
        double length = 26;
        double drawX = x - length / 2;
        double drawY = y - width / 2;

        gc.setFill(Color.web(car.getColor()));
        gc.fillRoundRect(drawX, drawY, length, width, 4, 4);

        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(drawX + 6, drawY + 2, 4, width - 4);
        gc.fillRect(drawX + length - 10, drawY + 2, 4, width - 4);

        gc.restore();
    }
}