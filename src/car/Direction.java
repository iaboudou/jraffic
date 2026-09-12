package car;

public enum Direction {
    NORTH,
    SOUTH,
<<<<<<< HEAD
    WEST,
    EAST;

    public Direction opposite() {
        switch (this) {
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
            case EAST:
                return WEST;
            default:
                throw new IllegalStateException();
        }
    }

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
=======
    EAST,
    WEST
}
>>>>>>> 04f7102be3d5b428df3014300e5e67943aabba3f
