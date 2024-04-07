package mouse.project.ui;

import java.awt.*;

public interface Application {
    void init() ;
    void draw();
    void update();
    Component core();
}
