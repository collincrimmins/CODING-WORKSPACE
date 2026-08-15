package LLD.Projects.parkinglot.VehicleFactoryPattern.ConcreteVehicles;

import LLD.Projects.parkinglot.FareStrategyPattern.ParkingFeeStrategy;
import LLD.Projects.parkinglot.VehicleFactoryPattern.Vehicle;

public class CarVehicle extends Vehicle {
    public CarVehicle(String licensePlate, String vehicleType, ParkingFeeStrategy feeStrategy) {
        super(licensePlate, vehicleType, feeStrategy);
    }
}
