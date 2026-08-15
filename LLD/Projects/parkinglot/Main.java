package LLD.Projects.parkinglot;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import LLD.Projects.parkinglot.FareStrategyPattern.ParkingFeeStrategy;
import LLD.Projects.parkinglot.FareStrategyPattern.ConcreteStrategies.BasicHourlyRateStrategy;
import LLD.Projects.parkinglot.FareStrategyPattern.ConcreteStrategies.PremiumRateStrategy;
import LLD.Projects.parkinglot.ParkingLotController.ParkingLot;
import LLD.Projects.parkinglot.ParkingLotController.ParkingLotBuilder;
import LLD.Projects.parkinglot.ParkingSpots.ParkingSpot;
import LLD.Projects.parkinglot.ParkingSpots.ConcreteParkingSpots.BikeParkingSpot;
import LLD.Projects.parkinglot.ParkingSpots.ConcreteParkingSpots.CarParkingSpot;
import LLD.Projects.parkinglot.PaymentStrategyPattern.PaymentStrategy;
import LLD.Projects.parkinglot.PaymentStrategyPattern.ConcretePaymentStrategies.CashPayment;
import LLD.Projects.parkinglot.PaymentStrategyPattern.ConcretePaymentStrategies.CreditCardPayment;
import LLD.Projects.parkinglot.VehicleFactoryPattern.Vehicle;
import LLD.Projects.parkinglot.VehicleFactoryPattern.VehicleFactory;
import LLD.Projects.parkinglot.enums.DurationType;

public class Main {
    public static void main(String[] args) {
        ParkingLot parkingLot =
            new ParkingLotBuilder()
            // First floor: 2 car spots, 2 bike spots
            .createFloor(1, 2, 2)
            // Second floor: 3 car spots, 1 bike spot, 1 other vehicle spot
            .createFloor(2, 3, 1, 1)
            .build();

        

        /*
        // Initialize parking spots
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        parkingSpots.add(new CarParkingSpot(1, "Car"));
        parkingSpots.add(new CarParkingSpot(2, "Car"));
        parkingSpots.add(new BikeParkingSpot(3, "Bike"));
        parkingSpots.add(new BikeParkingSpot(4, "Bike"));

        // Create Parking Lot
        ParkingLot parkingLot = new ParkingLot(parkingSpots);

        // Fee Strategy
        ParkingFeeStrategy basicHourlyRateStrategy = new BasicHourlyRateStrategy();
        ParkingFeeStrategy premiumRateStrategy = new PremiumRateStrategy();

        // Create Vehicles & Fee Strategy
        Vehicle car1 = VehicleFactory.createVehicle("Car", "CAR123", basicHourlyRateStrategy);
        Vehicle car2 = VehicleFactory.createVehicle("Car", "CAR345", basicHourlyRateStrategy);
        Vehicle bike1 = VehicleFactory.createVehicle("Bike", "BIKE123", premiumRateStrategy);
        Vehicle bike2 = VehicleFactory.createVehicle("Bike", "BIKE456", premiumRateStrategy);

        // Park the cars
        ParkingSpot carSpot = parkingLot.parkVehicle(car1);
        ParkingSpot bikeSpot = parkingLot.parkVehicle(bike1);
        ParkingSpot carSpot2 = parkingLot.parkVehicle(car2);
        ParkingSpot bikeSpot2 = parkingLot.parkVehicle(bike2);

        // Get Input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select payment method for your vehicle:");
        System.out.println("1. Credit Card");
        System.out.println("2. Cash");
        int paymentMethod = scanner.nextInt();

        if (carSpot != null) {
            double carFee = car1.calculateFee(2, DurationType.HOURS);
            PaymentStrategy carPaymentStrategy = getPaymentStrategy(paymentMethod, carFee);
            carPaymentStrategy.processPayment(carFee);
            parkingLot.vacateSpot(carSpot, car1);
        }
        if (bikeSpot != null) {
            double bikeFee = bike1.calculateFee(3, DurationType.HOURS);
            PaymentStrategy bikPaymentStrategy = getPaymentStrategy(paymentMethod, bikeFee);
            bikPaymentStrategy.processPayment(bikeFee);
            parkingLot.vacateSpot(bikeSpot, bike1);
        }

        scanner.close();
        */
    }

    private static PaymentStrategy getPaymentStrategy(int paymentMethod, double fee) {
        if (paymentMethod == 1) {
            return new CreditCardPayment(fee);
        } else if (paymentMethod == 2) {
            return new CashPayment(fee);
        } else {
            System.out.println("Invalid choice! Default to Credit card payment.");
            return new CreditCardPayment(fee);
        }
    }
}

/*
- Multiple parking spot types (car / bike / etc)
- Vehicle types (bike, car, truck)
- Given parking ticket on entry
- Parking fee = duration of stay & vehicle type
- Exit needs to make a payment
- Payment types: cash, card


*/