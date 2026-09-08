package Projects.amazonlocker;

public class _Main {
    public static void main(String[] args) throws InterruptedException {
        AmazonLocker system = new AmazonLocker(2, 2, 2);

        // Basic Functionality (Sizes, Access Codes, Token Info)
        system.depositPackage(PackageSize.SMALL, "a");
        system.depositPackage(PackageSize.SMALL, "b");
        system.depositPackage(PackageSize.SMALL, "a"); // Error: Access Code Exists
        system.depositPackage(PackageSize.SMALL, "c"); // Error: No SMALL spot (max 2)
        system.enterAccessCode("this code doesnt exist"); // Error: invalid code
        system.enterAccessCode("a");

        Token token = system.depositPackage(PackageSize.LARGE, "123");
        System.out.println(token.toString());

        // SMALL package will fill up SMALL, MEDIUM, and LARGE
        system.depositPackage(PackageSize.SMALL, "11");
        system.depositPackage(PackageSize.SMALL, "12");
        system.depositPackage(PackageSize.SMALL, "13");
        system.depositPackage(PackageSize.SMALL, "14");
        system.printCompartmentStatuses();
        system.enterAccessCode("11");
        system.enterAccessCode("12");
        system.enterAccessCode("13");
        system.enterAccessCode("14");

        // Wait 3 seconds to Expire Token
        system.printCompartmentStatuses();
        System.out.println(token.isExpired());
        Thread.sleep(2000);
        System.out.println(token.isExpired());
        Thread.sleep(1500);
        System.out.println(token.isExpired()); // True (expired)
        system.enterAccessCode("123"); // Error: Token expired
        system.openExpiredCompartments();
        system.printCompartmentStatuses();
    }

    /*
        Prompt: Design and implement Amazon Locker system 
            with OTP-based access and 7-day expiry policy

        Requirements
        - Amazon Driver despoits a Package (Small, Medium, Large) into an available Locker
        - User opens Locker with token code, the locker is left empty
        - token code expires 7 days from now
        - Compartment statuses (AVAILABLE, OCCUPIED, OUT_OF_SERVICE)

        Entities
        - Locker
        - Compartment
        - Token
        - PackageSize
        - LockerStatus

        class AmazonLocker
        - List<Compartment> compartments
        - List<Token> tokens
        + findAvailableCompartment(packagesize) -> boolean
        + enterAccessCode()
        + openExpiredCompartments()
        
        class Compartment
        - LockerStatus status
        - PackageSize size
        + isOccupied()

        class Token
        - String id ("TOKEN-UUID")
        - String accessCode
        - Instant expiration
        - Compartment compartment
        + isExpired()

        enum PackageSize
        - SMALL, MEDIUM, LARGE
        + isFit(size)

        enum LockerStatus
        - AVAILABLE, OCCUPIED, OUT_OF_ORDER



    */
}
