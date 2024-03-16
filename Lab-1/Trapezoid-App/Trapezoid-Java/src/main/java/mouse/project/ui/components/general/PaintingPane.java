package mouse.project.ui.components.general;

import mouse.project.state.ConstUtils;
import mouse.project.state.MouseAction;
import mouse.project.state.ProgramMode;
import mouse.project.state.State;
import mouse.project.ui.components.draw.DrawManager;
import mouse.project.ui.components.graph.*;
import mouse.project.ui.components.main.AppComponent;
import mouse.project.utils.MathUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaintingPane implements AppComponent, ProgramModeListener {
    private final DrawPanel drawPanel;
    private final UIGraph graph;
    private final DrawManager drawManager;
    private ClickHandler clickHandler = new ClickHandler() {};
    private final List<ClickHandler> clickHandlers;
    public PaintingPane() {
        this.drawPanel = new DrawPanel();
        graph = new UIGraph();
        drawManager = State.get().getProgramState().getDrawManager();
        clickHandlers = createClickHandlers();
        State.get().getProgramState().registerListener(this);
    }

    private List<ClickHandler> createClickHandlers() {
        List<ClickHandler> handlers = new ArrayList<>();
        handlers.add(new NodesClickHandler());
        handlers.add(new EdgesClickHandler());
        handlers.add(new TargetHandler());
        handlers.add(new EraseClickHandler());
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
    public void onProgramModeChange(ProgramMode programMode) {
        Optional<ClickHandler> handler = clickHandlers.stream().filter(s -> s.isApplied(programMode)).findFirst();
        clickHandler.onDisable();
        clickHandler = handler.orElse(new ClickHandler() {});
        clickHandler.onEnable();
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
                    if (outOfBounds(x, y)) {
                        return;
                    }
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        onAction(mouseAction, Position.of(x, y));
                    }
                }

                private static boolean outOfBounds(int x, int y) {
                    return x > ConstUtils.WORLD_WIDTH || y > ConstUtils.WORLD_HEIGHT;
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
                    if (outOfBounds(x, y)) {
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

        switch (mouseAction) {
            case CLICK -> clickHandler.click(pos);
            case DRAG -> clickHandler.drag(pos);
            case RELEASE -> clickHandler.release(pos);
            case PRESS -> clickHandler.press(pos);
        }
    }

    private interface ClickHandler {
        default boolean isApplied(ProgramMode mode) {return false;}
        default void click(Position position) {}
        default void drag(Position position) {}
        default void release(Position position) {}
        default void press(Position position) {}
        default void onEnable() {}
        default void onDisable() {}
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

    private class TargetHandler implements ClickHandler {
        private TargetPoint targetPoint = null;

        @Override
        public void onEnable() {
            hide();
        }

        @Override
        public void onDisable() {
            hide();
        }

        @Override
        public boolean isApplied(ProgramMode mode) {
            return mode == ProgramMode.TARGET;
        }

        private void hide() {
            if (targetPoint != null) {
                drawManager.onRemove(targetPoint);
                targetPoint = null;
            }
        }

        @Override
        public void click(Position position) {
            position = adjustPosition(position);
            if (targetPoint == null) {
                targetPoint = new TargetPoint(position);
                drawManager.onAdd(targetPoint);
            } else {
                targetPoint.moveTo(position);
            }

        }

        private Position adjustPosition(Position position) {
            Optional<Node> nodeAt = graph.getNodeAt(position);
            if (nodeAt.isPresent()) {
                position = nodeAt.get().getPosition();
            } else {
                Optional<Edge> edgeAt = graph.getEdgeAt(position);
                if (edgeAt.isPresent()) {
                    Edge edge = edgeAt.get();
                    position = MathUtils.movePositionToEdge(edge, position);
                }
            }
            return position;
        }

    }

    private class EraseClickHandler implements ClickHandler {

        @Override
        public boolean isApplied(ProgramMode mode) {
            return mode == ProgramMode.ERASE;
        }

        @Override
        public void click(Position position) {
            boolean removed = graph.removeNodeAt(position);
            if (removed) {
                return;
            }
            graph.removeEdgeAt(position);
        }
    }
}
