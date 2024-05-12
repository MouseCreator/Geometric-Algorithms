package mouse.project.algorithm.data;

import lombok.Data;
import mouse.project.math.FPosition;
@Data
public class Point {
    private FPosition fPosition;
    private String id;
    public Point(FPosition fPosition, String id) {
        this.fPosition = fPosition;
        this.id = id;
    }
}
