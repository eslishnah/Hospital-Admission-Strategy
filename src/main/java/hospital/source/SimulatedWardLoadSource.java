package hospital.source;

import java.util.Map;

public class SimulatedWardLoadSource implements WardLoadSource {
    private final Map<Integer, Integer> loads = Map.of(1, 8, 2, 4, 3, 1);

    @Override
    public int getLoad(int ward) {
        return loads.getOrDefault(ward, 100);
    }
}
