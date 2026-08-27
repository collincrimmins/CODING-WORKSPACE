package Projects.movietickets;

import java.util.List;

public class Main {
    /*

        Prompt: Design a movie ticket booking system similar to BookMyShow that allows users to browse movies, 
        select theaters and showtimes, book tickets, and manage reservations
        
        Requirements
        - Select Reservation = Movie Title + Theater + Room + Seat + Time
        - Manage Reservation = Cancel Ticket
        - Book multiple seats at checkout
        - Search (just a exact string match)
        - Concurrency (only 1 booking per request seat can succeed)
        
        Entities
        - BookingSystem
        - Movie (Name)
        - Theater (Rooms)
        - Room (Movie, Time, Seats)
        - Reservation (ConfirmationID + ReservedSeats)

    */


    public static void main(String[] args) {
        BookingSystem system = new BookingSystem();

        // Create Theaters
        Theater theater1 = system.createTheater("Theater #1");

        // Movie - The Oddysey
        Movie movie1 = system.createMovie("The Oddysey");
        MovieEvent movieEvent1 = system.createMovieEvent(theater1, movie1, "8/30/2026 8:00 PM", 1);
        MovieEvent movieEvent1a = system.createMovieEvent(theater1, movie1, "8/30/2026 9:00 PM", 2);
        Reservation reservation1 = system.executeBookSeats(movieEvent1, List.of("A1"));
        Reservation reservation2 = system.executeBookSeats(movieEvent1, List.of("A2", "A3"));
        reservation1.printReservation();
        movieEvent1.printMovieEventSeats(); // Booked A1, A2, A3
        reservation2.cancelReservation(); // Delete A2 and A3
        movieEvent1.printMovieEventSeats(); // Booked A1

        // Movie - The Big Short
        Movie movie2 = system.createMovie("The Big Short");
        MovieEvent movieEvent2 = system.createMovieEvent(theater1, movie2, "8/30/2026 5:00 PM", 1);
        Reservation reservation3 = system.executeBookSeats(movieEvent2, List.of("A1", "A2", "A3", "A4", "A5"));
        movieEvent2.printMovieEventSeats();

        // Search
        List<MovieEvent> searchList = system.search("The Oddysey");
        List<MovieEvent> searchList2 = system.search("Nonexistant Movie");
        List<MovieEvent> searchList3 = system.search("The Big Short");

        // Concurrent Booking
        Movie movie5 = system.createMovie("Example_Movie");
        MovieEvent movieEventConcurrency = system.createMovieEvent(theater1, movie5, "8/30/2026 5:00 PM", 1);

        Thread thread1 = new Thread(() -> {
            Reservation reservationThread1 = system.executeBookSeats(movieEventConcurrency, List.of("A1"));
            //System.out.println(reservationThread1);
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            Reservation reservationThread2 = system.executeBookSeats(movieEventConcurrency, List.of("A1"));
            //System.out.println(reservationThread2);
        });
        thread2.start();

        System.out.println("-> Concurrency Test for A1");
        movieEventConcurrency.printMovieEventSeats();





        // Error: Existing Movie Events
        // MovieEvent event123 = system.createMovieEvent(theater1, movie1, "8/30/2026 1:00 PM", 1);
        // MovieEvent event1234 = system.createMovieEvent(theater1, movie1, "8/30/2026 1:00 PM", 1);

        // Error: Booked Seats
        // system.executeBookSeats(movieEvent1, List.of("A5"));
        // system.executeBookSeats(movieEvent1, List.of("A5"));
    }
}
