package javarush_simulation.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class SimulationConfig {
    private int islandWidth;
    private int islandHeigth;
    
    private int innitiaslisationWolves;
    private int innitiaslisationRabbits;
    private int innitiaslisationDeer;

    private int plantsPerCell;

    private long tickDurationMs;

    
    
    
}
