package Projects.amazonlocker.locker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Projects.amazonlocker.compartment.Compartment;
import Projects.amazonlocker.enums.PackageSize;
import Projects.amazonlocker.token.Token;

public class Locker {
    private List<Compartment> compartments;
    private Map<String, Compartment> map;

    public Locker(int small, int medium, int large) {
        compartments = new ArrayList<>();
        map = new HashMap<>();

        // Create Compartments
        int compartmentNumber = 1;
        for (int i = 0; i < small; i++) {
            compartments.add(new Compartment(compartmentNumber, PackageSize.SMALL));
            compartmentNumber = compartmentNumber + 1;
        }
        for (int i = 0; i < medium; i++) {
            compartments.add(new Compartment(compartmentNumber, PackageSize.MEDIUM));
            compartmentNumber = compartmentNumber + 1;
        }
        for (int i = 0; i < large; i++) {
            compartments.add(new Compartment(compartmentNumber, PackageSize.LARGE));
            compartmentNumber = compartmentNumber + 1;
        }
    }

    public void despoitPackage(String code, PackageSize packageSize) {
        // Check Valid Code
        if (map.containsKey(code)) {
            System.out.println("X - Access Code " + code + " already exists");
            return;
        }

        // Find Open Compartment
        for (Compartment compartment : compartments) {
            if (!compartment.getOccupied()) {
                if (compartment.canPlacePackage(packageSize)) {
                    // Set Compartment
                    compartment.placePackage();

                    // Set Token
                    map.put(code, compartment);

                    return;
                }
            }
        }
        
        // No Available Compartment
        System.out.println("X - No open package compartment! Size " + packageSize);
    }

    public void openLockerWithCode(String code) {
        // Find Matching Code
        if (!map.containsKey(code)) {
            System.out.println("X - Invalid code " + code);
            return;
        }

        // Open Compartment & Empty
        Compartment myCompartment = map.get(code);
        myCompartment.openLockerForUser();

        // Remove Code
        map.remove(code);

        System.out.println("Valid Code " + code);
    }

    public void openExpiredCompartments() {
        // Empty all expired compartments
        for (Compartment compartment : compartments) {
            if (compartment.getOccupied()) {
                if (compartment.getCompartmentIsExpired()) {
                    compartment.openLockerForUser();
                }
            }
        }
    }

    public void printLocker() {
        for (Compartment compartment : compartments) {
            System.out.println(compartment.getPrintStatus());
        }
    }
}
