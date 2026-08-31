package Projects.parkinglot;

public class Spot {
    private Car car;
    private boolean occupied;
    private final CarType carType;

    public Spot(CarType carType) {
        this.car = null;
        this.occupied = false;
        this.carType = carType;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public CarType getCarType() {
        return carType;
    }
}
