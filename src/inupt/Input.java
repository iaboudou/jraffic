package input;

import car.CarMovement;
import car.Direction;
import ui.SimulationPanel;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Input {

    public static CarMovement carMovement;
    public static SimulationPanel panel;

    public static void handleKeyPress(KeyEvent event) {

        switch (event.getCode()) {
            case UP: carMovement.spawnCar(Direction.SOUTH)  ; break;
            case DOWN: carMovement.spawnCar(Direction.NORTH); break;
            case RIGHT: carMovement.spawnCar(Direction.WEST); break;
            case LEFT: carMovement.spawnCar(Direction.EAST) ; break;
            case R:
                int random = (int) (Math.random() * 4);
                switch (random) {
                    case 0: carMovement.spawnCar(Direction.NORTH); break;
                    case 1: carMovement.spawnCar(Direction.SOUTH); break;
                    case 2: carMovement.spawnCar(Direction.WEST) ; break;
                    case 3: carMovement.spawnCar(Direction.EAST) ; break;
                }
                break;

            case ESCAPE:
                Stage stage = (Stage) panel.getScene().getWindow();
                stage.close();
                break;
        }
    }
}