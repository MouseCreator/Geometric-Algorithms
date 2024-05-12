package mouse.project.algorithm.sweep.parabola;

import mouse.project.algorithm.sweep.struct.Site;
import mouse.project.math.FPosition;
import mouse.project.math.Numbers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParabolaServiceTest {
    private ParabolaService parabolaService;

    @BeforeEach
    void setUp() {
        parabolaService = new ParabolaService();
    }



    @Test
    void getParabolaFromSiteAndLine() {
        Site site = new Site(FPosition.of(10, 15), 1);
        double y = 32;
        Parabola parabola = parabolaService.getParabolaFromSiteAndLine(site, y);
        assertParabolaEquals(new Parabola(-0.0294117647059, 0.588235294118, 20.5588235294), parabola);
    }

    private void assertParabolaEquals(Parabola p1, Parabola p2) {
        boolean result;
        result = Numbers.dEquals(p1.a(), p2.a());
        result = result && Numbers.dEquals(p1.b(), p2.b());
        result = result && Numbers.dEquals(p1.c(), p2.c());
        assertTrue(result, "Parabolas are not equal: " + p1 + ", " + p2);
    }

    private void assertPositionsEquals(FPosition p1, FPosition p2) {
        boolean result;
        result = Numbers.dEquals(p1.x(), p2.x());
        result = result && Numbers.dEquals(p1.y(), p2.y());
        assertTrue(result, "Positions are not equal: " + p1 + ", " + p2);
    }

    @Test
    void findIntersection() {
        Parabola p1 = new Parabola(1, 2,3);
        Parabola p2 = new Parabola(0.2, 4, 6);
        FPosition intersection = parabolaService.findIntersection(p1, p2);
        assertPositionsEquals(FPosition.of(-1.054886114323,2.003012485545), intersection);
    }
}