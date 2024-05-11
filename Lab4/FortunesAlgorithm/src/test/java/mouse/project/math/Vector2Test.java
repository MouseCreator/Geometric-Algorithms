package mouse.project.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2Test {

    @Test
    void angle() {
        Vector2 ox = Vector2.of(1, 0);
        Vector2 v45 = Vector2.of(1, 1);
        double angle45 = Math.PI/4;
        double angle = ox.angle(v45);
        assertDoublesEqual(angle45, angle);

        Vector2 v315 = Vector2.of(1, -1);
        double angle315 = Math.PI*7/4;
        double angle2 = ox.angle(v315);
        assertDoublesEqual(angle315, angle2);
    }

    private void assertDoublesEqual(double d1, double d2) {
        assertTrue(Numbers.dEquals(d1, d2), "Numbers " + d1 + " and " + d2 + " are not equal.");
    }
}