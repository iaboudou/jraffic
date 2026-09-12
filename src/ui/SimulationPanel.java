package ui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class SimulationPanel extends Pane {

    private final double WIDTH = 1000;
    private final double HEIGHT = 700;

    private final double ROAD_WIDTH = 140;
    private final double SIDEWALK_WIDTH = 10;

    public SimulationPanel() {
        setPrefSize(WIDTH, HEIGHT);
        drawMap();
    }

    private void drawMap() {
        Rectangle background = new Rectangle(
                WIDTH,
                HEIGHT
        );

        background.setFill(Color.LIGHTGREEN);

        getChildren().add(background);

        double centerX = WIDTH / 2;
        double centerY = HEIGHT / 2;

        Rectangle horizontalRoad = new Rectangle(0,centerY - ROAD_WIDTH / 2,WIDTH,ROAD_WIDTH);

        horizontalRoad.setFill(Color.DARKGRAY);

        Rectangle verticalRoad = new Rectangle(centerX - ROAD_WIDTH / 2,0,ROAD_WIDTH,HEIGHT);

        verticalRoad.setFill(Color.DARKGRAY);

        getChildren().addAll(horizontalRoad,verticalRoad);

        drawSidewalks();
        drawRoadLines();
        drawCrosswalks();
        drawTrafficLights();
    }

    private void drawSidewalks() {
        double centerX = WIDTH / 2;
        double centerY = HEIGHT / 2;

        Rectangle top = new Rectangle(0,centerY - ROAD_WIDTH / 2 - SIDEWALK_WIDTH,WIDTH,SIDEWALK_WIDTH);

        Rectangle bottom = new Rectangle(0,centerY + ROAD_WIDTH / 2,WIDTH,SIDEWALK_WIDTH);

        Rectangle left = new Rectangle(centerX - ROAD_WIDTH / 2 - SIDEWALK_WIDTH,0,SIDEWALK_WIDTH,HEIGHT);

        Rectangle right = new Rectangle(centerX + ROAD_WIDTH / 2,0,SIDEWALK_WIDTH,HEIGHT);

        top.setFill(Color.LIGHTGRAY);
        bottom.setFill(Color.LIGHTGRAY);
        left.setFill(Color.LIGHTGRAY);
        right.setFill(Color.LIGHTGRAY);

        getChildren().addAll(top,bottom,left,right);
    }

    private void drawRoadLines() {
        double centerX = WIDTH / 2;
        double centerY = HEIGHT / 2;

        for (double x = 0; x < WIDTH; x += 35) {
            Line line = new Line(x,centerY,x + 18,centerY);


            line.setStroke(Color.WHITE);
            line.setStrokeWidth(2);

            getChildren().add(line);
        }

        for (double y = 0; y < HEIGHT; y += 35) {
            Line line = new Line(centerX,y,centerX,y + 18);

            line.setStroke(Color.WHITE);
            line.setStrokeWidth(2);

            getChildren().add(line);
        }
    }

    private void drawCrosswalks() {
        double centerX = WIDTH / 2;
        double centerY = HEIGHT / 2;

        for (int i = 0; i < 7; i++) {

            Rectangle north = new Rectangle(centerX - 45 + i * 15,centerY - ROAD_WIDTH / 2 + 5,8,25);

            Rectangle south = new Rectangle(centerX - 45 + i * 15,centerY + ROAD_WIDTH / 2 - 30,8,25);

            Rectangle west = new Rectangle(centerX - ROAD_WIDTH / 2 + 5,centerY - 45 + i * 15,25,8);

            Rectangle east = new Rectangle(centerX + ROAD_WIDTH / 2 - 30,centerY - 45 + i * 15,25,8);

            north.setFill(Color.WHITE);
            south.setFill(Color.WHITE);
            west.setFill(Color.WHITE);
            east.setFill(Color.WHITE);

            getChildren().addAll(north,south,west,east);
        }
    }

    private void drawTrafficLights() {
        double centerX = WIDTH / 2;
        double centerY = HEIGHT / 2;

        drawTrafficLight(centerX - ROAD_WIDTH / 2 - 25,centerY - ROAD_WIDTH / 2 - 50);

        drawTrafficLight(centerX + ROAD_WIDTH / 2 + 10,centerY - ROAD_WIDTH / 2 - 50);

        drawTrafficLight(centerX - ROAD_WIDTH / 2 - 25,centerY + ROAD_WIDTH / 2 + 10);

        drawTrafficLight(centerX + ROAD_WIDTH / 2 + 10,centerY + ROAD_WIDTH / 2 + 10);
    }

    private void drawTrafficLight(double x,double y) {
        Rectangle box = new Rectangle(x,y,18,40);

        box.setFill(Color.BLACK);

        Circle red = new Circle(x + 9,y + 9,5,Color.RED);

        Circle green = new Circle(x + 9,y + 31,5,Color.DARKGREEN);

        getChildren().addAll(box,red,green);
    }
}

