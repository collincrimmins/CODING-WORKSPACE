package LLD.Projects.parkinggarage.Vehicles;

import LLD.Projects.parkinggarage.ParkingSpace.ParkingSpace;
import LLD.Projects.parkinggarage.Payments.PaymentStrategy;
import LLD.Projects.parkinggarage.enums.VehicleSize;
import LLD.Projects.parkinglot.ParkingSpots.ParkingSpot;

public abstract class Vehicle {
    // Vehicle
    private String licensePlate;
    private VehicleSize vehicleSize;
    private PaymentStrategy paymentStrategy;

    // Parking Spot
    private ParkingSpace parkingSpace;

    public Vehicle(String licensePlate, VehicleSize vehicleSize) {
        this.licensePlate = licensePlate;
        this.vehicleSize = vehicleSize;
        this.parkingSpace = null;
    }

    public VehicleSize getVehicleSize() {
        return vehicleSize;
    }
    
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }
}
