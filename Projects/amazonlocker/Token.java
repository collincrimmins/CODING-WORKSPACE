package Projects.amazonlocker;

import java.time.Instant;
import java.util.UUID;

public class Token {
    private final String id;
    private final String accessCode;
    private final Instant expirationTime;
    private final Compartment compartment;

    public Token(String accessCode, Compartment compartment) {
        this.id = "TOKEN-" + UUID.randomUUID().toString().substring(0, 10);
        this.accessCode = accessCode;
        this.compartment = compartment;

        int exp7DaysSecond = 60 * 60 * 24 * 7;
        int expTesting = 3;
        this.expirationTime = Instant.now().plusSeconds(expTesting);
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expirationTime);
    }

    public String getId() {
        return id;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public Instant getExpirationTime() {
        return expirationTime;
    }

    public Compartment getCompartment() {
        return compartment;
    }

    @Override
    public String toString() {
        return "Token [id=" + id + ", accessCode=" + accessCode + ", expirationTime=" + expirationTime
                + ", compartment=" + compartment.getId() + "]";
    }
    


}