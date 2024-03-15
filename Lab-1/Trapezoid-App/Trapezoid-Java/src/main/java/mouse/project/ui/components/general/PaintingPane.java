package mouse.project.ui.components.general;

import mouse.project.state.ConstUtils;
import mouse.project.ui.components.main.AppComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PaintingPane implements AppComponent {

    private final DrawPanel drawPanel;
    public PaintingPane() {
        this.drawPanel = new DrawPanel();
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

    private static class DrawPanel extends JPanel {
        private final List<Point> points = new ArrayList<>();

        public DrawPanel() {
            this.setPreferredSize(new Dimension(ConstUtils.WORLD_WIDTH, ConstUtils.WORLD_HEIGHT));
            this.setBackground(ConstUtils.BACKGROUND);
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    points.add(e.getPoint());
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(Color.RED);

            for (Point point : points) {
                g2d.fillOval(point.x - 5, point.y - 5, 10, 10);
            }
        }
    }
}
