package hospital.model;

public class Bed {
    private final String id;
    private final int ward;
    private final int distance;
    private final boolean available;

    public Bed(String id, int ward, int distance, boolean available) {
        this.id = id;
        this.ward = ward;
        this.distance = distance;
        this.available = available;
    }

    public String getId() { return id; }
    public int getWard() { return ward; }
    public int getDistance() { return distance; }
    public boolean isAvailable() { return available; }
}
