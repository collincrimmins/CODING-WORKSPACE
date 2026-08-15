package LLD.Projects.parkinglot.ParkingSpots.ConcreteParkingSpots;

import LLD.Projects.parkinglot.ParkingSpots.ParkingSpot;
import LLD.Projects.parkinglot.VehicleFactoryPattern.Vehicle;

public class CarParkingSpot extends ParkingSpot {
    public CarParkingSpot(int spotNumber, String spotType) {
        super(spotNumber, spotType);
    }

    @Override
    public boolean canParkVehicle(Vehicle vehicle) {
        return "Car".equalsIgnoreCase(vehicle.getVehicleType());
    }
}
