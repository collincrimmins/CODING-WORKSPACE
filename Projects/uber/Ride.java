package Projects.uber;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class Ride {
    private final String id;
    private final Rider rider;
    private Driver driver = null;
    private Driver pendingDriver; // acts as a Lock when RideStatus = PENDING
    private RideStatus status;
    private final Location startLocation;
    private final Location endLocation;
    private final double distance;
    private double fare;
    private Instant startTime;

    public Ride(Rider rider, Location startLocation, Location endLocation) {
        if (rider == null || startLocation == null || endLocation == null) {
            throw new RuntimeException("Invalid Ride creation");
        }

        this.id = "RIDE-" + UUID.randomUUID().toString().substring(0, 8);

        // Set Rider
        this.rider = rider;
        rider.setRide(this);

        this.status = RideStatus.PENDING;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distance = Location.getDistance(startLocation, endLocation);
        this.fare = FareService.calculateFare(startLocation, endLocation);
    }

    // --- Ride ---

    public synchronized void setPendingDriverLock(Driver driver) {
        if (status != RideStatus.PENDING && driver != null) {
            throw new RuntimeException("Pending Driver cannot be set unless in PENDING state");
        }

        this.pendingDriver = driver;
    }

    public void startRideWithPendingDriver() throws InterruptedException {
        if (driver != null || status == RideStatus.ACTIVE) {
            throw new RuntimeException("Invalid setDriver()");
        }

        // Set Driver
        this.driver = pendingDriver;

        // Start Ride
        status = RideStatus.ACTIVE;
        startTime = Instant.now();

        // Print
        System.out.println("[" + id + "] Starting...");
        System.out.println("[" + id + "] Driver: " + driver.getUser().getName());
        System.out.println("[" + id + "] Rider: " + rider.getUser().getName());
    }

    public void setRideFinished() {
        // Set Status
        status = RideStatus.COMPLETED;

        // Update Driver & Rider
        rider.clearRide();
        driver.clearRide();

        // Get Time
        Duration time = Duration.between(startTime, Instant.now());
        long seconds = time.toSeconds();

        // Print
        System.out.println("[" + id + "] Finished Ride in " + seconds + "s");
    }

    public void cancelRideNoDriverFound() {
        System.out.println("[" + id + "] No Driver Found, Ride Cancelled");

        // Status
        status = RideStatus.CANCELLED_NO_DRIVER;
    }

    // --- Getters ---

    public String getId() {
        return id;
    }

    public Rider getRider() {
        return rider;
    }

    public Driver getDriver() {
        return driver;
    }

    public Driver getPendingDriver() {
        return pendingDriver;
    }

    public RideStatus getStatus() {
        return status;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public double getDistance() {
        return distance;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public double getFare() {
        return fare;
    }

}
