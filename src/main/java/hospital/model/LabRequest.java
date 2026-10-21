package hospital.model;

public class LabRequest {
    private final String id;
    private final int arrivalOrder;
    private final int urgency;
    private final int duration;

    public LabRequest(String id, int arrivalOrder, int urgency, int duration) {
        this.id = id;
        this.arrivalOrder = arrivalOrder;
        this.urgency = urgency;
        this.duration = duration;
    }

    public String getId() { return id; }
    public int getArrivalOrder() { return arrivalOrder; }
    public int getUrgency() { return urgency; }
    public int getDuration() { return duration; }
}
