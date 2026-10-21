package hospital.selector;

import hospital.bed.*;
import hospital.lab.*;
import hospital.source.WardLoadSource;

public class HospitalStrategySelector {
    private final WardLoadSource loadSource;

    public HospitalStrategySelector(WardLoadSource loadSource) {
        this.loadSource = loadSource;
    }

    public BedSelectionStrategy selectBed(int urgency, boolean hospitalBusy) {
        if (urgency >= 4) {
            return new NearestBedStrategy();
        } else if (hospitalBusy) {
            return new LeastBusyWardStrategy(loadSource);
        }
        return new FirstAvailableBedStrategy();
    }

    public LabSchedulingStrategy selectLab(int urgency, int waitingCount) {
        if (urgency >= 4) {
            return new UrgentLabStrategy();
        } else if (waitingCount >= 4) {
            return new ShortestLabStrategy();
        }
        return new FirstComeLabStrategy();
    }

    public BedSelectionStrategy bedByName(String name) {
        switch (name.toUpperCase()) {
            case "FIRST": return new FirstAvailableBedStrategy();
            case "NEAREST": return new NearestBedStrategy();
            case "LEAST_BUSY": return new LeastBusyWardStrategy(loadSource);
            default: throw new IllegalArgumentException("Unknown bed strategy: " + name);
        }
    }

    public LabSchedulingStrategy labByName(String name) {
        switch (name.toUpperCase()) {
            case "FIRST": return new FirstComeLabStrategy();
            case "URGENT": return new UrgentLabStrategy();
            case "SHORTEST": return new ShortestLabStrategy();
            default: throw new IllegalArgumentException("Unknown lab strategy: " + name);
        }
    }
}
