package mouse.project.graphics;

import mouse.project.ui.components.main.Drawable;

public interface GFX extends Drawable {
    default void dehighlight() {};
    default void highlight() {};
}
