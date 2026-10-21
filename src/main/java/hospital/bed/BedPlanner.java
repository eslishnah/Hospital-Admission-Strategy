package hospital.bed;

import hospital.model.Bed;
import hospital.source.WardLoadSource;
import java.util.List;

public class BedPlanner {
    private final WardLoadSource source;

    public BedPlanner(WardLoadSource source) {
        this.source = source;
    }

    public Bed choose(List<Bed> beds, String mode) {
        Bed chosen = null;
        if (mode.equals("FIRST")) {
            for (Bed bed : beds) {
                if (bed.isAvailable()) return bed;
            }
        } else if (mode.equals("NEAREST")) {
            for (Bed bed : beds) {
                if (bed.isAvailable() && (chosen == null || bed.getDistance() < chosen.getDistance())) {
                    chosen = bed;
                }
            }
        } else if (mode.equals("LEAST_BUSY")) {
            int lowestLoad = Integer.MAX_VALUE;
            for (Bed bed : beds) {
                if (bed.isAvailable()) {
                    int load = source.getLoad(bed.getWard());
                    if (load < lowestLoad) {
                        lowestLoad = load;
                        chosen = bed;
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Unknown mode: " + mode);
        }
        return chosen;
    }
}
