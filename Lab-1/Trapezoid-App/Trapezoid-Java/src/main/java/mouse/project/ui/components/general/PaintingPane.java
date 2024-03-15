package mouse.project.ui.components.general;

import mouse.project.state.ConstUtils;
import mouse.project.state.State;
import mouse.project.ui.components.draw.DrawManager;
import mouse.project.ui.components.graph.UIGraph;
import mouse.project.ui.components.main.AppComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PaintingPane implements AppComponent {
    private final DrawPanel drawPanel;
    private final UIGraph graph;
    private DrawManager drawManager;
    public PaintingPane() {
        this.drawPanel = new DrawPanel();
        graph = new UIGraph();
        drawManager = State.get().getProgramState().getDrawManager();
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
                    System.out.println(x + " " + y);
                    if (x > ConstUtils.WORLD_WIDTH && y > ConstUtils.WORLD_HEIGHT) {
                        return;
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
}
