package mouse.project.algorithm;

import java.awt.Color;
import java.util.Random;

public class ColorGenerator {
    private final Random random;

    public ColorGenerator() {
        this.random = new Random();
    }

    public Color generateBright() {
        int r = getBright();
        int g = getBright();
        int b = getBright();
        return new Color(r, g, b);
    }

    private int getBright() {
        return random.nextInt(200, 256);
    }
}
