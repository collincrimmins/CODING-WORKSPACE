package Projects.amazonlocker;

public enum PackageSize {
    SMALL(1),
    MEDIUM(2),
    LARGE(3);

    private final int size;

    PackageSize(int size) {
        this.size = size;
    }

    public boolean canFitIntoCompartment(PackageSize locker) {
        return locker.size >= this.size; // Bug: logic is reversed
    }
}
