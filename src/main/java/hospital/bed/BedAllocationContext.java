package hospital.bed;

import hospital.model.Bed;
import java.util.List;

public class BedAllocationContext {
    private BedSelectionStrategy strategy;

    public BedAllocationContext(BedSelectionStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(BedSelectionStrategy strategy) {
        this.strategy = strategy;
    }

    public Bed choose(List<Bed> beds) {
        return strategy.choose(beds);
    }
}
