package Projects.meetingrooms;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetingRoomsService {
    private final Map<String, Room> rooms;
    private final List<Meeting> meetings;

    public MeetingRoomsService() {
        this.rooms = new HashMap<>();
        this.meetings = new ArrayList<>();
    }

    public synchronized void addRoom(String id, int capacity) {
        if (rooms.containsKey(id)) {
            throw new IllegalArgumentException("room id already exists");
        }

        rooms.put(id, new Room(id, capacity));
    }

    public synchronized void addMeeting(List<User> myUsers, String roomId, Instant start, Instant end) {
        if (myUsers == null || myUsers.size() == 0) {
            throw new IllegalArgumentException("no users in your meeting");
        }

        if (roomId == null) {
            throw new IllegalArgumentException("no  room id");
        }

        if (start == null || end == null) {
            throw new IllegalArgumentException("invalid start and/or end");
        }

        if (start == end || end.isBefore(start)) {
            throw new IllegalArgumentException("your end time cannot be before start");
        }

        if (!rooms.containsKey(roomId)) {
            throw new IllegalArgumentException("invalid room");
        }

        if (myUsers.size() > rooms.get(roomId).getMaxCapacity()) {
            throw new IllegalArgumentException("more users than max capacity");
        }

        // Check Overlapping Meeting Times & Rooms
        Room room = rooms.get(roomId);
        Interval interval = new Interval(start, end);
        for (Meeting meeting : meetings) {
            if (meeting.getRoom() == room && meeting.isTimeOverlapping(interval)) {
                throw new RuntimeException("New Meeting Request is overlapping with existing meeting");
            }
        }

        // Create Meeting
        Meeting meeting = new Meeting(room, interval, myUsers);

        // Add to Meetings
        meetings.add(meeting);
    }

    public synchronized void printMeetings() {
        for (Meeting meeting : meetings) {
            System.out.println(meeting.toString());
        }
    }
}
