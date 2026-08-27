package Projects.parkinggarage.ParkingSpace;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.management.RuntimeErrorException;

import Projects.parkinggarage.Vehicles.Vehicle;
import Projects.parkinggarage.enums.VehicleSize;

public class ParkingSpace {
    // Attributes
    private final VehicleSize spaceSize;
    private boolean occupied;

    // Vehicle
    private String licensePlate;

    // Concurrency
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    // Constructor
    public ParkingSpace(VehicleSize size) {
        spaceSize = size;
        occupied = false;
        licensePlate = "";
    }
    
    // Concurrency (occupied, licensePlate)
    public void setVehicle(Vehicle vehicle) {
        rwLock.writeLock().lock();

        try {
            if (occupied) {
                throw new RuntimeException("[Error] occupied parking space");
            }

            //System.err.println("Succesfully parked " + vehicle.getLicensePlate() + " in space size " + vehicle.getVehicleSize());
            occupied = true;
            licensePlate = vehicle.getLicensePlate();
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void exitSpace() {
        rwLock.writeLock().lock();

        try {
            if (!occupied) {
                throw new RuntimeException("[Error] Space is already empty!");
            }

            occupied = false;
            licensePlate = "";
        } finally {
           rwLock.writeLock().unlock();
        }
    }

    public boolean isOccupied() {
        rwLock.readLock().lock();
        try {
            return occupied;
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public String getLicensePlate() {
        rwLock.readLock().lock();
        try {
            return licensePlate;
        } finally {
            rwLock.readLock().unlock();
        }
    }

    // Getters
    public boolean canFitCar(Vehicle vehicle) {
        if (spaceSize == vehicle.getVehicleSize()) {
            return true;
        }
        return false;
    }

    public VehicleSize getSpaceSize() {
        return spaceSize;
    }
}
