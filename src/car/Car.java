package car;

public class Car {

    public enum Route {
        STRAIGHT("#1e90ff"),
        LEFT("#ff9800"),
        RIGHT("#ffd700");

        private final String color;

        Route(String color) {
            this.color = color;
        }

        public String color() {
            return color;
        }

        public static Route random() {
            Route[] routes = values();
            return routes[(int) (Math.random() * routes.length)];
        }
    }

    public enum Phase {
        APPROACHING,
        IN_INTERSECTION,
        LEAVING,
        DONE
    }

    private final Direction direction;
    private final Route route;
    private final double speed;

    private double s;
    private double x;
    private double y;
    private double heading;
    private boolean stopped;
    private Phase phase = Phase.APPROACHING;

    public Car(Direction direction, Route route, double speed) {
        if (speed <= 0) {
            throw new IllegalArgumentException("speed must be positive");
        }
        this.direction = direction;
        this.route = route;
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public Route getRoute() {
        return route;
    }

    public double getSpeed() {
        return speed;
    }

    public String getColor() {
        return route.color();
    }

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getHeading() {
        return heading;
    }

    public double getHeadingDegrees() {
        return Math.toDegrees(heading);
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }
}
