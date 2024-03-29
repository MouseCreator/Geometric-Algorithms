package mouse.project.ui.components.general;

import mouse.project.algorithm.Algorithm;
import mouse.project.algorithm.AlgorithmInvoke;
import mouse.project.event.service.EventAddRegister;
import mouse.project.event.service.EventDeleteRegister;
import mouse.project.event.service.EventListener;
import mouse.project.event.service.Events;
import mouse.project.event.type.*;
import mouse.project.event.type.Event;
import mouse.project.saver.SaveLoad;
import mouse.project.state.ConstUtils;
import mouse.project.state.MouseAction;
import mouse.project.state.ProgramMode;
import mouse.project.state.State;
import mouse.project.ui.components.draw.DrawManager;
import mouse.project.ui.components.graph.*;
import mouse.project.ui.components.main.AppComponent;
import mouse.project.utils.math.MathUtils;
import mouse.project.utils.math.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaintingPane implements AppComponent, ProgramModeListener, EventListener {
    private final DrawPanel drawPanel;
    private final UIGraph graph;
    private final DrawManager drawManager;
    private ClickHandler clickHandler = new ClickHandler() {};
    private final ClickHandler rightClickHandler = new RightClickHandler() {};
    private final Algorithm algorithm;
    private List<GeneralEventHandler> eventHandlers;
    private final List<ClickHandler> clickHandlers;
    public PaintingPane() {
        this.drawPanel = new DrawPanel();
        graph = new UIGraph();
        drawManager = State.get().getProgramState().getDrawManager();
        algorithm = new AlgorithmInvoke(drawManager);
        clickHandlers = createClickHandlers();
        State.get().getProgramState().registerListener(this);
        Events.register(this);
        createEventHandlers();
    }

    private void createEventHandlers() {
        eventHandlers = new ArrayList<>();
        eventHandlers.add(new LoadEventHandler());
        eventHandlers.add(new SaveEventHandler());
        eventHandlers.add(new BuildTreeHandler());
        eventHandlers.add(new RemoveEventHandler());
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

    @Override
    public void onEvent(Event event) {
       for (GeneralEventHandler generalEventHandler : eventHandlers) {
           if (generalEventHandler.canHandle(event)) {
               generalEventHandler.handle(event);
           }
       }
    }

    private interface GeneralEventHandler {
        boolean canHandle(Event event);
        void handle(Event event);
    }

    private class RemoveEventHandler implements GeneralEventHandler {

        @Override
        public boolean canHandle(Event event) {
            return event instanceof RemoveAllEvent;
        }

        @Override
        public void handle(Event event) {

            onRemoveAll();
        }
    }
    private class SaveEventHandler implements GeneralEventHandler {

        @Override
        public boolean canHandle(Event event) {
            return event instanceof SaveEvent;
        }

        @Override
        public void handle(Event event) {
            SaveLoad.save(graph);
        }
    }

    private class LoadEventHandler implements GeneralEventHandler {

        @Override
        public boolean canHandle(Event event) {
            return event instanceof LoadEvent;
        }

        @Override
        public void handle(Event event) {
            algorithm.clear();
            SaveLoad.load(graph);
        }
    }
    private class BuildTreeHandler implements GeneralEventHandler {

        @Override
        public boolean canHandle(Event event) {
            return event instanceof BuildTreeEvent;
        }

        @Override
        public void handle(Event event) {
            algorithm.build(graph);
        }
    }


    private void onRemoveAll() {
        graph.removeAll();
        algorithm.clear();
    }

    @Override
    public void register(EventAddRegister eventAddRegister) {
        eventAddRegister.register(this, RemoveAllEvent.class);
        eventAddRegister.register(this, SaveEvent.class);
        eventAddRegister.register(this, LoadEvent.class);
        eventAddRegister.register(this, BuildTreeEvent.class);
    }

    @Override
    public void unregister(EventDeleteRegister eventDeleteRegister) {
        eventDeleteRegister.unregister(this);
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
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        onLeftClickAction(mouseAction, Position.of(x, y));
                    }
                    else if (SwingUtilities.isRightMouseButton(e)) {
                        onRightClickAction(mouseAction, Position.of(x, y));
                    }
                }

                private static boolean outOfBounds(int x, int y) {
                    return x > ConstUtils.WORLD_WIDTH || y > ConstUtils.WORLD_HEIGHT;
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
                    handleDragAction(e, x, y);
                }

                private void handleDragAction(MouseEvent e, int x, int y) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        if (outOfBounds(x, y)) {
                            onLeftClickAction(MouseAction.DRAG, null);
                        } else {
                            onLeftClickAction(MouseAction.DRAG, Position.of(x, y));
                        }
                    }
                    else if (SwingUtilities.isRightMouseButton(e)) {
                        if (outOfBounds(x, y)) {
                            onRightClickAction(MouseAction.DRAG, null);
                        } else {
                            onRightClickAction(MouseAction.DRAG, Position.of(x, y));
                        }
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

    private void onRightClickAction(MouseAction mouseAction, Position of) {
        switch (mouseAction) {
            case PRESS -> rightClickHandler.press(of);
            case DRAG -> rightClickHandler.drag(of);
            case RELEASE -> rightClickHandler.release(of);
        }
    }

    private void onLeftClickAction(MouseAction mouseAction, Position pos) {

        switch (mouseAction) {
            case PRESS -> clickHandler.press(pos);
            case DRAG -> clickHandler.drag(pos);
            case RELEASE -> clickHandler.release(pos);
        }
    }

    private interface ClickHandler {
        default boolean isApplied(ProgramMode mode) {return false;}
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
        public void press(Position position) {
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
            algorithm.clear();
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
        public void press(Position position) {
            position = adjustPosition(position);
            if (targetPoint == null) {
                targetPoint = new TargetPoint(position);
                drawManager.onAdd(targetPoint);
            } else {
                targetPoint.moveTo(position);
            }
            algorithm.find(targetPoint.position());

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
        public void press(Position position) {
            boolean removed = graph.removeNodeAt(position);
            if (removed) {
                return;
            }
            graph.removeEdgeAt(position);
        }
    }

    private class RightClickHandler implements ClickHandler {

        @Override
        public boolean isApplied(ProgramMode mode) {
            return true;
        }
        private Node currentNode = null;
        private Position startPosition = null;
        @Override
        public void press(Position position) {
            Optional<Node> nodeAt = graph.getNodeAt(position);
            if (nodeAt.isEmpty()) {
                return;
            }
            startPosition = position;
            currentNode = nodeAt.get();
        }

        @Override
        public void drag(Position position) {
            if (currentNode == null) {
                return;
            }
            if (position == null) {
                reset();
                return;
            }
            currentNode.moveTo(position);
        }

        private void reset() {
            currentNode.moveTo(startPosition);
            currentNode = null;
            startPosition = null;
        }

        @Override
        public void release(Position position) {
            currentNode = null;
            startPosition = null;
        }
    }
}
