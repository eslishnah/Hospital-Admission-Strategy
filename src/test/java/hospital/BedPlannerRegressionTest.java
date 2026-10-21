package hospital;

import hospital.bed.BedPlanner;
import hospital.model.Bed;
import hospital.source.WardLoadSource;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class BedPlannerRegressionTest {
    @Test
    void originalBehaviorStaysTheSame() {
        WardLoadSource fake = ward -> Map.of(1, 8, 2, 4, 3, 1).get(ward);
        BedPlanner planner = new BedPlanner(fake);
        List<Bed> beds = List.of(new Bed("B1", 1, 8, true),
                new Bed("B2", 2, 2, true), new Bed("B3", 3, 5, true));

        assertEquals("B1", planner.choose(beds, "FIRST").getId());
        assertEquals("B2", planner.choose(beds, "NEAREST").getId());
        assertEquals("B3", planner.choose(beds, "LEAST_BUSY").getId());
    }
}
