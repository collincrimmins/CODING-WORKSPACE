package Projects.amazonlocker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AmazonLocker {
    private final List<Compartment> compartments;
    private final Map<String, Token> tokens;

    public AmazonLocker(int numSmall, int numMedium, int numLarge) {
        this.compartments = new ArrayList<>();
        this.tokens = new HashMap<>();

        // Create Compartments
        int compartmentNumber = 1;
        for (int i = 0; i < numSmall; i++) {
            compartments.add(new Compartment(compartmentNumber, PackageSize.SMALL));
            compartmentNumber = compartmentNumber + 1;
        }
        for (int i = 0; i < numMedium; i++) {
            compartments.add(new Compartment(compartmentNumber, PackageSize.MEDIUM));
            compartmentNumber = compartmentNumber + 1;
        }
        for (int i = 0; i < numLarge; i++) {
            compartments.add(new Compartment(compartmentNumber, PackageSize.LARGE));
            compartmentNumber = compartmentNumber + 1;
        }
    }

    public synchronized Token depositPackage(PackageSize size, String accessCode) {
        // Check AccessCode Exists
        if (tokens.containsKey(accessCode)) {
            System.out.println("Error: Access Code already exists " + accessCode);
            return null;
        }

        for (Compartment unit : compartments) {
            if (unit.getStatus() == CompartmentStatus.AVAILABLE) {
                if (size.canFitIntoCompartment(unit.getSize())) {
                    // Enter Here
                    unit.setOccupied();

                    // Create Token
                    Token token = new Token(accessCode, unit);

                    // Add to Tokens Map
                    tokens.put(accessCode, token);

                    return token;
                }
            }
        }

        // No Compartment Found
        System.out.println("Error: No compartment available for size " + size);
        return null;
    }

    public synchronized boolean enterAccessCode(String accessCode) {
        if (!tokens.containsKey(accessCode)) {
            System.err.println("No acccess code: " + accessCode);
            return false;
        }

        Token token = tokens.get(accessCode);
        Compartment compartment = token.getCompartment();

        // Check Expired
        if (token.isExpired()) {
            System.out.println("Error: Token expired! " + accessCode);
            return false;
        }

        // Open Compartment
        compartment.openAndSetUnoccupied();

        // Remove AccessCode
        tokens.remove(token.getAccessCode());

        return true;
    }

    public synchronized void openExpiredCompartments() {
        List<String> expiredAccessCodes = new ArrayList<>();

        for (Map.Entry<String, Token> entry : tokens.entrySet()) {
            if (entry.getValue().isExpired()) {
                Token token = entry.getValue();

                // Compartment
                System.out.println("Opened Expired Token: " + entry.getValue().getId());
                token.getCompartment().openAndSetUnoccupied();

                // Add to Remove List
                expiredAccessCodes.add(token.getAccessCode());
            }
        }

        for (String code : expiredAccessCodes) {
            tokens.remove(code);
        }
    }

    public synchronized void printCompartmentStatuses() {
        for (Compartment compartment : compartments) {
            System.out.println(compartment.getPrintStatus());
        }
        System.out.println(tokens.toString());

        int open = 0;
        int closed = 0;
        for (Compartment unit : compartments) {
            if (unit.getStatus() == CompartmentStatus.AVAILABLE) {
                open = open + 1;
            } else {
                closed = closed + 1;
            }
        }
        System.out.println("[STATUS] Open: x" + open + " - Closed: x" + closed);
    }
}