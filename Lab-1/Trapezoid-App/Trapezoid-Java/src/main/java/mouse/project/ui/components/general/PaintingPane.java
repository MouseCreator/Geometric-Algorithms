package mouse.project.ui.components.general;

import mouse.project.state.ConstUtils;
import mouse.project.state.ProgramMode;
import mouse.project.state.State;
import mouse.project.ui.components.draw.DrawManager;
import mouse.project.ui.components.graph.Position;
import mouse.project.ui.components.graph.UIGraph;
import mouse.project.ui.components.main.AppComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaintingPane implements AppComponent {
    private final DrawPanel drawPanel;
    private final UIGraph graph;
    private static final Logger logger = LogManager.getLogger(PaintingPane.class);
    private final DrawManager drawManager;
    private final List<ClickHandler> clickHandlers;
    public PaintingPane() {
        this.drawPanel = new DrawPanel();
        graph = new UIGraph();
        drawManager = State.get().getProgramState().getDrawManager();
        clickHandlers = createClickHandlers();
    }

    private List<ClickHandler> createClickHandlers() {
        List<ClickHandler> handlers = new ArrayList<>();
        handlers.add(new NodesClickHandler());
        return handlers;
    }

    @Override
    public void redraw() {
        drawPanel.repaint();
    }
    @Override
    public Component getComponent() {
        return drawPanel;
    }
    @Override
    public void update() {

    }

    private class DrawPanel extends JPanel {

        public DrawPanel() {
            this.setPreferredSize(new Dimension(ConstUtils.WORLD_WIDTH, ConstUtils.WORLD_HEIGHT));
            this.setBackground(ConstUtils.BACKGROUND);
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Point point = e.getPoint();
                    int x = point.x;
                    int y = point.y;
                    logger.debug("Click at " + x + " " + y);
                    if (x > ConstUtils.WORLD_WIDTH && y > ConstUtils.WORLD_HEIGHT) {
                        return;
                    }
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        handleLeftClick(Position.of(x, y));
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            drawManager.drawAll(g2d);
        }
    }

    public void handleLeftClick(Position clickAt) {
        ProgramMode mode = State.get().getProgramState().getMode();
        Optional<ClickHandler> handler = clickHandlers.stream().filter(s -> s.isApplied(mode)).findFirst();
        handler.ifPresent(h -> h.apply(clickAt));
    }

    private interface ClickHandler {
        boolean isApplied(ProgramMode mode);
        void apply(Position position);
    }

    private class NodesClickHandler implements ClickHandler {

        @Override
        public boolean isApplied(ProgramMode mode) {
            return mode == ProgramMode.NODE;
        }

        @Override
        public void apply(Position position) {
            graph.addNodeAt(position);
        }
    }
}
