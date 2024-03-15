package mouse.project.ui;

import mouse.project.state.ConstUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DrawPanel extends JPanel {
    private final AppLoop loop;
    private double scaleX = 1.0;
    private double scaleY = 1.0;

    public DrawPanel(AppLoop loop) {
        super();
        this.setPreferredSize(new Dimension(ConstUtils.WORLD_WIDTH, ConstUtils.WORLD_HEIGHT));
        this.setBackground(ConstUtils.BACKGROUND);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scaleX = (double) getWidth() / ConstUtils.WORLD_WIDTH;
                scaleY = (double) getHeight() / ConstUtils.WORLD_HEIGHT;
            }
        });
        this.loop = loop;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(scaleX, scaleY);
        loop.draw(g2d);
        g2d.dispose();
    }
}
