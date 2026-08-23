package LLD.Projects.parkinggarage.Vehicles;

import javax.management.Notification;

import LLD.Projects.parkinggarage.Fee.FeeStrategy;
import LLD.Projects.parkinggarage.ParkingSpace.ParkingSpace;
import LLD.Projects.parkinggarage.Payments.PaymentStrategy;
import LLD.Projects.parkinggarage.enums.VehicleSize;
import LLD.Projects.parkinggarage.notifications.NotificationObservor;
import LLD.Projects.parkinggarage.notifications.NotificationService;
import LLD.Projects.parkinggarage.notifications.Notifications;

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
