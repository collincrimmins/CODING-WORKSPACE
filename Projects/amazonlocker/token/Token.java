package Projects.amazonlocker.token;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import Projects.amazonlocker.compartment.Compartment;
import Projects.amazonlocker.locker.Locker;

public class Token {
    private String code;
    private Compartment compartment;

    public Token(String code, Compartment compartment) {
        this.code = code;
        this.compartment = compartment;
    }

    public String getCode() {
        return code;
    }
}
