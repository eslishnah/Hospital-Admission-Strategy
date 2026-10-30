package hospital;

import hospital.bed.*;
import hospital.lab.*;
import hospital.model.Bed;
import hospital.model.LabRequest;
import hospital.selector.HospitalStrategySelector;
import hospital.source.WardLoadSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class StrategyTest {
    private final WardLoadSource fake = ward -> Map.of(1, 8, 2, 4, 3, 1).get(ward);
    private final HospitalStrategySelector selector = new HospitalStrategySelector(fake);
    private final List<Bed> beds = List.of(new Bed("B1", 1, 8, true),
            new Bed("B2", 2, 2, true), new Bed("B3", 3, 5, true),
            new Bed("B4", 3, 1, false));
    private final List<LabRequest> requests = List.of(
            new LabRequest("L1", 1, 2, 10), new LabRequest("L2", 2, 5, 8),
            new LabRequest("L3", 3, 3, 3));

    @Test void firstAvailableBed() {
        assertEquals("B1", new FirstAvailableBedStrategy().choose(beds).getId());
    }

    @Test void nearestBedSkipsUnavailable() {
        assertEquals("B2", new NearestBedStrategy().choose(beds).getId());
    }

    @Test void leastBusyUsesFakeExternalSource() {
        assertEquals("B3", new LeastBusyWardStrategy(fake).choose(beds).getId());
    }

    @Test void firstComeLab() {
        assertEquals("L1", new FirstComeLabStrategy().choose(requests).getId());
    }

    @Test void urgentLab() {
        assertEquals("L2", new UrgentLabStrategy().choose(requests).getId());
    }

    @Test void shortestLab() {
        assertEquals("L3", new ShortestLabStrategy().choose(requests).getId());
    }

    @Test void bedSelectorUrgentRule() {
        assertInstanceOf(NearestBedStrategy.class, selector.selectBed(5, true));
    }

    @Test void bedSelectorBusyRule() {
        assertInstanceOf(LeastBusyWardStrategy.class, selector.selectBed(2, true));
    }

    @Test void bedSelectorDefaultRule() {
        assertInstanceOf(FirstAvailableBedStrategy.class, selector.selectBed(2, false));
    }

    @Test void labSelectorUrgentRule() {
        assertInstanceOf(UrgentLabStrategy.class, selector.selectLab(5, 6));
    }

    @Test void labSelectorQueueRule() {
        assertInstanceOf(ShortestLabStrategy.class, selector.selectLab(2, 4));
    }

    @Test void labSelectorDefaultRule() {
        assertInstanceOf(FirstComeLabStrategy.class, selector.selectLab(2, 2));
    }

    @Test void contextsSwitchAtRuntime() {
        BedAllocationContext bedContext = new BedAllocationContext(new FirstAvailableBedStrategy());
        assertEquals("B1", bedContext.choose(beds).getId());
        bedContext.setStrategy(new NearestBedStrategy());
        assertEquals("B2", bedContext.choose(beds).getId());

        LabSchedulingContext labContext = new LabSchedulingContext(new FirstComeLabStrategy());
        assertEquals("L1", labContext.choose(requests).getId());
        labContext.setStrategy(new UrgentLabStrategy());
        assertEquals("L2", labContext.choose(requests).getId());
    }

    @Test void replacingExternalSourceChangesDecision() {
        WardLoadSource otherFake = ward -> Map.of(1, 0, 2, 10, 3, 20).get(ward);
        assertEquals("B1", new LeastBusyWardStrategy(otherFake).choose(beds).getId());
    }


    @Test void manualSelectorNamesAndInvalidMode() {
        assertInstanceOf(FirstAvailableBedStrategy.class, selector.bedByName("FIRST"));
        assertInstanceOf(NearestBedStrategy.class, selector.bedByName("NEAREST"));
        assertInstanceOf(LeastBusyWardStrategy.class, selector.bedByName("LEAST_BUSY"));
        assertInstanceOf(FirstComeLabStrategy.class, selector.labByName("FIRST"));
        assertInstanceOf(UrgentLabStrategy.class, selector.labByName("URGENT"));
        assertInstanceOf(ShortestLabStrategy.class, selector.labByName("SHORTEST"));
        assertThrows(IllegalArgumentException.class, () -> selector.bedByName("BAD"));
        assertThrows(IllegalArgumentException.class, () -> selector.labByName("BAD"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"FIRST", "NEAREST", "LEAST_BUSY"})
    void allBedStrategiesReturnNullWhenNoBedIsAvailable(String mode) {
        List<Bed> unavailable = List.of(new Bed("X", 1, 1, false));
        BedAllocationContext context = new BedAllocationContext(selector.bedByName(mode));
        assertNull(context.choose(unavailable));
    }

    
    @Test
    void wrapperLimitsAllBedStrategies() {
        BedSelectionStrategy first =
                new MaxDistanceBedStrategy(new FirstAvailableBedStrategy(), 5);

        BedSelectionStrategy nearest =
                new MaxDistanceBedStrategy(new NearestBedStrategy(), 5);

        BedSelectionStrategy leastBusy =
                new MaxDistanceBedStrategy(new LeastBusyWardStrategy(fake), 5);

    assertNull(first.choose(beds));
    assertEquals("B2", nearest.choose(beds).getId());
    assertEquals("B3", leastBusy.choose(beds).getId());
}



}
