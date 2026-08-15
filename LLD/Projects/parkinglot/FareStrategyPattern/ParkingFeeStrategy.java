package LLD.Projects.parkinglot.FareStrategyPattern;

import LLD.Projects.parkinglot.enums.DurationType;

public interface ParkingFeeStrategy {
    double calculateFee(String vehicleType, int duration, DurationType durationType);
}
