package hospital.lab;

import hospital.model.LabRequest;
import java.util.List;

public class ShortestLabStrategy implements LabSchedulingStrategy {
    @Override
    public LabRequest choose(List<LabRequest> requests) {
        LabRequest chosen = null;
        for (LabRequest request : requests) {
            if (chosen == null || request.getDuration() < chosen.getDuration()
                    || (request.getDuration() == chosen.getDuration()
                    && request.getArrivalOrder() < chosen.getArrivalOrder())) {
                chosen = request;
            }
        }
        return chosen;
    }
}
