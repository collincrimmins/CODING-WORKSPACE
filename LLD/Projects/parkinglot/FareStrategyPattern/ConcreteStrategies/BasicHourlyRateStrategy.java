package LLD.Projects.parkinglot.FareStrategyPattern.ConcreteStrategies;

import LLD.Projects.parkinglot.FareStrategyPattern.ParkingFeeStrategy;
import LLD.Projects.parkinglot.enums.DurationType;

public class BasicHourlyRateStrategy implements ParkingFeeStrategy {
    @Override
    public double calculateFee(String vehicleType, int duration, DurationType durationType) {
        String vehicle = vehicleType.toLowerCase();
        
        if (vehicle.equals("car")) {
            return durationType == DurationType.HOURS
                ? duration * 10.0 // $10 per hour for cars
                : duration * 10.0 * 24; // Daily rate
        } else if (vehicle.equals("bike")) {
            return durationType == DurationType.HOURS
                ? duration * 5.0 // $10 per hour for 
                : duration * 5.0 * 24; // Daily rate
        } if (vehicle.equals("auto")) {
            return durationType == DurationType.HOURS
                ? duration * 8.0 // $10 per hour for
                : duration * 8.0 * 24; // Daily rate
        } else {
            return durationType == DurationType.HOURS
                ? duration * 15.0 // $10 per hour for
                : duration * 15.0 * 24; // Daily rate
        }
    }
    
}
