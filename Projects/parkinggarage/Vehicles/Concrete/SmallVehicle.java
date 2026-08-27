package Projects.parkinggarage.Vehicles.Concrete;

import javax.management.Notification;

import Projects.parkinggarage.Fee.FeeStrategy;
import Projects.parkinggarage.Payments.PaymentStrategy;
import Projects.parkinggarage.Vehicles.Vehicle;
import Projects.parkinggarage.enums.VehicleSize;
import Projects.parkinggarage.notifications.NotificationObservor;

public class SmallVehicle extends Vehicle {
    public SmallVehicle(String licensePlate, VehicleSize vehicleSize, FeeStrategy feeStrategy, NotificationObservor notificationObservor) {
        super(licensePlate, vehicleSize, feeStrategy, notificationObservor);
    }
}
