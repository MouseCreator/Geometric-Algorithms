package mouse.project.ui.components.main;

import java.awt.*;

public interface Model {
    Component getComponent();
    default void update() {}
    default void redraw() {}
}
