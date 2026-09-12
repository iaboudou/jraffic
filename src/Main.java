
import javafx.application.Application;
import javafx.stage.Stage;
import simulation.Simulation;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Simulation simulation = new Simulation();
        simulation.start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}