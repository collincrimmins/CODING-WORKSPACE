package LLD.Projects.amazonlocker.compartment;

import java.time.LocalDateTime;

import LLD.Projects.amazonlocker.enums.PackageSize;

public class Compartment {
    private PackageSize compartmentSize;
    private boolean isOccupied;
    private LocalDateTime expirationDate;
    private int number;

    public Compartment(int number, PackageSize compartmentSize) {
        // Initialize
        this.compartmentSize = compartmentSize;
        this.isOccupied = false;
        this.number = number;
    }

    public void placePackage() {
        // Set Compartment
        this.isOccupied = true;
        this.expirationDate = LocalDateTime.now().plusDays(7);
    }

    public void openLockerForUser() {
        // Reset Compartment
        this.isOccupied = false;
        this.expirationDate = null;
    }

    public boolean getOccupied() {
        return isOccupied;
    }

    public boolean canPlacePackage(PackageSize packageSize) {
        return packageSize == compartmentSize;
    }

    public boolean getCompartmentIsExpired() {
        return LocalDateTime.now().isAfter(expirationDate);
    }

    public String getPrintStatus() {
        if (!isOccupied) {
            return "Compartment #" + number + ": {}";
        }
        return "Compartment #" + number + ": occupied " + compartmentSize;
    }
}
