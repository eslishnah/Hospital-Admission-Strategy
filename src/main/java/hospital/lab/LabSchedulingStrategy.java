package hospital.lab;

import hospital.model.LabRequest;
import java.util.List;

public interface LabSchedulingStrategy {
    LabRequest choose(List<LabRequest> requests);
}
