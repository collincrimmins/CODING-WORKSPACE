package LLD.Projects.parkinggarage;

import LLD.Projects.parkinggarage.Fee.Fee;
import LLD.Projects.parkinggarage.ParkingGarage.ParkingGarage;
import LLD.Projects.parkinggarage.ParkingSpace.ParkingSpace;
import LLD.Projects.parkinggarage.Payments.Concrete.CashPayment;
import LLD.Projects.parkinggarage.Payments.Concrete.CreditCardPayment;
import LLD.Projects.parkinggarage.Vehicles.Vehicle;
import LLD.Projects.parkinggarage.Vehicles.VehicleFactory;
import LLD.Projects.parkinggarage.enums.VehicleSize;

public class Main {
    public static void main(String[] args) {
        // Create Parking Garage
        ParkingGarage garage = new ParkingGarage.Builder()
                                    .addFloor(1, 1, 1, 1)
                                    .addFloor(2, 1, 1, 1)
                                    .addFloor(3, 1, 1, 1)
                                    .build();
        garage.printGarageInfo();

        // Create Vehicles
        Vehicle car1 = VehicleFactory.createVehicle("ABCDEF", VehicleSize.MEDIUM);
        Vehicle truck1 = VehicleFactory.createVehicle("JGIREK", VehicleSize.LARGE);
        Vehicle bike1 = VehicleFactory.createVehicle("123456", VehicleSize.SMALL);

        // Park my cars
        garage.getOpenParkingSpace(car1);
        garage.getOpenParkingSpace(car1);
        // garage.getOpenParkingSpace(car1);
        // garage.getOpenParkingSpace(car1);
        garage.getOpenParkingSpace(truck1);
        // garage.getOpenParkingSpace(truck1);
        // garage.getOpenParkingSpace(truck1);
        // garage.getOpenParkingSpace(truck1);
        garage.getOpenParkingSpace(bike1);
        // garage.getOpenParkingSpace(bike1);
        // garage.getOpenParkingSpace(bike1);
        // garage.getOpenParkingSpace(bike1);

        // Exit my cars
        garage.vehicleExitParkingGarage(car1, new CashPayment());
        garage.vehicleExitParkingGarage(truck1, new CreditCardPayment());
    }
}

/*
    Patterns
    - Builder: ParkingGarage .newFloor(...)
    - Strategy: Payment Type
    - Factory: Vehicle by size

    Ideas
    - Parking Garage/Lot has a set # of Spots for Big/Medium cars and Small for bikes
    - Cars Big/Small and Bike
    - On Enter, check parking for open spots by size
    - On Exit, charge based on # of hours and car size, charge using PaymentTypes like Cash/Credit

    Classes
        ParkingFloor
        ParkingLot
        Vehicle
        PaymentType
        CostCalculator
        Ticket
        Enums (CarType)

*/
