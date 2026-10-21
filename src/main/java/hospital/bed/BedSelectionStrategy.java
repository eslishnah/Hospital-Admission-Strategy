package hospital.bed;

import hospital.model.Bed;
import java.util.List;

public interface BedSelectionStrategy {
    Bed choose(List<Bed> beds);
}
