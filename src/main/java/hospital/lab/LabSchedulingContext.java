package hospital.lab;

import hospital.model.LabRequest;
import java.util.List;

public class LabSchedulingContext {
    private LabSchedulingStrategy strategy;

    public LabSchedulingContext(LabSchedulingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(LabSchedulingStrategy strategy) {
        this.strategy = strategy;
    }

    public LabRequest choose(List<LabRequest> requests) {
        return strategy.choose(requests);
    }
}
