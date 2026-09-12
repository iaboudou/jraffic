import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import ui.Map;
import intersection.TrafficLight;


public class Main extends Application {

    @Override
    public void start(Stage stage) {

        final double WIDTH = 900;
        final double HEIGHT = 900;

        // canva
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // map
        Map.drawMap(gc);
        TrafficLight.draw(gc);

        // scene
        Pane root = new Pane(canvas);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        // window
        stage.setTitle("JTraffic");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}


