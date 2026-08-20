package LLD.Projects.amazonlocker;

import LLD.Projects.amazonlocker.enums.PackageSize;
import LLD.Projects.amazonlocker.locker.Locker;
import LLD.Projects.amazonlocker.token.Token;

public class Main {
    /*
        Requirements
        - Amazon Driver despoits a Package (Small, Medium, Large) into an available Locker
        - User opens Locker with token code, the locker is left empty

        Entities
        - Locker
        - Token
    */
    public static void main(String[] args) {
        Locker locker = new Locker(2, 2, 2);

        // Place Items
        locker.despoitPackage("123456", PackageSize.SMALL);
        locker.despoitPackage("123456", PackageSize.SMALL); // Error on Duplicate Code
        locker.despoitPackage("abc", PackageSize.SMALL);
        locker.despoitPackage("abc123", PackageSize.SMALL); // Error on > 2 Small Packages
        locker.despoitPackage("1234567", PackageSize.MEDIUM);
        locker.despoitPackage("12345678", PackageSize.MEDIUM);
        locker.despoitPackage("large1", PackageSize.LARGE);
        locker.despoitPackage("large2", PackageSize.LARGE);
        locker.despoitPackage("large", PackageSize.LARGE); // Error on > 2 Large Packages

        locker.printLocker();

        // Check Codes
        locker.openLockerWithCode("abcdef"); // Invalid Code
        locker.openLockerWithCode("abc");
        locker.openLockerWithCode("123456");
        locker.openLockerWithCode("1234567");
        locker.openLockerWithCode("12345678");
        locker.openLockerWithCode("large1");
        locker.openLockerWithCode("large2");

        locker.printLocker();






        // Staff opens expired compartments
        locker.openExpiredCompartments();
    }
}
