package mouse.project.algorithm.data;

import lombok.Data;
import mouse.project.math.FPosition;
@Data
public class Point {
    private FPosition fPosition;

    public Point(FPosition fPosition) {
        this.fPosition = fPosition;
    }
}
