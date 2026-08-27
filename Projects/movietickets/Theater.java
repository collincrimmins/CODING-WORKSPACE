package Projects.movietickets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Theater {
    private final String id;
    private final String name;
    private final List<MovieEvent> events;

    public Theater(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.events = new ArrayList<>();
    }
    
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addMovieEvent(MovieEvent movieEvent) {
        events.add(movieEvent);
    }

    public List<MovieEvent> getMovieEvents() {
        return events;
    }

    public boolean checkMovieEventExists(Movie movie, String datetime, int room) {
        for (MovieEvent event : events) {
            if (event.getMovie() == movie && event.getDatetime().equals(datetime) && event.getRoom() == room) {
                return true;
            }
        }
        return false;
    }

}
