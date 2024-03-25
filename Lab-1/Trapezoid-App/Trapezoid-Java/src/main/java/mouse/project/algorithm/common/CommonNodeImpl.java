package mouse.project.algorithm.common;

import mouse.project.utils.math.Position;

public record CommonNodeImpl(Position position, String key, boolean extra) implements CommonNode {
}
