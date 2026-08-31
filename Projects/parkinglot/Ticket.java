package Projects.parkinglot;

import java.util.UUID;

public class Ticket {
    private final String id;
    private final Car car;
    private final long entryTime;
    private boolean used; // Prevent multi-use

    public Ticket(Car car, long entryTime) {
        this.id = "TICKET-" + UUID.randomUUID().toString().substring(0, 10);
        this.car = car;
        this.entryTime = entryTime;
    }

    public String getId() {
        return id;
    }

    public Car getCar() {
        return car;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
