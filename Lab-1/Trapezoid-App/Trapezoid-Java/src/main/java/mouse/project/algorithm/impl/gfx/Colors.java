package mouse.project.algorithm.impl.gfx;

import java.awt.*;
import java.util.Random;

public class Colors {
    private static final Random random = new Random();
    private static final int ORIGIN = 190;
    private static final int BOUND = 230;
    public static Color generateSubtle() {
        int r = colorElement();
        int g = colorElement();
        int b = colorElement();
        return new Color(r, g, b);
    }

    private static int colorElement() {
        return random.nextInt(ORIGIN, BOUND);
    }
}
