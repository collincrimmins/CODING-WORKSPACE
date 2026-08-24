package LLD.Projects.elevator;

import java.util.HashMap;
import java.util.Map;

import LLD.Projects.elevator.Controller.Controller;
import LLD.Projects.elevator.Request.Request;
import LLD.Projects.elevator.Request.RequestType;

public class Main {
    /*
        Requirements
        - User can add a Request (Up/Down & Floor) to Queue
        - User inside an elevator can select multiple floors
        - Elevator will go Up & Down, following Queue Order
        - 3 Elevators / 10 Floors: requests first ask elevators which is closest (nearest in floor) then add to that queue
            - Hall Call: floor requests from hallway user
            - Destination: floor requested from inside elevator
        - step() simulates Time

        Entities
        - ElevatorController
        - Elevator
        - Request (Floor + Up/Down Direction)
    */

    public static void main(String[] args) {
        // Elevators
        Controller controller = new Controller();

        // Actions
        Map<Integer, Request> map = new HashMap<>();
        int counter = 0;
        // Go from 2 to 9
        map.put(counter++, new Request(2, RequestType.PICKUP_UP));
        map.put(counter++, new Request(3, RequestType.PICKUP_DOWN)); // Down
        map.put(counter++, new Request(7, RequestType.PICKUP_DOWN)); //Down
        map.put(counter++, new Request(3, RequestType.PICKUP_UP));
        map.put(counter++, new Request(4, RequestType.PICKUP_UP));
        map.put(counter++, new Request(5, RequestType.PICKUP_UP));
        map.put(counter++, new Request(6, RequestType.PICKUP_UP));
        map.put(counter++, new Request(4, RequestType.PICKUP_DOWN)); //Down

        map.put(counter++, new Request(7, RequestType.PICKUP_DOWN));
        map.put(counter++, new Request(4, RequestType.PICKUP_DOWN));
        map.put(counter++, new Request(2, RequestType.PICKUP_DOWN));
        map.put(counter++, new Request(8, RequestType.PICKUP_DOWN));
        map.put(counter++, new Request(5, RequestType.PICKUP_DOWN));
        map.put(counter++, new Request(3, RequestType.PICKUP_UP));
        map.put(counter++, new Request(8, RequestType.PICKUP_UP));

        // Simulate 100 units of time
        for (int i = 0; i < 50; i++) {
            // Do Action
            if (map.containsKey(i)) {
                Request req = map.get(i);
                controller.floorRequestElevator(req.getFloor(), req.getRequestType());
            }

            if (i == 1) {
                // controller.floorRequestElevator(2, RequestType.PICKUP_UP);
                // controller.floorRequestElevator(3, RequestType.PICKUP_UP);
                // controller.floorRequestElevator(4, RequestType.PICKUP_UP);
                // controller.floorRequestElevator(5, RequestType.PICKUP_UP);
                // controller.floorRequestElevator(6, RequestType.PICKUP_UP);
                // controller.floorRequestElevator(7, RequestType.PICKUP_UP);
                // controller.floorRequestElevator(8, RequestType.PICKUP_UP);
                // controller.floorRequestElevator(2, RequestType.PICKUP_DOWN);
                // controller.floorRequestElevator(3, RequestType.PICKUP_DOWN);
                // controller.floorRequestElevator(4, RequestType.PICKUP_DOWN);
                // controller.floorRequestElevator(5, RequestType.PICKUP_DOWN);
                // controller.floorRequestElevator(6, RequestType.PICKUP_DOWN);
                // controller.floorRequestElevator(7, RequestType.PICKUP_DOWN);
                // controller.floorRequestElevator(8, RequestType.PICKUP_DOWN);
            }

            controller.step();
        }

        controller.printElevatorPaths();
    }
}
