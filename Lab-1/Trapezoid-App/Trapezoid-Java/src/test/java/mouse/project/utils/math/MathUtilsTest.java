package mouse.project.utils.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathUtilsTest {

    @Test
    void toGenLine() {
        Line line = new Line(Position.of(6,8), Vector2.of(6,8));
        GenLine genLine = MathUtils.toGenLine(line);
        GenLine expected = new GenLine(-0.8, 0.6, 0);
        lineEquals(expected ,genLine);
    }

    private void lineEquals(GenLine expected, GenLine genLine) {
        double minW = 0.0001;
        assertTrue(Math.abs( expected.a() - genLine.a()) < minW);
        assertTrue(Math.abs( expected.b() - genLine.b()) < minW);
        assertTrue(Math.abs( expected.c() - genLine.c()) < minW);
    }

    @Test
    void getIntersection() {
    }
}