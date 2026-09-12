package Projects.uber;

public class _Main {
    public static void main(String[] args) throws InterruptedException {
        UberService system = new UberService();

        // Create Users
        Rider rider1 = system.createRider("#1 Rider");
        Rider rider2 = system.createRider("#2 Rider");
        Rider rider3 = system.createRider("#3 Rider");
        Driver driver1 = system.createDriver("Joe Driver", "123456", 
            new Location(10, 10)
        );
        Driver driver2 = system.createDriver("Jill Driver", "abcdef", 
            new Location(12, 12)
        );
        Driver driver3 = system.createDriver("Jose Driver", "abc123", 
            new Location(15, 15)
        );
        Driver driver4 = system.createDriver("Jannete Driver", "etw123", 
            new Location(45, 45)
        );
         Driver driver5 = system.createDriver("Jose Driver", "abc123", 
            new Location(500, 500)
        );

        // Create Ride
        Location startLocation = new Location(5, 5);
        Location endLocation = new Location(25, 25);
        Ride ride1 = system.userCreateRide(rider1, startLocation, endLocation);
        Thread.sleep(500);
        Ride ride2 = system.userCreateRide(rider2, startLocation, endLocation);

        Thread.sleep(1000);

        // Error: Creating a Ride on a User with an active ride
        // Ride rideInvalid = system.userCreateRide(rider1, startLocation, endLocation);

        // Error: Driver5 tries to accept Ride
        // driver5.acceptRideRequest();

        // Driver1 accepts Ride 1
        driver1.acceptRideRequest();

        // Driver2 accepts Ride 2
        driver2.acceptRideRequest();

        // No Driver accepts Ride3
        Ride ride3 = system.userCreateRide(rider3, startLocation, endLocation);

        // Finish Rides
        Thread.sleep(1500);
        ride1.setRideFinished();
        ride2.setRideFinished();

        // Pay
        rider1.payForRide(ride1);
        rider2.payForRide(ride2);






    }

    /*
        Design and implement a Ride Sharing Service that allows riders 
        to request rides, drivers to accept trips, and the system to manage 
        trip assignments, payments, and trip status.  
        https://medium.com/@avinashsoni9829/low-level-design-uber-ride-booking-system-1525af82f6fd 
        https://github.com/ashishps1/awesome-low-level-design/blob/main/solutions/java/src/ridesharingservice/README.md

        Requirements
        - Rider can Request ride (based on location)
        - System pings every closest driver (in order), driver has 10 seconds to accept/reject
        - Simple Fee Strategy of Distance * $5.00
        
        class UberService
        - List<User> users
        - List<Driver> drivers
        + userCreateRide(user, startLocation, endLocation) -> Ride
        + find(location, List<Drivers>) -> List<Drivers>
        + driverAcceptRide(ride)
        + driverRejectRide(ride)
        + pay(Ride)

        class Ride
        - String id
        - User user
        - Driver driver
        - Location start
        - Location end
        - Double fare

        enum RideStatus
        - PENDING, ACTIVE, COMPLETED, CANCELLED_NO_DRIVER

        class Location
        - Lat
        - Long
        + getDistance(location1, location2)

        class User
        - String id
        - String name

        class Rider

        class Driver
        - String licenseplate

        Extensibility:
        - Strategy: for Payments (PaymentCreditCard, PaymentApplePay)
        - Strategy: for Driver Finding (simpleClosestDistance, ...)



    */
}
