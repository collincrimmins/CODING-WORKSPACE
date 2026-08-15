package LLD.Projects.parkinggarage.ParkingGarage;

import java.util.ArrayList;
import java.util.List;

import LLD.Projects.parkinggarage.Fee.Fee;
import LLD.Projects.parkinggarage.ParkingFloor.ParkingFloor;
import LLD.Projects.parkinggarage.ParkingSpace.ParkingSpace;
import LLD.Projects.parkinggarage.Payments.PaymentStrategy;
import LLD.Projects.parkinggarage.Payments.Concrete.CashPayment;
import LLD.Projects.parkinggarage.Vehicles.Vehicle;
import LLD.Projects.parkinggarage.enums.VehicleSize;

public class ParkingGarage {
    List<ParkingFloor> floors;

    // Constructor
    private ParkingGarage() {
        // Create Garage
        floors = new ArrayList<>();
    }

    // Get List of Floors
    public List<ParkingFloor> getFloors() {
        return floors;
    }

    // Find Space
    public ParkingSpace getOpenParkingSpace(Vehicle vehicle) {
        // Check Vehicle is not already parked
        if (vehicle.getParkingSpace() != null) {
            System.out.println("X - Vehicle " + vehicle.getLicensePlate() + " is already parked");
            return null;
        }

        // Find Space
        for (ParkingFloor floor : floors) {
            for (ParkingSpace space : floor.getParkingSpaces()) {
                // Park in my Space Size
                if (space.canFitCar(vehicle)) {
                    if (!space.isOccupied()) {
                        space.setVehicle(vehicle);
                        vehicle.setParkingSpace(space);
                        return space;
                    }
                }
            }
        }

        // No Space
        System.out.println("X - No available space for vehicle size of " + vehicle.getVehicleSize());
        return null;
    }

    // Exit Car
    public void vehicleExitParkingGarage(Vehicle vehicle, PaymentStrategy vehiclePaymentStrategy) {
        // Exit Space
        ParkingSpace space = vehicle.getParkingSpace();
        space.exitSpace();
        
        // Charge Fee on Exit
        int numHours = 2;
        double fee = Fee.calculatePayment(numHours);
        vehiclePaymentStrategy.pay(fee);

        System.out.println("Succesfully exited " + vehicle.getLicensePlate());
    }

    // Print
    public void printGarageInfo() {
        int level = 1;
        for (ParkingFloor floor : floors) {
            System.out.println("Level " + level);
            for (ParkingSpace space : floor.getParkingSpaces()) {
                System.out.println("- Space [" + space.getSpaceSize() + "]: occupied " + space.isOccupied());
            }
            level = level + 1;
        }
    }

    // Builder
    public static class Builder {
        ParkingGarage garage;

        public Builder() {
            // Create Garage
            this.garage = new ParkingGarage();
        }

        public Builder addFloor(int floorNumber, int numSmall, int numMedium, int numLarge) {
            // Add Floor
            ParkingFloor newFloor = new ParkingFloor(floorNumber, numSmall, numMedium, numLarge);
            garage.floors.add(newFloor);
            return this;
        }

        public ParkingGarage build() {
            return garage;
        }
    }
}
