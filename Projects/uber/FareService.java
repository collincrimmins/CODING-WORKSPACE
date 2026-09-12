package Projects.uber;

public class FareService {
    public static double calculateFare(Location start, Location end) {
        double farePerMile = 5.00;
        return Location.getDistance(start, end) * farePerMile;
    }
}
