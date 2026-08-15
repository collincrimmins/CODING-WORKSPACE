package LLD.Projects.parkinglot.ParkingSpots.ConcreteParkingSpots;

import LLD.Projects.parkinglot.ParkingSpots.ParkingSpot;
import LLD.Projects.parkinglot.VehicleFactoryPattern.Vehicle;

public class BikeParkingSpot extends ParkingSpot {
    public BikeParkingSpot(int spotNumber, String spotType) {
        super(spotNumber, spotType);
    }

    @Override
    public boolean canParkVehicle(Vehicle vehicle) {
        return "Bike".equalsIgnoreCase(vehicle.getVehicleType());
    }
}
