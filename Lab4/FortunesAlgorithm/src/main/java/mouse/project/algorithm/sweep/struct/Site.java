package mouse.project.algorithm.sweep.struct;

import lombok.Data;
import mouse.project.math.FPosition;

@Data
public class Site {
    private FPosition position;
    private int id;
    public Site(FPosition fPosition, int id) {
        this.position = fPosition;
        this.id = id;
    }
}
