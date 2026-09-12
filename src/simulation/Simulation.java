package simulation;

import intersection.Intersection;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.SimulationPanel;

public class Simulation {

    private final SimulationPanel panel;
    private final Intersection intersection;
    private final AnimationTimer timer;

    public Simulation() {
        panel = new SimulationPanel();
        intersection = new Intersection();

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
    }

    public void start(Stage stage) {
        Scene scene = new Scene(panel, 1000, 700);

        stage.setTitle("Traffic Simulation");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        timer.start();
    }

    private void update() {

        //intersection.update();
        //car.update();

    }

    public SimulationPanel getPanel() {
        return panel;
    }

    public Intersection getIntersection() {
        return intersection;
    }
}
