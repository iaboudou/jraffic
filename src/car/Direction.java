package car;

public enum Direction {
    NORTH,
    SOUTH,
    WEST,
    EAST;

    public double dx() {
        switch (this) {
            case WEST:
                return 1;
            case EAST:
                return -1;
            default:
                return 0;
        }
    }

    public double dy() {
        switch (this) {
            case NORTH:
                return 1;
            case SOUTH:
                return -1;
            default:
                return 0;
        }
    }
}