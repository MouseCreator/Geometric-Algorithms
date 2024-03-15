package mouse.project.ui.components.main;

import java.awt.*;

public interface AppComponent {
    Component getComponent();
    default void update() {}
    default void redraw() {}
}
