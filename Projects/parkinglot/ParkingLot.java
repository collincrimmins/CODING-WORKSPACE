package Projects.parkinglot;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

public class ParkingLot {
    private static ParkingLot instance;
    private final List<Spot> parkingSpots;
    private final int hourlyRate;

    private ParkingLot(int hourlyRate) {
        this.hourlyRate = hourlyRate;
        this.parkingSpots = new ArrayList<>();

        // Create Spots
        for (int i = 1; i <= 5; i++) {
            Spot spot = new Spot(CarType.SMALL);
            parkingSpots.add(spot);
        }
        for (int i = 1; i <= 5; i++) {
            Spot spot = new Spot(CarType.MEDIUM);
            parkingSpots.add(spot);
        }
        for (int i = 1; i <= 5; i++) {
            Spot spot = new Spot(CarType.LARGE);
            parkingSpots.add(spot);
        }
    }

    public static synchronized ParkingLot getInstance(int hourlyRate) {
        if (instance == null) {
            instance = new ParkingLot(hourlyRate);
        }

        return instance;
    }

    public synchronized boolean enter(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("invalid car");
        }

        CarType carType = car.getCarType();

        // Find Spot w/ Size
        Spot mySpot = null;
        for (Spot spot : parkingSpots) {
            if (spot.getCarType() == carType) {
                if (spot.isOccupied() == false) {
                    mySpot = spot;
                    break;
                }
            }
        }
        if (mySpot == null) {
            return false;
        }

        // Create Ticket
        Ticket ticket = new Ticket(car, System.currentTimeMillis());
        car.setTicket(ticket);
        
        // Set Spot
        car.setSpot(mySpot);
        mySpot.setCar(car);
        mySpot.setOccupied(true);

        return true;
    }

    public synchronized void exit(Car car) {
        if (car.getSpot() == null) {
            return;
        }

        // Ticket
        Ticket ticket = car.getTicket();
        if (ticket == null) {
            throw new RuntimeException("no ticket");
        }
        if (ticket.isUsed()) {
            throw new RuntimeException("ticket invalid");
        }
        ticket.setUsed(true);

        // Pay Fee
        payTicket(ticket);

        // Exit Spot
        Spot spot = car.getSpot();
        spot.setCar(null);
        spot.setOccupied(false);

        // Exit Car
        car.setSpot(null);
        car.setTicket(null);
    }

    private void payTicket(Ticket ticket) {
        double durationMs = System.currentTimeMillis() - ticket.getEntryTime();
        int durationHours = (int) Math.ceil( durationMs / (1000 * 60 * 60));
        int cost = durationHours * hourlyRate;
        System.out.println("Parked x" + durationHours + " hours = $" + cost);
    }


    public List<Spot> getParkingSpots() {
        return parkingSpots;
    }

    public void printParkingSpotsState() {
        for (Spot spot : parkingSpots) {
            if (spot.getCar() == null) {
                System.out.println("spot: {empty}");
                continue;
            }
            System.out.println("spot: " + spot.isOccupied() + " licenseplate: " + spot.getCar().getLicensePlate());
        }
    }

    public int getHourlyRate() {
        return hourlyRate;
    }
}
