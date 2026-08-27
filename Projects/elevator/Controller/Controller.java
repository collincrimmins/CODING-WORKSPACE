package Projects.elevator.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import Projects.elevator.Elevator.Elevator;
import Projects.elevator.Elevator.ElevatorStatus;
import Projects.elevator.Request.Request;
import Projects.elevator.Request.RequestType;

public class Controller {
    List<Elevator> list;
    int floorMin = 0;
    int floorMax = 9;

    public Controller() {
        list = new ArrayList<>();

        // 3 Elevators
        list.add(new Elevator());
        list.add(new Elevator());
        list.add(new Elevator());
    }

    public void floorRequestElevator(int floor, RequestType type) {
        if (floor < floorMin || floor > floorMax) {
            throw new IllegalArgumentException("invalid floor");
        }

        Request request = new Request(floor, type);
        findBestElevator(request);
    }

    void findBestElevator(Request request) {
        // At each level closest to my level, find Elevator moving in my direction or idle
        int floor = request.getFloor();
        RequestType type = request.getRequestType();

        // Default to Getting IDLE Elevator
        int i = floor;
        int j = floor;
        while (i >= floorMin || j <= floorMax) {
            for (Elevator elevator : list) {
                // This Floor
                if (elevator.getFloor() == i || elevator.getFloor() == j) {
                    queueBestElevator(elevator, request);
                    return;
                }
                
                // Use Idle Elevator First
                if (elevator.getStatus() == ElevatorStatus.IDLE) {
                    queueBestElevator(elevator, request);
                    return;
                }
            }

            i = i - 1;
            j = j + 1;
        }

        // Try moving elevators in my direction
        i = floor;
        j = floor;
        while (i >= floorMin || j <= floorMax) {
            for (Elevator elevator : list) {
                // This Floor
                if (elevator.getFloor() == i || elevator.getFloor() == j) {
                    queueBestElevator(elevator, request);
                    return;
                }
                
                // Use Idle Elevator First
                if (elevator.getStatus() == ElevatorStatus.IDLE) {
                    queueBestElevator(elevator, request);
                    return;
                }

                // Moving Down
                if (elevator.getFloor() > floor && elevator.getStatus() == ElevatorStatus.DOWN && type == RequestType.PICKUP_DOWN) {
                    queueBestElevator(elevator, request);
                    return;
                }

                // Moving Up
                if (elevator.getFloor() < floor && elevator.getStatus() == ElevatorStatus.UP && type == RequestType.PICKUP_UP) {
                    queueBestElevator(elevator, request);
                    return;
                }
            }

            i = i - 1;
            j = j + 1;
        }

        // Closest Floor
        i = floor;
        j = floor;
        while (i >= floorMin || j <= floorMax) {
            for (Elevator elevator : list) {
                // This Floor
                System.out.println(elevator.getFloor() + " " + i + " " + j);
                if (elevator.getFloor() == i || elevator.getFloor() == j) {
                    queueBestElevator(elevator, request);
                    return;
                }
            }

            i = i - 1;
            j = j + 1;
        }

        throw new RuntimeException("Error adding " + request.getFloor() + " " + request.getRequestType());
    }

    void queueBestElevator(Elevator elevator, Request request) {
        elevator.addRequest(request);
    }

    public List<Elevator> getList() {
        return list;
    }

    // Execute 1 unit of time
    public void step() {
        for (Elevator elevator : list) {
            elevator.step();
        }
    }

    public void printStatuses() {
        for (Elevator elevator : list) {
            elevator.printStatus();
        }
    }

    public void printElevatorPaths() {
        for (Elevator elevator : list) {
            elevator.printFullPath();
        }
    }
}
