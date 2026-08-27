package Projects.parkinggarage;

import Projects.parkinggarage.Fee.FeeStrategy;
import Projects.parkinggarage.ParkingGarage.ParkingGarage;
import Projects.parkinggarage.ParkingSpace.ParkingSpace;
import Projects.parkinggarage.Payments.Concrete.CashPayment;
import Projects.parkinggarage.Payments.Concrete.CreditCardPayment;
import Projects.parkinggarage.Vehicles.Vehicle;
import Projects.parkinggarage.Vehicles.VehicleFactory;
import Projects.parkinggarage.enums.VehicleSize;
import Projects.parkinggarage.notifications.Concrete.EmailNotification;
import Projects.parkinggarage.notifications.Concrete.TextNotification;

/*
    Requirements
    - Parking Garage/Lot has a set # of Spots for Big/Medium cars and Small for bikes
    - Cars Big/Small and Bike
    - On Enter, check parking for open spots by size
    - On Exit, charge based on # of hours and car size, charge using PaymentTypes like Cash/Credit

    Entities
    - Vehicle
    - ParkingGarage / ParkingLot / ParkingSpace

    Patterns
    - Builder: ParkingGarage .newFloor(...)
    - Strategy: Payment Type
    - Factory: Vehicle by size
    - Observor: Exit notification (Email, SMS)
*/


public class Main {
    public static void main(String[] args) {
        // Create Parking Garage
        ParkingGarage garage = new ParkingGarage.Builder()
                                    .addFloor(1, 1, 1, 1)
                                    //.addFloor(2, 1, 1, 1)
                                    //.addFloor(3, 1, 1, 1)
                                    .build();
        //garage.printGarageInfo();

        // Create Vehicles
        Vehicle car1 = VehicleFactory.createVehicle("ABCDEF", VehicleSize.MEDIUM);
        Vehicle car2 = VehicleFactory.createVehicle("ABCDEFfwef", VehicleSize.MEDIUM);
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
        //garage.vehicleExitParkingGarage(bike1, new CashPayment());
        garage.vehicleExitParkingGarage(car1, new CashPayment());
        //garage.vehicleExitParkingGarage(truck1, new CreditCardPayment());


        // Concurrency Test
        boolean runConcurrenyTest = true;

        if (runConcurrenyTest) {
            ParkingSpace space = new ParkingSpace(VehicleSize.MEDIUM);
            new Thread(() -> {
                space.setVehicle(car1);
            }).start();

            new Thread(() -> {
                try {
                    // One of the threads will win, the other will hit the `if (occupied)` check
                    // and throw a RuntimeException!
                    space.setVehicle(car2); 
                } catch (Exception e) {
                    System.out.println("Caught expected concurrency collision: " + e.getMessage());
                }
            }).start();
        }
        

        garage.printGarageInfo();
    }
}