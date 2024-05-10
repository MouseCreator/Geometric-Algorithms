package mouse.project.algorithm.sweep;


import mouse.project.math.FPosition;

import java.util.Objects;


public record TIntersection(TSegment segment1, TSegment segment2, FPosition intersection) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TIntersection that = (TIntersection) o;
        return Objects.equals(segment1, that.segment1) && Objects.equals(segment2, that.segment2)
                || Objects.equals(segment2, that.segment1) && Objects.equals(segment1, that.segment2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(segment1, segment2);
    }

    public String print() {
        return segment1 + " intersects " + segment2 + " at " + intersection;
    }
}
