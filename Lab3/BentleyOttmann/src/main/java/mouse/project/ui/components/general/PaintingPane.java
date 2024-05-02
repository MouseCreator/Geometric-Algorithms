package mouse.project.ui.components.general;

import mouse.project.algorithm.BOAlgorithm;
import mouse.project.event.service.EventAddRegister;
import mouse.project.event.service.EventDeleteRegister;
import mouse.project.event.service.EventListener;
import mouse.project.event.service.Events;
import mouse.project.event.type.*;
import mouse.project.event.type.Event;
import mouse.project.graphics.GraphicsChangeListener;
import mouse.project.graphics.GraphicsChangeListenerImpl;
import mouse.project.saver.SaveLoad;
import mouse.project.state.ConstUtils;
import mouse.project.state.MouseAction;
import mouse.project.state.ProgramMode;
import mouse.project.state.State;
import mouse.project.ui.components.draw.DrawManager;
import mouse.project.ui.components.main.AppComponent;
import mouse.project.ui.components.point.Segment;
import mouse.project.ui.components.point.SegmentEnd;
import mouse.project.ui.components.point.SegmentEndRecord;
import mouse.project.ui.components.point.Segments;
import mouse.project.math.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaintingPane implements AppComponent, ProgramModeListener, EventListener {
    private final DrawPanel drawPanel;
    private final DrawManager drawManager;
    private ClickHandler clickHandler = new ClickHandler() {};
    private final ClickHandler rightClickHandler = new RightClickHandler();
    private final Segments segments;
    private List<GeneralEventHandler> eventHandlers;
    private final BOAlgorithm algorithm;
    private final List<ClickHandler> clickHandlers;
    public PaintingPane() {
        this.drawPanel = new DrawPanel();
        drawManager = State.get().getProgramState().getDrawManager();
        clickHandlers = createClickHandlers();
        segments = new Segments(drawManager);
        GraphicsChangeListener graphicsChangeListener = new GraphicsChangeListenerImpl(drawManager);
        State.get().getProgramState().registerListener(this);
        Events.register(this);
        algorithm = new BOAlgorithm(graphicsChangeListener);
        createEventHandlers();
    }

    private void createEventHandlers() {
        eventHandlers = new ArrayList<>();
        eventHandlers.add(new LoadEventHandler());
        eventHandlers.add(new SaveEventHandler());
        eventHandlers.add(new RemoveEventHandler());
    }

    private List<ClickHandler> createClickHandlers() {
        List<ClickHandler> handlers = new ArrayList<>();
        handlers.add(new EraseClickHandler());
        handlers.add(new SegmentsClickHandler());
        handlers.add(new IntersectionsClickHandler());
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
            SaveLoad.save(segments);
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
            SaveLoad.load(segments);
        }
    }


    private void onRemoveAll() {
        segments.clear();
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
        default void press(Position position) {}
        default void drag(Position position) {}
        default void release(Position position) {}
        default void onEnable() {}
        default void onDisable() {}
    }

    public class RightClickHandler implements ClickHandler {

        private SegmentEnd activeEnd = null;
        private Segment ofSegment = null;
        private Position startPosition = null;

        private boolean isActive() {
            return activeEnd != null;
        }
        @Override
        public boolean isApplied(ProgramMode mode) {
            return true;
        }
        @Override
        public void press(Position position) {
            Optional<SegmentEndRecord> endAt = segments.getEndAt(position);
            endAt.ifPresent(segmentEnd -> {
                activeEnd = segmentEnd.end();
                ofSegment = segmentEnd.segment();
                startPosition = position;
            });
        }
        @Override
        public void drag(Position position) {
            if (!isActive()) {
                return;
            }
            if (position == null) {
                activeEnd.setPosition(startPosition);
                hide();
            } else {
                activeEnd.setPosition(position);
            }
        }
        @Override
        public void release(Position position) {
            if (position == null || ofSegment.length() < ConstUtils.MIN_SEGMENT_LENGTH) {
                activeEnd.setPosition(startPosition);
            } else {
                activeEnd.setPosition(position);
            }
            hide();
        }

        private void hide() {
            activeEnd = null;
            startPosition = null;
            ofSegment = null;
        }


    }

    public class SegmentsClickHandler implements ClickHandler {
        private Segment currentSegment = null;
        private SegmentEnd fromEnd = null;
        private SegmentEnd toEnd = null;
        private boolean active;
        @Override
        public boolean isApplied(ProgramMode mode) {
            return mode == ProgramMode.SEGMENTS;
        }
        @Override
        public void press(Position position) {
            if (active) {
                return;
            }
            active = true;
            currentSegment = new Segment();
            currentSegment.setId(segments.getIdGenerator().generate());
            fromEnd = new SegmentEnd(position);
            toEnd = new SegmentEnd(position);
            currentSegment.setFrom(fromEnd);
            currentSegment.setTo(toEnd);
            drawManager.onAdd(currentSegment);
        }
        @Override
        public void drag(Position position) {
            if (!active) {
                return;
            }
            if (position == null) {
                hide();
                removeFromGfx();
                return;
            }
            toEnd.setPosition(position);
        }

        private void hide() {
            active = false;
            fromEnd = null;
            toEnd = null;
            if (currentSegment != null)
                segments.getIdGenerator().free(currentSegment.getId());
            currentSegment = null;
        }
        private void saveAndHide() {
            active = false;
            fromEnd = null;
            toEnd = null;
            currentSegment = null;
        }

        private void removeFromGfx() {
            if (currentSegment != null) {
                drawManager.onRemove(currentSegment);
                currentSegment = null;
            }
        }

        @Override
        public void release(Position position) {
            if (currentSegment.length() < ConstUtils.MIN_SEGMENT_LENGTH) {
                hide();
                removeFromGfx();
                return;
            }
            segments.addSegment(currentSegment, false);
            saveAndHide();
        }

        @Override
        public void onEnable() {
            hide();
        }

        @Override
        public void onDisable() {
            hide();
        }
    }

    public class IntersectionsClickHandler implements ClickHandler {
        @Override
        public boolean isApplied(ProgramMode mode) {
            return mode == ProgramMode.FIND_INTERSECTIONS;
        }
        @Override
        public void onEnable() {
            algorithm.apply(segments);
        }

        @Override
        public void onDisable() {
            algorithm.clear();
        }
    }

    public class EraseClickHandler implements ClickHandler {
        @Override
        public boolean isApplied(ProgramMode mode) {
            return mode == ProgramMode.ERASE;
        }

        @Override
        public void press(Position position) {
            segments.deleteSegmentsAt(position);
        }
    }


}
