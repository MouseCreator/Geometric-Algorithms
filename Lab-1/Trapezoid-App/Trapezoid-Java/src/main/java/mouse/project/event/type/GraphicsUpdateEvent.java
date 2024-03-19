package mouse.project.event.type;

import mouse.project.algorithm.InputGraph;

public record GraphicsUpdateEvent(InputGraph inputGraph) implements Event {
}
