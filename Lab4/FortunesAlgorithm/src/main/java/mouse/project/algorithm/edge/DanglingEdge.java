package mouse.project.algorithm.edge;

import lombok.Data;
import mouse.project.algorithm.sweep.struct.Site;
import mouse.project.math.GenLine;
@Data
public class DanglingEdge {
    private final Site generator1;
    private final Site generator2;
    private final GenLine line;

    public DanglingEdge(Site generator1, Site generator2, GenLine line) {
        this.generator1 = generator1;
        this.generator2 = generator2;
        this.line = line;
    }
}
