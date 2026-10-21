package hospital.bed;

import hospital.model.Bed;
import hospital.selector.HospitalStrategySelector;
import hospital.source.WardLoadSource;
import java.util.List;

public class BedPlanner {
    private final HospitalStrategySelector selector;

    public BedPlanner(WardLoadSource source) {
        this.selector = new HospitalStrategySelector(source);
    }

    public Bed choose(List<Bed> beds, String mode) {
        BedAllocationContext context = new BedAllocationContext(selector.bedByName(mode));
        return context.choose(beds);
    }
}
