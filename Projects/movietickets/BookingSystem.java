package Projects.movietickets;

import java.util.ArrayList;
import java.util.List;

public class BookingSystem {
    List<Theater> theaters;
    List<MovieEvent> movieEvents;
    List<Movie> movies;
    List<Reservation> reservations;

    public BookingSystem() {
        theaters = new ArrayList<>();
        movieEvents = new ArrayList<>();
        movies = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    // Create
    public Theater createTheater(String name) {
        // Create
        Theater newTheater = new Theater(name);

        // Add to Global Theaters
        theaters.add(newTheater);

        return newTheater;
    }

    public MovieEvent createMovieEvent(Theater theater, Movie movie, String time, int room) {
        // Verify that identical MovieEvent does not Exist
        if (theater.checkMovieEventExists(movie, time, room)) {
            throw new RuntimeException("[Error] Identical MovieEvent already Exists!");
        }

        // Create
        MovieEvent newEvent = new MovieEvent(theater, movie, time, room);

        // Add to Theater
        theater.addMovieEvent(newEvent);

        // Add to Global MovieEvents
        movieEvents.add(newEvent);

        return newEvent;
    }

    public Movie createMovie(String name) {
        // Create
        Movie newMovie = new Movie(name);

        // Add to Global Movies
        movies.add(newMovie);

        return newMovie;
    }

    // Booking
    public Reservation executeBookSeats(MovieEvent movieEvent, List<String> seats) {
        // Book Seats
        boolean success = movieEvent.bookSeats(seats);
        if (!success) {
            System.out.println("[Error] Booking rejected - seat(s) are booked! Attempted: " + seats.toString());
            return null;
        }

        // Create
        Reservation myReservation = new Reservation(movieEvent, seats);

        // Add to Global Reservations
        reservations.add(myReservation);

        return myReservation;
    }

    // Search
    public List<MovieEvent> search(String title) {
        List<MovieEvent> result = new ArrayList<>();
        for (MovieEvent event : movieEvents) {
            if (event.getMovie().getName() == title) {
                // Found MovieEvent
                result.add(event);
            }
        }
        printListMovieEvents(title, result);
        return result;
    }

    public void printListMovieEvents(String searchQuery, List<MovieEvent> searchList) {
        System.out.println("||| Search Results for: " + searchQuery);
        for (MovieEvent event : searchList) {
            System.out.println("||| " + event.getMovie().getName() + " " + event.getDatetime() + " " + event.getTheater().getName() + " - Room #" + event.getRoom());
        }
    }
}
