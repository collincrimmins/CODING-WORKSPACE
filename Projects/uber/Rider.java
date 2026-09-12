package Projects.uber;

public class Rider  {
    private final User user;
    private Ride ride;

    public Rider(User user) {
        this.user = user;
        this.ride = null;
    }

    public User getUser() {
        return user;
    }

    // --- Ride ---

    public void setRide(Ride ride) {
        if (this.ride != null) {
            throw new RuntimeException("User already has an active Ride");
        }

        this.ride = ride;
    }

    public void clearRide() {
        if (this.ride == null) {
            throw new RuntimeException("User does not have an active ride");
        }

        ride = null;
    }

    public Ride getRide() {
        return ride;
    }

    // --- Print ---

    @Override 
    public String toString() {
        if (ride == null) {
            return user.getUserId() + " " + user.getName();
        } else {
            return user.getUserId() + " " + user.getName() + " RideStatus: " + ride.getStatus();
        }
    }
    
    // --- Pay ---

    public void payForRide(Ride ride) {
        double fare = ride.getFare();
        System.out.println("[" + ride.getId() + "] " + user.getName() + " paid $" + fare + " - Distance: " + ride.getDistance() + " miles");
    }
}
