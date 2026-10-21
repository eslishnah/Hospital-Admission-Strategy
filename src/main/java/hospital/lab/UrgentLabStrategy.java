package hospital.lab;

import hospital.model.LabRequest;
import java.util.List;

public class UrgentLabStrategy implements LabSchedulingStrategy {
    @Override
    public LabRequest choose(List<LabRequest> requests) {
        LabRequest chosen = null;
        for (LabRequest request : requests) {
            if (chosen == null || request.getUrgency() > chosen.getUrgency()
                    || (request.getUrgency() == chosen.getUrgency()
                    && request.getArrivalOrder() < chosen.getArrivalOrder())) {
                chosen = request;
            }
        }
        return chosen;
    }
}
