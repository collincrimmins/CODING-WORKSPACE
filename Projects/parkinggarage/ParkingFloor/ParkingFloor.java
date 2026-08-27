package Projects.parkinggarage.ParkingFloor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import Projects.parkinggarage.ParkingSpace.ParkingSpace;
import Projects.parkinggarage.enums.VehicleSize;

public class ParkingFloor {
    List<ParkingSpace> parkingSpaces;
    int level;

    public ParkingFloor(int level, int numSmall, int numMedium, int numLarge) {
        parkingSpaces = new ArrayList<>();
        this.level = level;

        for (int i = 0; i < numSmall; i++) {
            ParkingSpace newSpace = new ParkingSpace(VehicleSize.SMALL);
            parkingSpaces.add(newSpace);
        }

        for (int i = 0; i < numMedium; i++) {
            ParkingSpace newSpace = new ParkingSpace(VehicleSize.MEDIUM);
            parkingSpaces.add(newSpace);
        }

        for (int i = 0; i < numLarge; i++) {
            ParkingSpace newSpace = new ParkingSpace(VehicleSize.LARGE);
            parkingSpaces.add(newSpace);
        }
    }

    public List<ParkingSpace> getParkingSpaces() {
        return parkingSpaces;
    }

    public int getLevel() {
        return level;
    }
}
