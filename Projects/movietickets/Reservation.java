package Projects.movietickets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Reservation {
    private final String confirmationId;
    private final MovieEvent movieEvent;
    private final List<String> seatIds;

    public Reservation(MovieEvent movieEvent, List<String> seatIds) {
        this.confirmationId = "CONF-" + UUID.randomUUID().toString();
        this.movieEvent = movieEvent;
        this.seatIds = new ArrayList<>(seatIds);
    }

    public String getConfirmationId() {
        return confirmationId;
    }

    public MovieEvent getMovieEvent() {
        return movieEvent;
    }

    public List<String> getSeatIds() {
        return new ArrayList<>(seatIds);
    }

    public void cancelReservation() {    
        // Unbook from MovieEvent
        movieEvent.cancelSeats(seatIds);

         // Remove from this class
        seatIds.clear();
    }

    public void printReservation() {
        System.out.println("=== ReservationID: " + confirmationId);
        System.err.println("=== MovieEvent: " + " " + movieEvent.getMovie().getName() + " _ " + movieEvent.getDatetime() + " _ Room #" + movieEvent.getRoom() + " _ MovieEventID: " + movieEvent.getId());
        System.out.println("=== Seats: " + seatIds.toString());
    }
}
