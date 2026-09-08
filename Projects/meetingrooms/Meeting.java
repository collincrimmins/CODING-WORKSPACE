package Projects.meetingrooms;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class Meeting {
    private final String id;
    private final Room room;
    private final Interval interval;
    private final List<User> users;

    public Meeting(Room room, Interval interval, List<User> users) {
        this.id = "MEETING-" + UUID.randomUUID().toString().substring(0, 10);
        this.room = room;
        this.interval = interval;
        this.users = List.copyOf(users);
    }

    public boolean isTimeOverlapping(Interval otherInterval) {
        return interval.isIntervalOverlapping(otherInterval);
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        .withZone(ZoneId.systemDefault());
        String start = formatter.format(interval.getStart());
        String end = formatter.format(interval.getEnd());

        return "[" + id + "] " + users.size() + " users - " + room.getId() + " - " + start + " -> " + end;
    }
}
