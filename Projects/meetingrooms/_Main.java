package Projects.meetingrooms;

import java.time.Instant;
import java.util.List;

public class _Main {
    public static void main(String[] args) {
        MeetingRoomsService system = new MeetingRoomsService();
        system.addRoom("room1", 10);
        system.addRoom("room2", 10);

        // Invalid (room id already exists)
        //system.addRoom("room2", 10);

        Instant start = null;
        Instant end = null;

        User user1 = new User("bob");
        User user2 = new User("sally");
        User user3 = new User("joe");
        List<User> myUsers = List.of(user1, user2, user3);

        // Valid
        start = Instant.now();
        end = Instant.now().plusSeconds(3);
        system.addMeeting(myUsers, "room1", start, end);

        // Invalid Time
        start = Instant.now().plusSeconds(1);
        end = Instant.now().plusSeconds(2);
        system.addMeeting(myUsers, "room1", start, end);

        // Valid (in room #2)
        start = Instant.now().plusSeconds(1);
        end = Instant.now().plusSeconds(2);
        system.addMeeting(myUsers, "room2", start, end);

        // Valid (non overlapping time)
        start = Instant.now().plusSeconds(4);
        end = Instant.now().plusSeconds(5);
        system.addMeeting(myUsers, "room1", start, end);

        // Room Max Capacity (>10)
        // List<User> myUsersGiantList = List.of(user1, user1, user1, user1 ,user1 , user1 ,user1,user1,user1,user1,user1);
        // system.addMeeting(myUsersGiantList, "room1", start, end);

        // Print
        system.printMeetings();

    }

    /*
        Prompt: Design a meeting room booking system that allows users to create meeting rooms 
        with defined capacities, book available rooms for specific time intervals, 
        and prevent overlapping reservations.
        https://medium.com/@sumashreyatv/building-a-low-level-design-for-meeting-room-scheduler-2f461639d0db 

        Requirements:
        - Create Meetings (start, end, users)
        - Create Rooms
        - Prevent rooms from being booked if start/end is overlapping with an existing meeting
        - Prevent more users than room capacity
        - Concurrency (no double booking of meetings & rooms & times w/ multiple threads)

        Class Design

            MeetingRoomsService
            - List<Room> rooms
            - List<Meeting> meetings
            + createMeeting(users, room, start, end) // check if meeting overlaps with existing meetings & rooms

            Room
            - String id

            Meeting
            - String id
            - Interval interval
            - List<User> users
            + isTimeOverlapping(interval)

            Interval
            - Instant start
            - Instant end
            + isIntervalOverlapping(interval)

            User
            - String id
    */
}
