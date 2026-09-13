package intersection;

import car.Car;
import car.Direction;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import car.CarMovement;


public class Intersection {
    private static final double timer = 6.0;
    private static final Direction[] LIGHTS =  { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST };
    private static  double[] lastTimeLightChanged = { 0, 0, 0, 0 }; 

    private final CarMovement carMovement;
    private int activeLight = 0;

    // e is time since the last light change
    private double e = 0.0;

    public Intersection(CarMovement carMovement) {
        this.carMovement = carMovement;
    }

    // update the intersection state

    public void update(double dt) {
    
        // key: direction 
        // value: number of cars in that direction
        Map<String, Integer> carsByDirection = getCarsByDirection();
    
        e += dt;
    
        // update time for all lights except the active one
        for (int i = 0; i < lastTimeLightChanged.length; i++) {
            if (i != activeLight) {
                lastTimeLightChanged[i] += dt;
            }
        }
    
        // min is 2 sec to pass the light
        if (e < timer - 2) {
            return;
        }
    
        // if all the directions are empty, then just make a normal ligth swap
    
        boolean empty = true;
        for (Direction direction : LIGHTS) {
            if (carsByDirection.getOrDefault(direction.toString(), 0) > 0) {
                empty = false;
                break;
            }
        }
    
        if (empty && e >= timer) {
            e -= timer;
            lastTimeLightChanged[activeLight] = 0.0;
            activeLight = (activeLight + 1) % LIGHTS.length;
            return;
        }
        
    
        // try find the direction that has been waiting more than 20 seconds and has cars waiting
        // d is the direction to switch to
        // worstWait is the time that direction has been waiting
        int d = -1;
        double worstWait = -1;
    
        for (int i = 0; i < LIGHTS.length; i++) {
            if (i == activeLight) continue;
    
            // if the ligth has been waiting more than 20 sec and there are cars waiting
            int waitingCars = carsByDirection.getOrDefault(LIGHTS[i].toString(), 0);
            if (lastTimeLightChanged[i] >= 20.0 && waitingCars > 0) {
                if (lastTimeLightChanged[i] > worstWait) {
                    worstWait = lastTimeLightChanged[i];
                    d = i;
                }
            }
        }
    
        // in case of direction has been waiting more than 20 seconds, switch active light to that direction
        if (d != -1) {
            lastTimeLightChanged[activeLight] = 0.0;
            activeLight = d;
            e = 0.0;
            return;
        }
    
        // if the current light has no cars waiting, switch to the direction with the most cars waiting
        if (!empty && carsByDirection.getOrDefault(LIGHTS[activeLight].toString(), 0) == 0) {
        
            int b = activeLight;
            int maxCars = -1;
            // try find which is with the most cars waiting
            for (int i = 0; i < LIGHTS.length; i++) {
                int count = carsByDirection.getOrDefault(LIGHTS[i].toString(), 0);
                if (count > maxCars) {
                    maxCars = count;
                    b = i;
                }
            }
    
            lastTimeLightChanged[activeLight] = 0.0;
            activeLight = b;
            e = 0.0;
            return;
        }
    
        // since we tried to switch the light, we need to wait at least 5 seconds before switching again
    
        if (e >= timer) {
            e -= timer;
                        
            int b = activeLight;
            int maxCars = -1;
            for (int i = 0; i < LIGHTS.length; i++) {
                int count = carsByDirection.getOrDefault(LIGHTS[i].toString(), 0);
                if (count > maxCars) {
                    maxCars = count;
                    b = i;
                }
            }
    
            lastTimeLightChanged[activeLight] = 0.0;
            activeLight = b;
        }
    }

    // check if the light is green for a given direction
    public boolean isGreen(Direction direction) {
        return direction == LIGHTS[activeLight];
    }

    // return a map containing the number of cars in each direction
    public Map<String, Integer> getCarsByDirection() {
        // System.out.println(carMovement.getCars());

        Map<String, Integer> mp = new HashMap<>();
        for (Direction direction : Direction.values()) {
            mp.put(direction.toString(), 0);
        }

        for (Car car : carMovement.getCars()) {
            Direction direction = car.getDirection();
            // System.out.println(direction.toString());
            mp.put(direction.toString(), mp.get(direction.toString()) + 1);
        }

        return mp;
    }
}