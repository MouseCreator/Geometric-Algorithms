package mouse.project.ui;

import mouse.project.state.DefaultStateHolder;
import mouse.project.state.State;
import mouse.project.ui.components.general.GeneralPane;

import java.awt.*;

public class DrawApplication implements Application {

    private GeneralPane mainPanel;

    @Override
    public void init() {
        State.use(new DefaultStateHolder());
        mainPanel = new GeneralPane();
    }

    @Override
    public void draw() {
        mainPanel.redraw();
    }

    public void update() {
        mainPanel.update();
    }

    @Override
    public Component core() {
        return mainPanel.getComponent();
    }
}
