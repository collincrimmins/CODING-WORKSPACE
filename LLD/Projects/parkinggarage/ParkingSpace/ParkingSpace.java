package LLD.Projects.parkinggarage.ParkingSpace;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.management.RuntimeErrorException;

import LLD.Projects.parkinggarage.Vehicles.Vehicle;
import LLD.Projects.parkinggarage.enums.VehicleSize;

public class ParkingSpace {
    // Attributes
    private VehicleSize spaceSize;
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

    public boolean canFitCar(Vehicle vehicle) {
        if (spaceSize == vehicle.getVehicleSize()) {
            return true;
        }
        return false;
    }

    public boolean isOccupied() {
        rwLock.readLock().lock();
        try {
            return occupied;
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public VehicleSize getSpaceSize() {
        return spaceSize;
    }

    // Set Vehicle to Space
    // writeLock = only 1 thread can check-and-write to this space
    public void setVehicle(Vehicle vehicle) {
        rwLock.writeLock().lock();

        try {
            if (occupied) {
                throw new RuntimeException("[Error] occupied parking space");
            }

            System.err.println("Succesfully parked " + vehicle.getLicensePlate() + " in space size " + vehicle.getVehicleSize());
            occupied = true;
            licensePlate = vehicle.getLicensePlate();
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void exitSpace() {
        rwLock.writeLock().lock();

        try {
            occupied = false;
            licensePlate = "";
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    // Exit Space
    public void setEmptySpace() {
        rwLock.writeLock().lock();

        try {
            occupied = false;
            licensePlate = "";
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}
