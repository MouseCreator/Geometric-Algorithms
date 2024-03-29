package mouse.project.algorithm.impl.gfx;

import java.awt.*;
import java.util.Random;

public class Colors {
    private static final Random random = new Random();
    private static final int DIFF = 100;
    private static final int ORIGIN = 100;
    public static Color generateSubtle() {
        int r = random.nextInt(ORIGIN, 256);
        int g = random.nextInt(ORIGIN, 256);
        int b = random.nextInt(ORIGIN, 256);
        return new Color(r, g, b);
    }

    public static Color highlight(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        return new Color(red - DIFF, green - DIFF, blue - DIFF);
    }

    public static Color dehighlight(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        return new Color(red + DIFF, green + DIFF, blue + DIFF);
    }
}
