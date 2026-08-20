package LLD.Projects.amazonlocker.token;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import LLD.Projects.amazonlocker.compartment.Compartment;
import LLD.Projects.amazonlocker.locker.Locker;

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
