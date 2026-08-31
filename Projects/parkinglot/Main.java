package Projects.parkinglot;

public class Main {
    /*
        Prompt: A parking lot system manages vehicle parking across multiple spots. 
        When a vehicle enters, the system assigns an available spot matching the 
        vehicle type and issues a ticket. When the vehicle exits, the system 
        calculates the parking fee based on time spent and frees up the spot for the next customer.

        Requirements:
        - Cars can Enter & Exit the parking lot
        - Cars are assigned an available spot (or reject if no spots avialable)
        - Cars receive a Ticket w/ unique id
        - Multiple Car types (small, medium, large)
        - On Exit, the car must pay a Fee based on time spent (round up to nearest hour)
        - Concurrency & Thread Handling

        Entities
        - ParkingLot (system)
        - Car
        - Spot
        - Ticket
    
    */


    public static void main(String[] args) {
        ParkingLot system = new ParkingLot(5);

        // Test Enter & Exit
        Car car1 = CarFactory.create("ABC123", CarType.SMALL);
        system.enter(car1);
        system.exit(car1);

        // Test Occupied Spaces (Max 5)
        for (int i = 1; i <= 6; i++) {
            Car car = CarFactory.create(String.valueOf(i), CarType.SMALL);
            boolean result = system.enter(car);
            if (result) {
                System.out.println("=> Parked Car " + car.getLicensePlate());
            } else {
                System.out.println("X - No Spots for car " + car.getLicensePlate());
            }
        }
    }
}
