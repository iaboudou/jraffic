package car;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class CarMovement {

    public static final double CENTER_X = 400;
    public static final double CENTER_Y = 400;
    public static final double LANE_WIDTH = 70;
    public static final double INTERSECTION_HALF = 70;
    public static final double CAR_LENGTH = 26;
    public static final double CAR_WIDTH = 16;
    public static final double SAFETY_GAP = 20;
    public static final double LANE_LENGTH = 340;
    public static final double EXIT_LENGTH = 620;
    public static final double MIN_SPEED = 90;
    public static final double MAX_SPEED = 140;

    private static final int CURVE_STEPS = 60;
    private static final double STOP_MARGIN = 0.1;

    private final List<Car> cars = new ArrayList<>();
    private final RoutePath[] paths;

    public CarMovement() {
        Direction[] directions = Direction.values();
        Car.Route[] routes = Car.Route.values();
        paths = new RoutePath[directions.length * routes.length];

        for (Direction direction : directions) {
            for (Car.Route route : routes) {
                paths[index(direction, route)] = new RoutePath(direction, route);
            }
        }
    }

    public Car spawnCar(Direction direction) {
        return spawnCar(direction, Car.Route.random());
    }

    public Car spawnCar(Direction direction, Car.Route route) {
        for (Car car : cars) {
            if (car.getDirection() == direction && car.getS() < CAR_LENGTH + SAFETY_GAP) {
                return null;
            }
        }

        double speed = MIN_SPEED + Math.random() * (MAX_SPEED - MIN_SPEED);
        Car car = new Car(direction, route, speed);
        cars.add(car);
        updateCarPosition(car);
        return car;
    }

    public void update(double dt, Predicate<Direction> greenLight) {
        if (dt <= 0) {
            return;
        }

        for (Direction direction : Direction.values()) {
            List<Car> lane = carsInLane(direction);
            Car leader = null;

            for (Car car : lane) {
                RoutePath path = path(car.getDirection(), car.getRoute());
                double move = car.getSpeed() * dt;

                if (leader != null) {
                    double maxS = leader.getS() - CAR_LENGTH - SAFETY_GAP;
                    move = Math.min(move, maxS - car.getS());
                }

                if (car.getPhase() == Car.Phase.APPROACHING && !canPass(car, greenLight)) {
                    move = Math.min(move, path.stopS - STOP_MARGIN - car.getS());
                }

                if (move < 0) {
                    move = 0;
                }

                moveCar(car, move, path);
                car.setStopped(move < 0.001);
                leader = car;
            }
        }

        cars.removeIf(car -> car.getPhase() == Car.Phase.DONE);
    }

    public List<Car> getCars() {
        return new ArrayList<>(cars);
    }

    public int carCount() {
        return cars.size();
    }

    public RoutePath path(Direction direction, Car.Route route) {
        return paths[index(direction, route)];
    }

    private List<Car> carsInLane(Direction direction) {
        List<Car> lane = new ArrayList<>();
        for (Car car : cars) {
            if (car.getDirection() == direction && car.getPhase() != Car.Phase.DONE) {
                lane.add(car);
            }
        }
        lane.sort(Comparator.comparingDouble(Car::getS).reversed());
        return lane;
    }

    private boolean canPass(Car car, Predicate<Direction> greenLight) {
        if (greenLight != null && !greenLight.test(car.getDirection())) {
            return false;
        }
        for (Car other : cars) {
            if (other != car && other.getPhase() == Car.Phase.IN_INTERSECTION) {
                if (other.getDirection() != car.getDirection()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void moveCar(Car car, double distance, RoutePath path) {
        double end = Math.min(car.getS() + distance, path.totalS);
        car.setS(end);

        if (end >= path.totalS) {
            car.setPhase(Car.Phase.DONE);
        } else if (end >= path.exitS) {
            car.setPhase(Car.Phase.LEAVING);
        } else if (end >= path.stopS) {
            car.setPhase(Car.Phase.IN_INTERSECTION);
        } else {
            car.setPhase(Car.Phase.APPROACHING);
        }

        updateCarPosition(car);
    }

    private void updateCarPosition(Car car) {
        RoutePath path = path(car.getDirection(), car.getRoute());
        double s = car.getS();

        if (s <= path.stopS) {
            car.setPosition(path.spawnX + path.ax * s, path.spawnY + path.ay * s);
            car.setHeading(angle(path.ax, path.ay));
            return;
        }

        if (s <= path.exitS) {
            Point point = path.pointAt(s - path.stopS);
            car.setPosition(point.x, point.y);
            car.setHeading(point.heading);
            return;
        }

        double exitDistance = s - path.exitS;
        car.setPosition(path.exitX + path.ex * exitDistance, path.exitY + path.ey * exitDistance);
        car.setHeading(angle(path.ex, path.ey));
    }

    private static int index(Direction direction, Car.Route route) {
        return direction.ordinal() * Car.Route.values().length + route.ordinal();
    }

    private static double angle(double dx, double dy) {
        return Math.atan2(dy, dx);
    }

    public static final class RoutePath {
        public final Direction direction;
        public final Car.Route route;
        public final double spawnX;
        public final double spawnY;
        public final double entryX;
        public final double entryY;
        public final double exitX;
        public final double exitY;
        public final double ax;
        public final double ay;
        public final double ex;
        public final double ey;
        public final double stopS = LANE_LENGTH;
        public final double exitS;
        public final double totalS;

        private final double[] curveX = new double[CURVE_STEPS + 1];
        private final double[] curveY = new double[CURVE_STEPS + 1];
        private final double[] curveS = new double[CURVE_STEPS + 1];

        private RoutePath(Direction direction, Car.Route route) {
            this.direction = direction;
            this.route = route;

            ax = direction.dx();
            ay = direction.dy();

            double[] exit = exitVector(direction, route);
            ex = exit[0];
            ey = exit[1];

            double laneX = CENTER_X - ay * LANE_WIDTH / 2;
            double laneY = CENTER_Y + ax * LANE_WIDTH / 2;
            entryX = laneX - ax * INTERSECTION_HALF;
            entryY = laneY - ay * INTERSECTION_HALF;
            spawnX = entryX - ax * LANE_LENGTH;
            spawnY = entryY - ay * LANE_LENGTH;

            double exitLaneX = CENTER_X - ey * LANE_WIDTH / 2;
            double exitLaneY = CENTER_Y + ex * LANE_WIDTH / 2;
            exitX = exitLaneX + ex * INTERSECTION_HALF;
            exitY = exitLaneY + ey * INTERSECTION_HALF;

            double[] c1 = firstControlPoint(route);
            double[] c2 = secondControlPoint(route);
            buildCurve(c1[0], c1[1], c2[0], c2[1]);

            exitS = stopS + curveS[CURVE_STEPS];
            totalS = exitS + EXIT_LENGTH;
        }

        private static double[] exitVector(Direction direction, Car.Route route) {
            double ax = direction.dx();
            double ay = direction.dy();

            switch (route) {
                case RIGHT:
                    return new double[] {-ay, ax};
                case LEFT:
                    return new double[] {ay, -ax};
                default:
                    return new double[] {ax, ay};
            }
        }

        private double[] firstControlPoint(Car.Route route) {
            if (route == Car.Route.STRAIGHT) {
                return midpoint();
            }
            return new double[] {
                    entryX + ax * (INTERSECTION_HALF - LANE_WIDTH / 2),
                    entryY + ay * (INTERSECTION_HALF - LANE_WIDTH / 2)
            };
        }

        private double[] secondControlPoint(Car.Route route) {
            if (route == Car.Route.STRAIGHT) {
                return midpoint();
            }
            return new double[] {
                    exitX - ex * (INTERSECTION_HALF - LANE_WIDTH / 2),
                    exitY - ey * (INTERSECTION_HALF - LANE_WIDTH / 2)
            };
        }

        private double[] midpoint() {
            return new double[] {(entryX + exitX) / 2, (entryY + exitY) / 2};
        }

        // private double[] innerCorner() {
        //     double x = Math.abs(entryX - CENTER_X) > Math.abs(exitX - CENTER_X) ? entryX : exitX;
        //     double y = Math.abs(entryY - CENTER_Y) > Math.abs(exitY - CENTER_Y) ? entryY : exitY;
        //     return new double[] {x, y};
        // }

        private void buildCurve(double c1x, double c1y, double c2x, double c2y) {
            for (int i = 0; i <= CURVE_STEPS; i++) {
                double t = (double) i / CURVE_STEPS;
                double mt = 1 - t;

                curveX[i] = mt * mt * mt * entryX
                        + 3 * mt * mt * t * c1x
                        + 3 * mt * t * t * c2x
                        + t * t * t * exitX;
                curveY[i] = mt * mt * mt * entryY
                        + 3 * mt * mt * t * c1y
                        + 3 * mt * t * t * c2y
                        + t * t * t * exitY;

                if (i > 0) {
                    curveS[i] = curveS[i - 1]
                            + Math.hypot(curveX[i] - curveX[i - 1], curveY[i] - curveY[i - 1]);
                }
            }
        }

        private Point pointAt(double distance) {
            if (distance <= 0) {
                return pointBetween(0, 1, 0);
            }

            if (distance >= curveS[CURVE_STEPS]) {
                return pointBetween(CURVE_STEPS - 1, CURVE_STEPS, 1);
            }

            for (int i = 1; i <= CURVE_STEPS; i++) {
                if (curveS[i] >= distance) {
                    double length = curveS[i] - curveS[i - 1];
                    double amount = length == 0 ? 0 : (distance - curveS[i - 1]) / length;
                    return pointBetween(i - 1, i, amount);
                }
            }

            return pointBetween(CURVE_STEPS - 1, CURVE_STEPS, 1);
        }

        private Point pointBetween(int a, int b, double amount) {
            double x = curveX[a] + (curveX[b] - curveX[a]) * amount;
            double y = curveY[a] + (curveY[b] - curveY[a]) * amount;
            return new Point(x, y, angle(curveX[b] - curveX[a], curveY[b] - curveY[a]));
        }
    }

    private static final class Point {
        final double x;
        final double y;
        final double heading;

        Point(double x, double y, double heading) {
            this.x = x;
            this.y = y;
            this.heading = heading;
        }
    }
}
