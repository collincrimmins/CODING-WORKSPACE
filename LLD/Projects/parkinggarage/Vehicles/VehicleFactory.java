package LLD.Projects.parkinggarage.Vehicles;

import LLD.Projects.parkinggarage.Vehicles.Concrete.LargeVehicle;
import LLD.Projects.parkinggarage.Vehicles.Concrete.MediumVehicle;
import LLD.Projects.parkinggarage.Vehicles.Concrete.SmallVehicle;
import LLD.Projects.parkinggarage.enums.VehicleSize;

public class VehicleFactory {
    public static Vehicle createVehicle(String licensePlate, VehicleSize vehicleSize) {
        if (licensePlate.equals("")) {
            throw new RuntimeException("Invalid license plate input");
        }

        if (vehicleSize == VehicleSize.SMALL) {
            // Bikes
            return new SmallVehicle(licensePlate, vehicleSize);
        } else if (vehicleSize == VehicleSize.MEDIUM) {
            // Compact
            return new MediumVehicle(licensePlate, vehicleSize);
        } else if (vehicleSize == VehicleSize.LARGE) {
            // Trucks
            return new LargeVehicle(licensePlate, vehicleSize);
        }

        throw new RuntimeException("Invalid vehicle input");
    }
}
