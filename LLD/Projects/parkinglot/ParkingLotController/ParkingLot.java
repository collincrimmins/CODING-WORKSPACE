package LLD.Projects.parkinglot.ParkingLotController;

import java.util.List;

import LLD.Projects.parkinglot.ParkingFloor.ParkingFloor;
import LLD.Projects.parkinglot.ParkingSpots.ParkingSpot;
import LLD.Projects.parkinglot.VehicleFactoryPattern.Vehicle;

public class ParkingLot {
     private List<ParkingFloor> floors; // List of parking floors in the parking lot
    // Constructor to initialize the parking lot with given floors
    public ParkingLot(List<ParkingFloor> floors) {
        this.floors = floors;
    }

    // Find Spot by Vehicle Type
    public ParkingSpot findAvailableSpot(String vehicleType) {
        for (ParkingFloor floor : floors) {
            ParkingSpot spot = floor.findAvailableSpot(vehicleType);
            if (spot != null) {
                return spot; // Return the first available spot found
            }
        }
        return null; // Return null if no spot is available
    }

    // Set Vehicle
    public ParkingSpot parkVehicle(Vehicle vehicle) {
        ParkingSpot spot = findAvailableSpot(vehicle.getVehicleType());

        // Found Spot
        if (spot != null) {
            spot.parkVehicle(vehicle);
            System.out.println("Vehicle parked successfully in spot: " + spot.getSpotNumber());
            return spot;
        }

        // No Spot Available
        System.out.println("No parking spots available for " + vehicle.getVehicleType() + "!");
        return null;
    }

    // Exit Parking Spot
    public void vacateSpot(ParkingSpot spot, Vehicle vehicle) {
        if (spot != null && spot.isOccupied()
            && spot.getVehicle().equals(vehicle)) {
            spot.vacate();
            System.out.println(vehicle.getVehicleType()
                    + " vacated the spot: " + spot.getSpotNumber());
        } else {
            System.out.println("Invalid operation! Either the spot is already vacant "
                    + "or the vehicle does not match.");
        }
    }

    // Method to retrieve a parking spot by its spot number
    public ParkingSpot getSpotByNumber(int spotNumber) {
        for (ParkingFloor floor : floors) {
        for (ParkingSpot spot : floor.getParkingSpots()) {
            if (spot.getSpotNumber() == spotNumber) {
            return spot; // Return the parking spot if found
            }
        }
        }
        return null; // Return null if no spot with the given number exists
    }
    // Getter method to retrieve the list of parking floors
    public List<ParkingFloor> getFloors() {
        return floors;
    }
}
