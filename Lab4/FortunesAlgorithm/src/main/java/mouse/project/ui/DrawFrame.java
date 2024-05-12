package mouse.project.ui;

import mouse.project.state.ConstUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class DrawFrame extends JFrame {

    private static final Logger logger = LogManager.getLogger(DrawFrame.class);

    public DrawFrame() {
    }
    public void startApp() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setTitle("Voronoi Diagram");
        this.setSize(new Dimension(ConstUtils.WINDOW_WIDTH, ConstUtils.WINDOW_HEIGHT));
        this.setResizable(false);
        AppLoop loop = new AppLoop();
        loop.init();
        Component gamePanel =  loop.getGamePanel();
        this.add(gamePanel);

        pack();

        this.setLocationRelativeTo(null);
        this.setVisible(true);
        loop.startGameThread();
        logger.debug("Application started!");
    }
}
