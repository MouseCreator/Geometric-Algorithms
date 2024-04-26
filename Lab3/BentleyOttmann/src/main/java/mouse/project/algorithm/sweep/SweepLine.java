package mouse.project.algorithm.sweep;

import mouse.project.algorithm.heap.BinaryHeap;
import mouse.project.algorithm.heap.Heap;
import mouse.project.algorithm.red.RBTree;
import mouse.project.algorithm.red.RBTreeImpl;
import mouse.project.math.Position;
import mouse.project.math.Vector2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SweepLine {
    private final RBTree<TSegment> status;
    private final Heap<Event> eventHeap;

    public SweepLine() {
        status = new RBTreeImpl<>(null); //fix
        eventHeap = new BinaryHeap<>(eventComparator);
    }

    private static final Comparator<Event> eventComparator = (e1, e2) -> {
        if (e1.position().y() < e2.position().y()) {
            return -1;
        }
        if (e1.position().y() > e2.position().y()) {
            return 1;
        }
        return e1.position().x() - e2.position().x();
    };

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

    public void scan(TSegmentSet segmentSet) {
        List<TSegment> all = new ArrayList<>(segmentSet.getAll());
        generateEventsFrom(all);
    }

    private void generateEventsFrom(List<TSegment> all) {
        all.forEach(e -> {
            eventHeap.insert(new StartEvent(e.getUpper(), e));
            eventHeap.insert(new EndEvent(e.getLower(), e));
        });
    }

    private void scanEvents() {
        while (!eventHeap.isEmpty()) {
            Event nextEvent = eventHeap.extractMin();
            List<Event> eventList = new ArrayList<>();
            eventList.add(nextEvent);
            while (eventComparator.compare(eventHeap.minimum(), nextEvent) == 0) {
                eventList.add(eventHeap.extractMin());
            }

        }
    }

}
