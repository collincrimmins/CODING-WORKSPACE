package Projects.parkinggarage.Vehicles;

import javax.management.Notification;

import Projects.parkinggarage.Fee.FeeStrategy;
import Projects.parkinggarage.ParkingSpace.ParkingSpace;
import Projects.parkinggarage.Payments.PaymentStrategy;
import Projects.parkinggarage.enums.VehicleSize;
import Projects.parkinggarage.notifications.NotificationObservor;
import Projects.parkinggarage.notifications.NotificationService;
import Projects.parkinggarage.notifications.Notifications;

public abstract class Vehicle {
    // Vehicle
    private String licensePlate;
    private VehicleSize vehicleSize;
    private FeeStrategy feeStrategy;
    private NotificationObservor notificationObservor;
    
    // Parking Spot
    private ParkingSpace parkingSpace;

    public Vehicle(String licensePlate, VehicleSize vehicleSize, FeeStrategy feeStrategy, NotificationObservor notificationObservor) {
        this.licensePlate = licensePlate;
        this.vehicleSize = vehicleSize;
        this.parkingSpace = null;
        this.feeStrategy = feeStrategy;
        this.notificationObservor = notificationObservor;
    }

    public VehicleSize getVehicleSize() {
        return vehicleSize;
    }
    
    public String getLicensePlate() {
        return licensePlate;
    }

    public FeeStrategy getFeeStrategy() {
        return feeStrategy;
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    public NotificationObservor getNotificationObservor() {
        return notificationObservor;
    }
}
