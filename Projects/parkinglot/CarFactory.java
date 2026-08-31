package Projects.parkinglot;

public class CarFactory {
    public static Car create(String licensePlate, CarType carType) {
        if (licensePlate == null) {
            throw new IllegalArgumentException("license plate");
        }

        if (carType == CarType.SMALL) {
            return new CarSmall(licensePlate, carType);
        } else if (carType == CarType.MEDIUM) {
            return new CarMedium(licensePlate, carType);
        } else if (carType == CarType.LARGE) {
            return new CarLarge(licensePlate, carType);
        }

        throw new IllegalArgumentException("invalid car type");
    }
}
