package mouse.project.ui.components.general;

import mouse.project.state.ConstUtils;
import mouse.project.ui.components.main.AppComponent;

import javax.swing.*;
import java.awt.*;

public class GeneralPane implements AppComponent {

    private final ButtonsPane buttonsPane;
    private final PaintingPane paintingPane;
    private final GeneralPanel generalPanel;

    public GeneralPane() {
        buttonsPane = new ButtonsPane();
        paintingPane = new PaintingPane();
        generalPanel = new GeneralPanel();
    }

    @Override
    public Component getComponent() {
        return generalPanel;
    }

    @Override
    public void update() {
        paintingPane.update();
    }

    @Override
    public void redraw() {
        paintingPane.redraw();
    }

    private class GeneralPanel extends JPanel {

        public GeneralPanel() {
            this.setPreferredSize(new Dimension(ConstUtils.WINDOW_WIDTH, ConstUtils.WINDOW_HEIGHT));
            this.setLayout(new BorderLayout());
            this.add(paintingPane.getComponent(), BorderLayout.CENTER);
            this.add(buttonsPane.getComponent(), BorderLayout.EAST);
        }
    }
}
