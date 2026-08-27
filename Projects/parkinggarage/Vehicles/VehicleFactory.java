package Projects.parkinggarage.Vehicles;

import Projects.parkinggarage.Fee.Concrete.LargeFee;
import Projects.parkinggarage.Fee.Concrete.MediumFee;
import Projects.parkinggarage.Fee.Concrete.SmallFee;
import Projects.parkinggarage.Vehicles.Concrete.LargeVehicle;
import Projects.parkinggarage.Vehicles.Concrete.MediumVehicle;
import Projects.parkinggarage.Vehicles.Concrete.SmallVehicle;
import Projects.parkinggarage.enums.VehicleSize;
import Projects.parkinggarage.notifications.Concrete.EmailNotification;
import Projects.parkinggarage.notifications.Concrete.TextNotification;

public class VehicleFactory {
    public static Vehicle createVehicle(String licensePlate, VehicleSize vehicleSize) {
        if (licensePlate.equals("")) {
            throw new RuntimeException("Invalid license plate input");
        }

        if (vehicleSize == VehicleSize.SMALL) {
            // Bikes
            return new SmallVehicle(licensePlate, vehicleSize, new SmallFee(), new EmailNotification());
        } else if (vehicleSize == VehicleSize.MEDIUM) {
            // Compact
            return new MediumVehicle(licensePlate, vehicleSize, new MediumFee(), new EmailNotification());
        } else if (vehicleSize == VehicleSize.LARGE) {
            // Trucks
            return new LargeVehicle(licensePlate, vehicleSize, new LargeFee(), new TextNotification());
        }

        throw new RuntimeException("Invalid vehicle input");
    }
}
