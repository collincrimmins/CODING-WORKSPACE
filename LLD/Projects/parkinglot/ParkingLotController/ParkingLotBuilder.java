package LLD.Projects.parkinglot.ParkingLotController;

import java.util.ArrayList;
import java.util.List;

import LLD.Projects.parkinglot.FareStrategyPattern.ConcreteStrategies.BasicHourlyRateStrategy;
import LLD.Projects.parkinglot.ParkingFloor.ParkingFloor;
import LLD.Projects.parkinglot.ParkingSpots.ConcreteParkingSpots.BikeParkingSpot;
import LLD.Projects.parkinglot.ParkingSpots.ConcreteParkingSpots.CarParkingSpot;
import LLD.Projects.parkinglot.VehicleFactoryPattern.ConcreteVehicles.OtherVehicle;

public class ParkingLotBuilder {
    private List<ParkingFloor> floors;

    public ParkingLotBuilder() {
        this.floors = new ArrayList<>();
    }

    public ParkingLotBuilder addFloor(ParkingFloor floor) {
        floors.add(floor);
        return this;
    }

    public ParkingLotBuilder createFloor(int floorNumber, int numOfCarSpots,
        int numOfBikeSpots, int... otherSpotCounts) {
        // create floor
        ParkingFloor floor = new ParkingFloor(floorNumber);

        // Add car spots
        for (int i = 0; i < numOfCarSpots; i++) {
            floor.addParkingSpot(new CarParkingSpot(i + 1, "Car"));
        }
        // Add bike spots
        for (int i = 0; i < numOfBikeSpots; i++) {
            floor.addParkingSpot(new BikeParkingSpot(numOfCarSpots + i + 1, "Bike"));
        }

        // Add other types of spots if provided
        int spotOffset = numOfCarSpots + numOfBikeSpots;
        for (int i = 0; i < otherSpotCounts.length; i++) {
            for (int j = 0; j < otherSpotCounts[i]; j++) {
                // Dynamically add other vehicle type spots
                // Note: This uses OtherVehicle as a placeholder. In a real system,
                // you might want a more robust way to handle different vehicle types
                // floor.addParkingSpot(new OtherVehicle(
                //     spotOffset + j + 1, new BasicHourlyRateStrategy()));
                //  }
                // Update the spot offset for the next type of vehicle
                spotOffset += otherSpotCounts[i];
            }
        }
        // Add the configured floor to the list of floors
        floors.add(floor);
        return this;
    }

    public ParkingLot build() {
        return new ParkingLot(floors);
    }
}
