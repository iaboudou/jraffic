package simulation;

import car.CarMovement;
import intersection.Intersection;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.SimulationPanel;
import input.Input;

public class Simulation {

    public SimulationPanel panel;
    public Intersection intersection;
    public CarMovement carMovement;
    public AnimationTimer timer;
    public long lastTime;

    public Simulation() {

        panel = new SimulationPanel();
        carMovement = new CarMovement();
        intersection = new Intersection(carMovement);
        Input.carMovement = carMovement;
        Input.panel = panel;

        timer = new AnimationTimer() {

            @Override
            public void handle(long now) {

                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                double dt = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;
                update(dt);
            }
        };
    }

    public void start(Stage stage) {

        Scene scene = new Scene(panel, 800, 800);

        scene.setOnKeyPressed(Input::handleKeyPress);
        stage.setTitle("Traffic Simulation");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        timer.start();
    }

    private void update(double dt) {
        intersection.update(dt);
        carMovement.update(dt, intersection::isGreen);
        panel.setCars(carMovement.getCars());
        panel.setIntersection(intersection);
        panel.draw();
    }
}