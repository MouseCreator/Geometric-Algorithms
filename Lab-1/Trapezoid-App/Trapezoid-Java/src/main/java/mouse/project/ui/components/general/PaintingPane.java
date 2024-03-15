package mouse.project.ui.components.general;

import mouse.project.state.ConstUtils;
import mouse.project.state.MouseAction;
import mouse.project.state.ProgramMode;
import mouse.project.state.State;
import mouse.project.ui.components.draw.DrawManager;
import mouse.project.ui.components.graph.*;
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
        handlers.add(new EdgesClickHandler());
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
            MouseAdapter mouseAdapter = new MouseAdapter() {

                private void handleAction(MouseEvent e, MouseAction mouseAction) {
                    Point point = e.getPoint();
                    int x = point.x;
                    int y = point.y;
                    if (x > ConstUtils.WORLD_WIDTH && y > ConstUtils.WORLD_HEIGHT) {
                        return;
                    }
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        onAction(mouseAction, Position.of(x, y));
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    handleAction(e, MouseAction.CLICK);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    handleAction(e, MouseAction.PRESS);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    handleAction(e, MouseAction.RELEASE);
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    Point point = e.getPoint();
                    int x = point.x;
                    int y = point.y;
                    if (x > ConstUtils.WORLD_WIDTH && y > ConstUtils.WORLD_HEIGHT) {
                        onAction(MouseAction.DRAG, null);
                    } else {
                        onAction(MouseAction.DRAG, Position.of(x, y));
                    }
                }
            };
            this.addMouseListener(mouseAdapter);
            this.addMouseMotionListener(mouseAdapter);
        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            drawManager.drawAll(g2d);
        }
    }
    private void onAction(MouseAction mouseAction, Position pos) {
        ProgramMode mode = State.get().getProgramState().getMode();
        Optional<ClickHandler> handler = clickHandlers.stream().filter(s -> s.isApplied(mode)).findFirst();
        handler.ifPresent(h -> {switch (mouseAction) {
            case CLICK -> h.click(pos);
            case DRAG -> h.drag(pos);
            case RELEASE -> h.release(pos);
            case PRESS -> h.press(pos);
        }});
    }

    private interface ClickHandler {
        boolean isApplied(ProgramMode mode);
        default void click(Position position) {}
        default void drag(Position position) {}
        default void release(Position position) {}
        default void press(Position position) {}
    }

    private class NodesClickHandler implements ClickHandler {

        @Override
        public boolean isApplied(ProgramMode mode) {
            return mode == ProgramMode.NODE;
        }

        @Override
        public void click(Position position) {
            graph.addNodeAt(position, false);
        }
    }

    private class EdgesClickHandler implements ClickHandler {
        private TempLine tempLine = null;
        @Override
        public boolean isApplied(ProgramMode mode) {
            return mode == ProgramMode.EDGE;
        }
        private boolean isActive() {
            return tempLine != null;
        }
        @Override
        public void drag(Position position) {
            if (position == null) {
                hideLine();
                return;
            }
            if (isActive()) {
                tempLine.setEndAt(position);
            }
        }

        private void showLine(Position initial) {
            tempLine = new TempLine(initial, initial);
            drawManager.onAdd(tempLine);
        }
        private void hideLine() {
            drawManager.onRemove(tempLine);
            tempLine = null;
        }

        @Override
        public void release(Position position) {
            if (!isActive()) {
                return;
            }
            graph.addEdge(tempLine.getStartAt(), tempLine.getEndAt(), false);
            hideLine();
        }

        @Override
        public void press(Position position) {
            Optional<Node> nodeAt = graph.getNodeAt(position);
            if (nodeAt.isEmpty()) {
                return;
            }
            Node startedWith = nodeAt.get();
            Position startPos = startedWith.getPosition();
            showLine(startPos);
        }
    }
}
