package LLD.Projects.parkinglot.VehicleFactoryPattern;

import LLD.Projects.parkinglot.FareStrategyPattern.ParkingFeeStrategy;
import LLD.Projects.parkinglot.VehicleFactoryPattern.ConcreteVehicles.BikeVehicle;
import LLD.Projects.parkinglot.VehicleFactoryPattern.ConcreteVehicles.CarVehicle;
import LLD.Projects.parkinglot.VehicleFactoryPattern.ConcreteVehicles.OtherVehicle;

public class VehicleFactory {
    public static Vehicle createVehicle(String vehicleType, String licensePlate, ParkingFeeStrategy feeStrategy) {
        if (vehicleType.equalsIgnoreCase("Car")) {
            return new CarVehicle(licensePlate, vehicleType, feeStrategy);
        } else if (vehicleType.equalsIgnoreCase("Bike")) {
            return new BikeVehicle(licensePlate, vehicleType, feeStrategy);
        }
        return new OtherVehicle(licensePlate, vehicleType, feeStrategy);
    }
}
