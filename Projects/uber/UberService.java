package Projects.uber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UberService {
    private final List<Rider> riders;
    private final List<Driver> drivers;
    private final Map<String, Ride> rides;
    private final ExecutorService executorsFindDriver;

    public UberService() {
        this.riders = new CopyOnWriteArrayList<>();
        this.drivers = new CopyOnWriteArrayList<>();
        this.rides = new ConcurrentHashMap<>();
        this.executorsFindDriver = Executors.newCachedThreadPool();
    }

    // --- Users ---

    public Rider createRider(String name) {
        User user = new User(name);
        Rider rider = new Rider(user);
        riders.add(rider);
        return rider;
    }

    public Ride userCreateRide(Rider rider, Location start, Location end) throws InterruptedException {
        // Check User does not have active rider
        if (rider.getRide() != null)  {
            throw new RuntimeException("Rider already has active ride");
        }

        // Create Ride & Rider
        Ride ride = new Ride(rider, start, end);

        // Add to Rides
        rides.put(rider.getUser().getUserId(), ride);

        // Find Driver (Async)
        executorsFindDriver.submit(() -> {
            try {
                findDriver(ride);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        return ride;
    }

    // --- Drivers ---

    public Driver createDriver(String name, String licensePlate, Location location) {
        User user = new User(name);
        Driver driver = new Driver(user, licensePlate, location);
        drivers.add(driver);
        return driver;
    }

    public void findDriver(Ride ride) throws InterruptedException {
        List<Driver> list = List.copyOf(drivers);

        // Find Drivers within 10 miles
        PriorityQueue<DriverInQueue> driversQueue = new PriorityQueue<>((driver1, driver2) -> {
            return Double.compare(driver1.distance, driver2.distance);
        });

        for (Driver driver : list) {
            double distance = Location.getDistance(driver.getLocation(), ride.getStartLocation());

            // Within 25 miles
            if (distance <= 25) {
                driversQueue.add(new DriverInQueue(driver, distance));
            }
        }

        // Print DriversQueue
        System.out.println("=== FindDriver Queue ===");
        PriorityQueue<DriverInQueue> printQueue = new PriorityQueue<>(driversQueue);
        while (printQueue.size() > 0) {
            DriverInQueue driver = printQueue.poll();
            System.out.println("- " + driver.driver.getUser().getName() + " : " + driver.distance);
        }

        // Petition every Driver to Accept (starting from Closest First)
        while (driversQueue.size() > 0) {
            // Check if Ride has been Accepted
            if (ride.getStatus() != RideStatus.PENDING) {
                break;
            }

            // Get Driver
            DriverInQueue nextElement = driversQueue.poll();
            Driver driver = nextElement.driver;

            // Skip Driver if RideRequest Active
            if (driver.getRideRequest() != null) {
                continue;
            }

            // Set PendingDriver
            ride.setPendingDriverLock(driver);
            driver.setRideRequest(ride);

            // Allow 10 seconds to Accept/Reject (poll every 100ms)
            int waitedMs = 0;
            while (waitedMs <= 3000) {
                // Ride Accepted
                if (ride.getStatus() != RideStatus.PENDING) {
                    return;
                }

                // Ride Rejected: Petition next Driver
                if (driver.getRideRequest() == null) {
                    break;
                }

                Thread.sleep(100);
                waitedMs = waitedMs + 100;
            }

            // Reset Driver (No Selection Made)
            if (driver.getRideRequest() != null) {
                driver.clearRideRequest();
                ride.setPendingDriverLock(null);
            }
        }

        // No Valid Driver Found: Cancel Ride
        if (ride.getStatus() == RideStatus.PENDING) {
            ride.cancelRideNoDriverFound();
        }

    }

    private static class DriverInQueue {
        Driver driver;
        double distance;

        public DriverInQueue(Driver driver, double distance) {
            this.driver = driver;
            this.distance = distance;
        }
    }
}
