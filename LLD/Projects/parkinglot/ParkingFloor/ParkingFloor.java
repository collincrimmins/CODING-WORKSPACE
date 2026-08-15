package LLD.Projects.parkinglot.ParkingFloor;

import java.util.ArrayList;
import java.util.List;

import LLD.Projects.parkinglot.ParkingSpots.ParkingSpot;

public class ParkingFloor {
    private List<ParkingSpot> spots;
    private int floorNumber;

    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
        spots = new ArrayList<>();
    }

    public void addParkingSpot(ParkingSpot spot) {
        this.spots.add(spot);
    }

    public ParkingSpot findAvailableSpot(String vehicleType) {
        for (ParkingSpot spot : spots) {
            if (!spot.isOccupied() && spot.getSpotType().equalsIgnoreCase(vehicleType)) {
                return spot;
            }
        }
        return null;
    }

    public List<ParkingSpot> getParkingSpots() {
        return spots;
    }
    public int getFloorNumber() {
        return floorNumber;
    }
}
