package mouse.project.algorithm.sweep.diagram;

import lombok.Getter;
import mouse.project.math.FPosition;

import java.util.List;

@Getter
public class Face {
    private final List<FPosition> nodes;
    public Face(List<FPosition> nodes) {
        this.nodes = nodes;
    }
}
