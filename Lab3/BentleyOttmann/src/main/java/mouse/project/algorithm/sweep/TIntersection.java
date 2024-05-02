package mouse.project.algorithm.sweep;


import mouse.project.math.Position;

public record TIntersection(TSegment segment1, TSegment segment2, Position intersection) {
    public String print() {
        return segment1 + " intersects " + segment2 + " at " + intersection;
    }
}
