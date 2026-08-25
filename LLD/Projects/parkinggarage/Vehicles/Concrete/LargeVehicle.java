package LLD.Projects.parkinggarage.Vehicles.Concrete;

import javax.management.Notification;

import LLD.Projects.parkinggarage.Fee.FeeStrategy;
import LLD.Projects.parkinggarage.Payments.PaymentStrategy;
import LLD.Projects.parkinggarage.Vehicles.Vehicle;
import LLD.Projects.parkinggarage.enums.VehicleSize;
import LLD.Projects.parkinggarage.notifications.NotificationObservor;

public class LargeVehicle extends Vehicle {
    public LargeVehicle(String licensePlate, VehicleSize vehicleSize, FeeStrategy feeStrategy, NotificationObservor notificationObservor) {
        super(licensePlate, vehicleSize, feeStrategy, notificationObservor);
    }
}
