
package hospital.bed;

import hospital.model.Bed;
import java.util.List;

public class MaxDistanceBedStrategy implements BedSelectionStrategy {

    private final BedSelectionStrategy wrapped;
    private final int maxDistance;

    public MaxDistanceBedStrategy(BedSelectionStrategy wrapped, int maxDistance) {
        this.wrapped = wrapped;
        this.maxDistance = maxDistance;
    }

    @Override
    public Bed choose(List<Bed> beds) {
        Bed selected = wrapped.choose(beds);

        if (selected == null || selected.getDistance() > maxDistance) {
            return null;
        }

        return selected;
    }
}
