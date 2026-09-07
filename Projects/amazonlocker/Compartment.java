package Projects.amazonlocker;

public class Compartment {
    private final int id;
    private CompartmentStatus status;
    private final PackageSize size;

    public Compartment(int id, PackageSize size) {
        this.id = id;
        this.status = CompartmentStatus.AVAILABLE;
        this.size = size;
    }

    public boolean setOccupied() {
        if (status == CompartmentStatus.OCCUPIED) {
            System.err.println("Error: Occupied!");
            return false;
        }

        status = CompartmentStatus.OCCUPIED;

        return true;
    }

    public void openAndSetUnoccupied() {
        status = CompartmentStatus.AVAILABLE;
    }

    public CompartmentStatus getStatus() {
        return status;
    }

    public PackageSize getSize() {
        return size;
    }

    public int getId() {
        return id;
    }

    public String getPrintStatus() {
        if (status == CompartmentStatus.AVAILABLE) {
            return "Compartment #" + id + ": {}";
        }
        return "Compartment #" + id + ": occupied " + size;
    }
}
