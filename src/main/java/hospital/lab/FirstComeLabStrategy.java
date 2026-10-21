package hospital.lab;

import hospital.model.LabRequest;
import java.util.List;

public class FirstComeLabStrategy implements LabSchedulingStrategy {
    @Override
    public LabRequest choose(List<LabRequest> requests) {
        LabRequest chosen = null;
        for (LabRequest request : requests) {
            if (chosen == null || request.getArrivalOrder() < chosen.getArrivalOrder()) {
                chosen = request;
            }
        }
        return chosen;
    }
}
