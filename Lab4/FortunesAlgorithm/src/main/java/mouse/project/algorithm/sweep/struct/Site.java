package mouse.project.algorithm.sweep.struct;

import lombok.Data;
import mouse.project.math.FPosition;

@Data
public class Site {
    private FPosition position;

    public Site(FPosition fPosition) {
        this.position = fPosition;
    }
}
