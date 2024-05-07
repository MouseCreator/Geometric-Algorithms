package mouse.project.algorithm.sweep.parabola;

import mouse.project.algorithm.sweep.struct.Site;
import mouse.project.math.FPosition;

public class ParabolaService {
    public FPosition findIntersection(Parabola t, Parabola o) {
        double da = t.a - o.a;
        double db = t.b - o.b;
        double dc = t.c - o.c;
        double[] solution = Solver.solveQuadratic(da, db, dc);
        if (solution.length == 0) {
            throw new IllegalStateException("Parabolas " + this + " and " + o + " do not intersect");
        }
        if (solution.length == 1) {
            double x = solution[0];
            double y = t.yAt(x);
            return FPosition.of(x, y);
        }
        if (solution.length == 2) {
            double x1 = t.lowestPoint().x();
            double x2 = o.lowestPoint().x();
            double sol1 = solution[0];
            double sol2 = solution[1];
            double sol;
            if (x1 < x2) {
                sol = Math.min(sol1, sol2);
            }
            else {
                sol = Math.max(sol1, sol2);
            }
            return FPosition.of(sol, t.yAt(sol));
        }
        throw new IllegalStateException("Unexpected number of solutions: " + solution.length);
    }

    public Parabola getParabolaFromSiteAndLine(Site s0, double y) {
        double x1 = s0.getPosition().x();
        double y1 = s0.getPosition().y();

        return new Parabola(
                1.0 / (2.0 * (y1 - y)),
                x1 / (y1 - y),
                (x1*x1 + y1*y1 - y * y)/(2*(y1- y))
        );
    }
}
