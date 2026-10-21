package hospital.bed;

import hospital.model.Bed;
import java.util.List;

public class NearestBedStrategy implements BedSelectionStrategy {
    @Override
    public Bed choose(List<Bed> beds) {
        Bed chosen = null;
        for (Bed bed : beds) {
            if (bed.isAvailable() && (chosen == null || bed.getDistance() < chosen.getDistance())) {
                chosen = bed;
            }
        }
        return chosen;
    }
}
