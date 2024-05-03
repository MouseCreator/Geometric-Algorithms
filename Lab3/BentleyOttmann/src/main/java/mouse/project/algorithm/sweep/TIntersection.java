package mouse.project.algorithm.sweep;


import mouse.project.math.FPosition;

public record TIntersection(TSegment segment1, TSegment segment2, FPosition intersection) {
    public String print() {
        return segment1 + " intersects " + segment2 + " at " + intersection;
    }
}
