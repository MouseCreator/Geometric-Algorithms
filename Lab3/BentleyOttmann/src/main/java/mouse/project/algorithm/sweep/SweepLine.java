package mouse.project.algorithm.sweep;

import mouse.project.algorithm.red.RBTree;
import mouse.project.algorithm.red.RBTreeImpl;
import mouse.project.math.Position;
import mouse.project.math.Vector2;

import java.util.Comparator;

public class SweepLine {

    private interface Event {
        Position position();
    }

    private record StartEvent(Position position, TSegment segment) implements Event {
    }

    private record EndEvent(Position position, TSegment segment) implements Event {
    }

    private static class IntersectionEvent implements Event {
        private final Position position;
        private final RBTree<TSegment> orderedSegments;
        private static final Comparator<TSegment> comparator = (s1, s2) -> {
            Vector2 v1 = s1.direction();
            Vector2 v2 = s2.direction();
            Vector2 left = Vector2.of(1, 0);
            double diff = left.cos(v1) - left.cos(v2);
            double tolerance = 0.0001;
            if (Math.abs(diff) < tolerance) {
                return 0;
            }
            if (diff < -tolerance) {
                return -1;
            }
            return 1;
        };
        public IntersectionEvent(Position position) {
            this.position = position;
            orderedSegments = new RBTreeImpl<>(comparator);
        }
        public void add(TSegment segment) {
            this.orderedSegments.insert(segment);
        }

        @Override
        public Position position() {
            return position;
        }
    }
}
