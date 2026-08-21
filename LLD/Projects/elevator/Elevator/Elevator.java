package LLD.Projects.elevator.Elevator;

import java.net.Authenticator.RequestorType;
import java.util.HashSet;
import java.util.Set;

import LLD.Projects.elevator.Request.Request;
import LLD.Projects.elevator.Request.RequestType;

public class Elevator {
    int floor; // 0 to 9
    ElevatorStatus status;
    Set<Request> requests;
    String path;

    public Elevator() {
        this.floor = 0;
        this.status = ElevatorStatus.IDLE;
        this.requests = new HashSet<>();
        path = "0";
    }

    void printRequests() {
        for (Request req : requests) {
            req.printState();
        }
    }

    // Go Up/Down until there is no higher/lower number in queue
    // Stop at all floors along the path
    public void step() {
        // Debug: Print State
        if (requests.size() > 0) {
            System.out.println("-------");
            System.out.println(floor + " " + status);
            printRequests();
        }

        // Check Next for Above & Below
        boolean requestAboveMe = false;
        boolean requestBelowMe = false;
        for (Request req : requests) {
            if (req.getFloor() > floor) {
                requestAboveMe = true;
            } else if (req.getFloor() < floor) {
                requestBelowMe = true;
            }
        }

        // Pickup/Dropoff This Floor & Pause Step
        for (Request request : requests) {
            if (request.getFloor() == floor) {
                // Exit User at Floor
                if (request.getRequestType() == RequestType.DESTINATION) {
                    // Exit User at Floor
                    requests.remove(request);
                    path = path + " (exit)";

                    // Check Direction & Requests Ahead - and then reverse
                    if (!requestAboveMe && !requestAboveMe) {
                        status = ElevatorStatus.IDLE;
                    } else if (status == ElevatorStatus.UP && !requestAboveMe && requestBelowMe) {
                        // Reverse Path
                        status = ElevatorStatus.DOWN;
                    } else if (status == ElevatorStatus.DOWN && requestAboveMe && !requestBelowMe) {
                        // Reverse Path
                        status = ElevatorStatus.UP;
                    }

                    // Hold Movement
                    return;
                }
                
                // Pickup on Path Up OR Start new Path Up
                if ((request.getRequestType() == RequestType.PICKUP_UP && status == ElevatorStatus.UP)
                    || (request.getRequestType() == RequestType.PICKUP_UP && !requestBelowMe)
                ) {
                    // Remove Pickup Request
                    requests.remove(request);
                    path = path + " (pickup up)";

                    // Simulate In-Elevator Button Push
                    addRequest(new Request(9, RequestType.DESTINATION));

                    // Set Direction
                    status = ElevatorStatus.UP;

                    // Hold Movement
                    return;
                }

                // Pickup on Path Down OR Start new Path Down
                if ((request.getRequestType() == RequestType.PICKUP_DOWN && status == ElevatorStatus.DOWN)
                    || (request.getRequestType() == RequestType.PICKUP_DOWN && !requestAboveMe)
                ) {
                    // Remove Pickup Request
                    requests.remove(request);
                    path = path + " (pickup down)";

                    // Simulate In-Elevator Button Push
                    addRequest(new Request(0, RequestType.DESTINATION));

                    // Set Direction
                    status = ElevatorStatus.DOWN;

                    // Hold Movement
                    return;
                }
            }
        }

        // Start Moving from Idle
        if (status == ElevatorStatus.IDLE) {
            if (requestAboveMe) {
                status = ElevatorStatus.UP;
            } else if (requestBelowMe) {
                status = ElevatorStatus.DOWN;
            }
        }

        // Continue Up/Down
        if (status == ElevatorStatus.UP) {
            floor = floor + 1;
            path = path + " -> " + floor;
        } else if (status == ElevatorStatus.DOWN) {
            floor = floor - 1;
            path = path + " -> " + floor;
        }
    }

    public void addRequest(Request newReq) {
        // Print State
        //System.out.println("adding " + newReq.getFloor() + " " + newReq.getRequestType());

        // Block In-Elevator users from pressing current floor
        if (newReq.getFloor() == floor && newReq.getRequestType() == RequestType.DESTINATION) {
            System.out.println("X - Request Floor is same as current floor: " + floor);
            return;
        }

        // Prevent Identical Requests
        for (Request request : requests) {
            if (request.checkIdenticalRequest(newReq)) {
                // System.out.println("X - Identical Request rejected");
                // request.printState();
                // newReq.printState();
                return;
            }
        }

        requests.add(newReq);
    }

    public int getFloor() {
        return floor;
    }

    public ElevatorStatus getStatus() {
        return status;
    }

    public void printStatus() {
        String dir;
        if (status == ElevatorStatus.UP) {
            dir = "UP";
        } else if (status == ElevatorStatus.DOWN) {
            dir = "DOWN";
        } else {
            dir = "IDLE";
        }
        System.out.println("Elevator [" + floor + "] " + dir);
    }

    public void printFullPath() {
        System.out.println("Elevator: " + path);
        // for (Request request : requests) {
        //     System.out.println(request.getFloor());
        // }
    }
}
