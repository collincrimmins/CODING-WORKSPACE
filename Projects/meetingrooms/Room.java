package Projects.meetingrooms;

public class Room {
    private final String id;
    private final int maxCapacity;

    public Room(String id, int maxCapacity) {
        this.id = id;
        this.maxCapacity = maxCapacity;
    }

    public String getId() {
        return id;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}
