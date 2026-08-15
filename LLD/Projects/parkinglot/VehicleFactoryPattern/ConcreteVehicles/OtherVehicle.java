package LLD.Projects.parkinglot.VehicleFactoryPattern.ConcreteVehicles;

import LLD.Projects.parkinglot.FareStrategyPattern.ParkingFeeStrategy;
import LLD.Projects.parkinglot.FareStrategyPattern.ConcreteStrategies.BasicHourlyRateStrategy;
import LLD.Projects.parkinglot.VehicleFactoryPattern.Vehicle;

public class OtherVehicle extends Vehicle {
    public OtherVehicle(String licensePlate, String vehicleType, ParkingFeeStrategy feeStrategy) {
        super(licensePlate, vehicleType, feeStrategy);
    }
}
