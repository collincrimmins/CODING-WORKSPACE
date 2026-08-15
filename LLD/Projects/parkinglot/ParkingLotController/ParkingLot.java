package LLD.Projects.parkinglot.ParkingLotController;

import java.util.List;

import LLD.Projects.parkinglot.ParkingSpots.ParkingSpot;
import LLD.Projects.parkinglot.VehicleFactoryPattern.Vehicle;

public class ParkingLot {
    public List<ParkingSpot> parkingSpots;

    public ParkingLot(List<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    // Find Spot by Vehicle Type
    public ParkingSpot findAvailableSpot(String vehicleType) {
        for (ParkingSpot spot : parkingSpots) {
            if (!spot.isOccupied()
                && spot.getSpotType().equals(vehicleType)) {
                    return spot;
            }
        }
        return null;
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

    // Get Spot by Number
    public ParkingSpot getSpotByNumber(int spotNumber) {
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getSpotNumber() == spotNumber) {
                return spot;
            }
        }
        return null;
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }
}
