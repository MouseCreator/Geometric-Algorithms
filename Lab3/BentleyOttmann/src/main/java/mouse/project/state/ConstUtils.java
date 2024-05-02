package mouse.project.state;

import java.awt.*;

public class ConstUtils {
    public static final int SEGMENT_END_DIAMETER = 6;
    public static final int SEGMENT_THICKNESS = 2;
    public static final int FPS = 60;
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    public static final int WORLD_WIDTH = 1000;
    public static final int WORLD_HEIGHT = 720;
    public static final Color BACKGROUND = Color.WHITE;
    public static final Color BACKGROUND_SECONDARY = Color.LIGHT_GRAY;
    public static final int BUTTON_PANE_WIDTH = WINDOW_WIDTH - WORLD_WIDTH;
    public static final int BUTTON_PANE_HEIGHT = WINDOW_HEIGHT;
    public static final Color TRANSPARENT = new Color(0,0,0,0);
    public static final double MIN_SEGMENT_LENGTH = 8.0;
    public static final Color SEGMENT_COLOR = Color.BLACK;
    public static final double END_AT_TOLERANCE = 4.0;
    public static final Color INTERSECTION_COLOR = Color.ORANGE;
    public static final int INTERSECTION_DIAMETER = 8;
}
