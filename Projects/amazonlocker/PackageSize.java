package Projects.amazonlocker;

public enum PackageSize {
    SMALL(1),
    MEDIUM(2),
    LARGE(3);

    private final int size;

    PackageSize(int size) {
        this.size = size;
    }

    public boolean canFit(PackageSize size) {
        return size.size >= this.size;
    }
}
