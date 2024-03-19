package mouse.project.event.type;

import mouse.project.algorithm.common.Trapezoid;
import mouse.project.event.type.Event;

public record TrapezoidEvent(Trapezoid trapezoid) implements Event {

}
