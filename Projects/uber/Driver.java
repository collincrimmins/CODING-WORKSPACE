package Projects.uber;

public class Driver {
    private final User user;
    private final String licensePlate;
    // Location
    private Location location;
    // Ride
    private Ride ride;
    private Ride rideRequest;
    private final Object rideLock = new Object();

    public Driver(User user, String licensePlate, Location location) {
        this.user = user;
        this.licensePlate = licensePlate;
        this.ride = null;
        this.rideRequest = null;
        this.location = location;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public User getUser() {
        return user;
    }

    // --- Ride & RideRequest ---

    public Ride getRideRequest() {
        synchronized(rideLock) {
            return rideRequest;
        }
    }

    public void setRideRequest(Ride rideRequest) {
        synchronized(rideLock) {
            if (this.rideRequest != null) {
                throw new RuntimeException("driver already has active ride request");
            }

            this.rideRequest = rideRequest;
        }
    }

    public void acceptRideRequest() throws InterruptedException {
        Ride activeRide = null;

        synchronized(rideLock) {
            if (rideRequest == null) {
                throw new RuntimeException("driver does not have a ride request");
            }

            // Set Ride
            ride = rideRequest;
            activeRide = rideRequest;

            // Clear RideRequets
            rideRequest = null;
        }

        // Start Ride & Set Me as Driver
        activeRide.startRideWithPendingDriver();
    }

    public void rejectRideRequest() throws InterruptedException {
        synchronized(rideLock) {
            if (rideRequest == null) {
                throw new RuntimeException("driver does not have a ride request");
            }

            System.out.println(user.getName() + " rejected the ride...");

            // Reject & Remove Me from Driver
            rideRequest.setPendingDriverLock(null);
            rideRequest = null;
        }
    }

    public void clearRide() {
        synchronized(rideLock) {
            if (this.ride == null) {
                throw new RuntimeException("User does not have an active ride");
            }

            ride = null;
        }
    }

    public void clearRideRequest() {
        synchronized(rideLock) {
            rideRequest = null;
        }
    }

    // --- Location ---

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override 
    public String toString() {
        if (ride == null) {
            return user.getUserId() + " " + user.getName();
        } else {
            return user.getUserId() + " " + user.getName() + " RideStatus: " + ride.getStatus();
        }
    }
    
    
}
