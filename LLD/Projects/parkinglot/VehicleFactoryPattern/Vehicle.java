package LLD.Projects.parkinglot.VehicleFactoryPattern;

import LLD.Projects.parkinglot.FareStrategyPattern.ParkingFeeStrategy;
import LLD.Projects.parkinglot.enums.DurationType;

public abstract class Vehicle {
    private String licensePlate;
    private String vehicleType;
    private ParkingFeeStrategy feeStrategy;

    public Vehicle(String licensePlate, String vehicleType, ParkingFeeStrategy feeStrategy) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.feeStrategy = feeStrategy;
    }

     public String getVehicleType() {
        return vehicleType;
    }
    
    public String getLicensePlate() {
        return licensePlate;
    }

    public double calculateFee(int duration, DurationType durationType) {
        return feeStrategy.calculateFee(vehicleType, duration, durationType);
    }
}
