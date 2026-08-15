package LLD.Projects.parkinglot.ParkingSpots;

import LLD.Projects.parkinglot.VehicleFactoryPattern.Vehicle;

public abstract class ParkingSpot {
    private int spotNumber;
    private boolean isOccupied;
    private Vehicle vehicle;
    private String spotType;

    public ParkingSpot(int spotNumber, String spotType) {
        this.spotNumber = spotNumber;
        this.isOccupied = false;
        this.spotType = spotType;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    // Implement in ConcreteParkingSpots class
    public abstract boolean canParkVehicle(Vehicle vehicle);

    public void parkVehicle(Vehicle vehicle) {
        // Occupied
        if (isOccupied) {
            throw new IllegalStateException("Spot occupied!");
        }

        // Vehicle Type & Spot Type
        if (!canParkVehicle(vehicle)) {
            throw new IllegalArgumentException(
                "spot is not suitable for " + vehicle.getVehicleType()
            );
        }

        this.vehicle = vehicle;
        this.isOccupied = true;
    }

    public void vacate() {
        if (!isOccupied) {
            throw new IllegalStateException("spot already vacant");
        }

        this.vehicle = null;
        this.isOccupied = false;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getSpotType() {
        return spotType;
    }
}
