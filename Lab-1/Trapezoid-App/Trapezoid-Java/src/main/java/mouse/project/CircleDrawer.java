package mouse.project;

import mouse.project.ui.DrawPanel;

import javax.swing.*;

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

}
