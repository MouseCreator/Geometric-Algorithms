package mouse.project.ui.components.general;

import mouse.project.event.service.EventAddRegister;
import mouse.project.event.service.EventDeleteRegister;
import mouse.project.event.service.EventListener;
import mouse.project.event.service.Events;
import mouse.project.event.type.*;
import mouse.project.event.type.Event;
import mouse.project.state.ConstUtils;
import mouse.project.state.MouseAction;
import mouse.project.state.ProgramMode;
import mouse.project.state.State;
import mouse.project.ui.components.draw.DrawManager;
import mouse.project.ui.components.main.AppComponent;
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
    private final DrawManager drawManager;
    private ClickHandler clickHandler = new ClickHandler() {};
    private ClickHandler rightClickHandler = new ClickHandler() {};
    private List<GeneralEventHandler> eventHandlers;
    private final List<ClickHandler> clickHandlers;
    public PaintingPane() {
        this.drawPanel = new DrawPanel();
        drawManager = State.get().getProgramState().getDrawManager();
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
        }
    }

    private class LoadEventHandler implements GeneralEventHandler {

        @Override
        public boolean canHandle(Event event) {
            return event instanceof LoadEvent;
        }

        @Override
        public void handle(Event event) {
        }
    }
    private class BuildTreeHandler implements GeneralEventHandler {

        @Override
        public boolean canHandle(Event event) {
            return event instanceof BuildTreeEvent;
        }

        @Override
        public void handle(Event event) {

        }
    }


    private void onRemoveAll() {

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
}
