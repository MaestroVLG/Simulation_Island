package javarush_simulation.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SimulationConfig {
    private final int islandWidth;
    private final int islandHeight;
    private final int initialWolves;
    private final int initialRabbits;
    private final int initialDeer;
    private final int plantsPerCell;
    private final long tickDelayMs;
    private final long tickDurationMs;
}