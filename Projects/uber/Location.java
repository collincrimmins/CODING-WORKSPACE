package Projects.uber;

public class Location {
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static double getDistance(Location start, Location end) {
        // Euclidean
        return Math.sqrt(Math.pow(start.getX() - end.getX(), 2) + Math.pow(start.getY() - end.getY(), 2));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
}
