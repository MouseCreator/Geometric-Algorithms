package mouse.project.ui;

import javax.swing.*;

public class DrawFrame extends JFrame {

    public DrawFrame() {
    }
    public void startApp() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setTitle("Trapezoid Method");
        AppLoop loop = new AppLoop();
        loop.init();
        DrawPanel gamePanel =  loop.getGamePanel();
        this.add(gamePanel);

        pack();

        this.setLocationRelativeTo(null);
        this.setVisible(true);
        loop.startGameThread();
    }
}
