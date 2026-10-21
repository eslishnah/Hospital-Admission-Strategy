package hospital.bed;

import hospital.model.Bed;
import hospital.source.WardLoadSource;
import java.util.List;

public class LeastBusyWardStrategy implements BedSelectionStrategy {
    private final WardLoadSource loadSource;

    public LeastBusyWardStrategy(WardLoadSource loadSource) {
        this.loadSource = loadSource;
    }

    @Override
    public Bed choose(List<Bed> beds) {
        Bed chosen = null;
        int lowestLoad = Integer.MAX_VALUE;
        for (Bed bed : beds) {
            if (bed.isAvailable()) {
                int load = loadSource.getLoad(bed.getWard());
                if (load < lowestLoad) {
                    chosen = bed;
                    lowestLoad = load;
                }
            }
        }
        return chosen;
    }
}
