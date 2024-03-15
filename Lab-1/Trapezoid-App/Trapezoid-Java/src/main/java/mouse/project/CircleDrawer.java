package mouse.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class CircleDrawer extends JFrame {
    public CircleDrawer() {

    }

    public void initialize() {
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.add(new DrawPanel());
    }


    static class DrawPanel extends JPanel {
        private final List<Point> points = new ArrayList<>();

        public DrawPanel() {
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    points.add(e.getPoint());
                    repaint();
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
