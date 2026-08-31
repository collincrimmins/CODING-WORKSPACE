package Projects.parkinglot;

public abstract class Car {
    private final String licensePlate;
    private final CarType carType;
    private Spot spot;
    private Ticket ticket;

    public Car(String licensePlate, CarType carType) {
        this.licensePlate = licensePlate;
        this.carType = carType;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public CarType getCarType() {
        return carType;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }
    
}
