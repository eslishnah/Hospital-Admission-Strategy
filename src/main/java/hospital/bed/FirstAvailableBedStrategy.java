package hospital.bed;

import hospital.model.Bed;
import java.util.List;

public class FirstAvailableBedStrategy implements BedSelectionStrategy {
    @Override
    public Bed choose(List<Bed> beds) {
        for (Bed bed : beds) {
            if (bed.isAvailable()) {
                return bed;
            }
        }
        return null;
    }
}
