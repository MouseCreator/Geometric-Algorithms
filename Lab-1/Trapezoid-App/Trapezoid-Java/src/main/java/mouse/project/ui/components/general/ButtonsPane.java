package mouse.project.ui.components.general;

import mouse.project.state.ConstUtils;
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
    }
    private void onEdgeButton() {
        logger.debug("Edges pressed!");
    }
    private void onTargetButton() {
        logger.debug("Target pressed!");
    }
    private void onSaveButton() {
        logger.debug("Save pressed!");
    }
    private void onLoadButton() {
        logger.debug("Load pressed!");
    }
    private void onClearButton() {
        logger.debug("Clear pressed!");
    }

    @Override
    public Component getComponent() {
        return panel;
    }

    private class ButtonsFlow extends JPanel {

        public ButtonsFlow() {
            setPreferredSize(new Dimension(ConstUtils.BUTTON_PANE_WIDTH, ConstUtils.BUTTON_PANE_HEIGHT));
            setLayout(new FlowLayout());
            JPanel main = new JPanel();
            main.setBackground(ConstUtils.TRANSPARENT);
            main.setPreferredSize(new Dimension(ConstUtils.BUTTON_PANE_WIDTH, ConstUtils.BUTTON_PANE_HEIGHT));
            main.setLayout(new GridLayout(2,1, 10, 10));
            JPanel topSubpane = createTopSubpane();
            JPanel bottomSubpane = createBottomSubpane();
            main.add(wrapped(topSubpane));
            main.add(wrapped(bottomSubpane));
            add(main);
        }

        private JPanel wrapped(JPanel pan) {
            JPanel jPanel = new JPanel();
            jPanel.add(pan);
            return jPanel;
        }

        private JPanel createTopSubpane() {
            JPanel subpanel = new JPanel();
            subpanel.setBackground(ConstUtils.TRANSPARENT);
            subpanel.setLayout(new GridLayout(3, 1 ,10, 10));
            setBackground(ConstUtils.BACKGROUND_SECONDARY);

            JToggleButton nodesBtn = createToggleButton("Nodes", ButtonsPane.this::onNodeButton);
            JToggleButton edgesBtn = createToggleButton("Edges", ButtonsPane.this::onEdgeButton);
            JToggleButton targetBtn = createToggleButton("Target", ButtonsPane.this::onTargetButton);

            ButtonGroup btnGroup = new ButtonGroup();
            btnGroup.add(nodesBtn);
            btnGroup.add(edgesBtn);
            btnGroup.add(targetBtn);

            subpanel.add(nodesBtn);
            subpanel.add(edgesBtn);
            subpanel.add(targetBtn);
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
            button.setSize(new Dimension(200, 50));
        }

        private JButton createButton(String text, Runnable action) {
            JButton button = new JButton(text);
            formatButton(button, action);
            return button;
        }


    }

}
