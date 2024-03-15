package mouse.project.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public class DrawFrame extends JFrame {

    private static final Logger logger = LogManager.getLogger(DrawFrame.class);

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
        logger.debug("Application started!");
    }
}
