package LLD.Projects.elevator.Request;

public class Request {
    int floor; // Hallway
    // int floorDestination; // In Elevator 
    RequestType requestType;

    public Request(int floor, RequestType requestType) {
        this.floor = floor;
        this.requestType = requestType;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public boolean checkIdenticalRequest(Request req) {
        return floor == req.getFloor() && requestType == req.getRequestType();
    }

    public void printState() {
        System.out.println("- " + floor + " " + requestType);
    }

    // public int getFloorDestination() {
    //     return floorDestination;
    // }

    // public void setFloorDestination(int floorDestination) {
    //     this.floorDestination = floorDestination;
    // }
}
