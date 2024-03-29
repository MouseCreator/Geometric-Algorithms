package mouse.project.ui.components.general;

import mouse.project.event.service.Events;
import mouse.project.event.type.BuildTreeEvent;
import mouse.project.event.type.LoadEvent;
import mouse.project.event.type.RemoveAllEvent;
import mouse.project.event.type.SaveEvent;
import mouse.project.state.ConstUtils;
import mouse.project.state.ProgramMode;
import mouse.project.state.State;
import mouse.project.ui.components.main.AppComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ButtonsPane implements AppComponent {
    private final JPanel panel;
    private final static Logger logger = LogManager.getLogger(ButtonsPane.class);
    public ButtonsPane() {
        this.panel = new ButtonsFlow();
    }

    private void onNodeButton() {
        logger.debug("Nodes pressed!");
        State.get().getProgramState().updateMode(ProgramMode.NODE);
    }
    private void onEdgeButton() {
        logger.debug("Edges pressed!");
        State.get().getProgramState().updateMode(ProgramMode.EDGE);
    }
    private void onTargetButton() {
        logger.debug("Target pressed!");
        State.get().getProgramState().updateMode(ProgramMode.TARGET);
        Events.generate(new BuildTreeEvent());
    }
    private void onEraseButton() {
        logger.debug("Erase pressed!");
        State.get().getProgramState().updateMode(ProgramMode.ERASE);
    }
    private void onSaveButton() {
        logger.debug("Save pressed!");
        resetState();
        Events.generate(new SaveEvent());
    }

    private void resetState() {
        btnGroup.clearSelection();
        State.get().getProgramState().updateMode(ProgramMode.IDLE);
    }

    private void onLoadButton() {
        logger.debug("Load pressed!");
        resetState();
        Events.generate(new LoadEvent());
    }
    private void onClearButton() {
        Events.generate(new RemoveAllEvent());
        logger.debug("Clear pressed!");
    }

    @Override
    public Component getComponent() {
        return panel;
    }
    private ButtonGroup btnGroup;
    private class ButtonsFlow extends JPanel {
        public ButtonsFlow() {
            btnGroup = new ButtonGroup();
            int buttonHeight = 50;
            int buttonCount = 7;
            int gap = 10;
            int spaceGap = 40;

            setPreferredSize(new Dimension(ConstUtils.BUTTON_PANE_WIDTH, ConstUtils.BUTTON_PANE_HEIGHT));
            setLayout(new FlowLayout());
            JPanel main = new JPanel();
            main.setBackground(ConstUtils.TRANSPARENT);
            main.setPreferredSize(new Dimension(ConstUtils.BUTTON_PANE_WIDTH, ConstUtils.BUTTON_PANE_HEIGHT));
            main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
            JPanel topSubpane = createTopSubpane();
            JPanel bottomSubpane = createBottomSubpane();
            main.add(topSubpane);
            JPanel space = createSpace(spaceGap, gap);
            main.add(space);
            main.add(bottomSubpane);
            int height = buttonCount * buttonHeight + (buttonCount - 1) * gap + spaceGap;
            main.setPreferredSize(new Dimension(ConstUtils.BUTTON_PANE_WIDTH - 30, height));
            add(main);
        }


        private JPanel createTopSubpane() {
            JPanel subpanel = new JPanel();
            subpanel.setBackground(ConstUtils.TRANSPARENT);
            subpanel.setLayout(new GridLayout(4, 1 ,10, 10));
            setBackground(ConstUtils.BACKGROUND_SECONDARY);

            JToggleButton nodesBtn = createToggleButton("Nodes", ButtonsPane.this::onNodeButton);
            JToggleButton edgesBtn = createToggleButton("Edges", ButtonsPane.this::onEdgeButton);
            JToggleButton targetBtn = createToggleButton("Target", ButtonsPane.this::onTargetButton);
            JToggleButton eraseBtn = createToggleButton("Erase", ButtonsPane.this::onEraseButton);


            btnGroup.add(nodesBtn);
            btnGroup.add(edgesBtn);
            btnGroup.add(targetBtn);
            btnGroup.add(eraseBtn);

            subpanel.add(nodesBtn);
            subpanel.add(edgesBtn);
            subpanel.add(targetBtn);
            subpanel.add(eraseBtn);
            return subpanel;
        }

        private JPanel createBottomSubpane() {
            JPanel subpanel = new JPanel();
            subpanel.setBackground(ConstUtils.TRANSPARENT);
            subpanel.setLayout(new GridLayout(3, 1 ,10, 10));
            setBackground(ConstUtils.BACKGROUND_SECONDARY);

            JButton saveBtn = createButton("Save", ButtonsPane.this::onSaveButton);
            JButton loadBtn = createButton("Load", ButtonsPane.this::onLoadButton);
            JButton clearBtn = createButton("Clear", ButtonsPane.this::onClearButton);

            subpanel.add(saveBtn);
            subpanel.add(loadBtn);
            subpanel.add(clearBtn);
            return subpanel;
        }

        private JToggleButton createToggleButton(String text, Runnable action) {
            JToggleButton button = new JToggleButton(text);
            formatButton(button, action);
            return button;
        }

        private void formatButton(AbstractButton button, Runnable action) {
            button.addActionListener(r -> action.run());
            button.setForeground(Color.BLACK);
            button.setBackground(Color.WHITE);
            Border line = new LineBorder(Color.ORANGE);
            Border margin = new EmptyBorder(15, 15, 15, 15);
            Border compound = new CompoundBorder(line, margin);
            button.setBorder(compound);
        }

        private JButton createButton(String text, Runnable action) {
            JButton button = new JButton(text);
            formatButton(button, action);
            return button;
        }


    }



    private static JPanel createSpace(int spaceGap, int gap) {
        JPanel space = new JPanel();
        space.setPreferredSize(new Dimension(0, spaceGap - gap));
        space.setBackground(ConstUtils.TRANSPARENT);
        return space;
    }

}
