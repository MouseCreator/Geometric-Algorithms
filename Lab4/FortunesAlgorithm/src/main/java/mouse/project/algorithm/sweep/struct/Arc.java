package mouse.project.algorithm.sweep.struct;

import mouse.project.algorithm.sweep.SweepLine;
import mouse.project.math.FPosition;

import java.util.Collection;

public interface Arc {
    Collection<SweepLine.CircleEvent> getCircleEvents();
    FPosition getOrigin();
}
