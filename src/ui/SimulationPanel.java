package ui;

import car.Car;
import car.CarState;
import car.Direction;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SimulationPanel extends Pane {

    private final double WIDTH = 1000;
    private final double HEIGHT = 700;
    private final double ROAD_WIDTH = 140;
    private final double SIDEWALK_WIDTH = 10;

    private final Canvas canvas;
    private final GraphicsContext gc;
    private final Renderer renderer;
    private final Car car;

    public SimulationPanel() {
        setPrefSize(WIDTH, HEIGHT);

        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        renderer = new Renderer();

    //    car = new Car(470, 200, 2, Direction.SOUTH, CarState.MOVING, Color.AZURE);

        getChildren().add(canvas);

        drawMap();
        drawCar();
    }

    private void drawMap() {
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        double centerX = WIDTH / 2;
        double centerY = HEIGHT / 2;
        
        drawSidewalks();
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0, centerY - ROAD_WIDTH / 2, WIDTH, ROAD_WIDTH);
        gc.fillRect(centerX - ROAD_WIDTH / 2, 0, ROAD_WIDTH, HEIGHT);

        drawRoadLines();
        drawTrafficLights();
    }

    private void drawSidewalks() {
        double centerX = WIDTH / 2;
        double centerY = HEIGHT / 2;

        gc.setFill(Color.LIGHTGRAY);

        gc.fillRect(0, centerY - ROAD_WIDTH / 2 - SIDEWALK_WIDTH, WIDTH, SIDEWALK_WIDTH);
        gc.fillRect(0, centerY + ROAD_WIDTH / 2, WIDTH, SIDEWALK_WIDTH);
        gc.fillRect(centerX - ROAD_WIDTH / 2 - SIDEWALK_WIDTH, 0, SIDEWALK_WIDTH, HEIGHT);
        gc.fillRect(centerX + ROAD_WIDTH / 2, 0, SIDEWALK_WIDTH, HEIGHT);
    }

    private void drawRoadLines() {
        double centerX = WIDTH / 2;
        double centerY = HEIGHT / 2;

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);

        for (double x = 0; x < WIDTH; x += 30) {
            gc.strokeLine(x, centerY, x + 15, centerY);
        }

        for (double y = 0; y < HEIGHT; y += 30) {
            gc.strokeLine(centerX, y, centerX, y + 15);
        }

        gc.setFill(Color.DARKGRAY);
        gc.fillRect(centerX - ROAD_WIDTH / 2,centerY - ROAD_WIDTH / 2,ROAD_WIDTH,ROAD_WIDTH);
        
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(4);

        gc.strokeLine(centerX - ROAD_WIDTH / 2, centerY - ROAD_WIDTH / 2 , centerX, centerY - ROAD_WIDTH / 2);
        gc.strokeLine(centerX, centerY + ROAD_WIDTH / 2 , centerX + ROAD_WIDTH / 2, centerY + ROAD_WIDTH / 2);
        gc.strokeLine(centerX - ROAD_WIDTH / 2, centerY, centerX - ROAD_WIDTH / 2, centerY + ROAD_WIDTH / 2);
        gc.strokeLine(centerX + ROAD_WIDTH / 2, centerY - ROAD_WIDTH / 2, centerX + ROAD_WIDTH / 2, centerY);
    }

    private void drawTrafficLights() {
        double centerX = WIDTH / 2;
        double centerY = HEIGHT / 2;

        drawTrafficLight(centerX - ROAD_WIDTH / 2 - 25,centerY - ROAD_WIDTH / 2 - 50);
        drawTrafficLight(centerX + ROAD_WIDTH / 2 + 10,centerY - ROAD_WIDTH / 2 - 50);
        drawTrafficLight(centerX - ROAD_WIDTH / 2 - 25,centerY + ROAD_WIDTH / 2 + 10);
        drawTrafficLight(centerX + ROAD_WIDTH / 2 + 10,centerY + ROAD_WIDTH / 2 + 10);
    }

    private void drawTrafficLight(double x, double y) {
        gc.setFill(Color.BLACK);
        gc.fillRect(x, y, 18, 40);

        gc.setFill(Color.RED);
        gc.fillOval(x + 4, y + 4, 10, 10);

        gc.setFill(Color.DARKGREEN);
        gc.fillOval(x + 4, y + 26, 10, 10);
    }

    private void drawCar() {
        renderer.drawCar(gc, car);
    }
}

