package LLD.Projects.movietickets;

import java.util.UUID;

public class Movie {
    private final String id;
    private final String name;

    public Movie(String title) {
        this.id = UUID.randomUUID().toString();
        this.name = title;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
