package LLD.Projects.movietickets;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MovieEvent {
    // Object
    private final String id;
    // Theater
    private final Theater theater;
    private final int room;
    private final Map<String, Boolean> seats;
    // Movie
    private final Movie movie;
    private final String datetime;
    // Lock
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public MovieEvent(Theater theater, Movie movie, String datetime, int room) {
        // Create MovieEvent
        this.id = UUID.randomUUID().toString();
        this.theater = theater;
        this.room = room;
        this.movie = movie;
        this.datetime = datetime;

        // Create Seats
        seats = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            seats.put("A" + String.valueOf(i), false);
        }
    }

    // Seats
    public boolean bookSeats(List<String> mySeats) {
        lock.writeLock().lock();

        try {
            // Check Seats are not Booked
            for (String seatId : mySeats) {
                if (!seats.containsKey(seatId)) {
                    throw new IllegalArgumentException("[Error] Seat " + seatId + " is not a valid seat!");
                }
                if (seats.get(seatId) == true) {
                    return false;
                }
            }

            // Book Seats
            for (String seatId : mySeats) {
                seats.put(seatId, true);
            }

            return true;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void cancelSeats(List<String> mySeats) {
        lock.writeLock().lock();

        try {
            // Unbook Seats
            for (String seatId : mySeats) {
                seats.put(seatId, false);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    // public boolean isSeatAvailable(String seatId) {
    //     lock.readLock().lock();

    //     try {
    //         return seats.get(seatId);
    //     } finally {
    //         lock.readLock().unlock();
    //     }
    // }

    public void printMovieEventSeats() {
        lock.readLock().lock();
        
        try {
            System.out.println("-> MovieEvent: " + movie.getName() + " " + datetime);
            for (Map.Entry<String, Boolean> seat : seats.entrySet()) {
                System.out.println("-> Seat " + seat.getKey() + ": " + seat.getValue());
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public Theater getTheater() {
        return theater;
    }

    public String getDatetime() {
        return datetime;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getRoom() {
        return room;
    }

    
}
