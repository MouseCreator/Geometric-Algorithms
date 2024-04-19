package mouse.project.ui;

import mouse.project.state.ConstUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class AppLoop implements Runnable {
    private Thread gameThread;
    private final Application app;
    private static final Logger logger = LogManager.getLogger(AppLoop.class);
    public AppLoop() {
        app = new DrawApplication();
    }

    public Component getGamePanel() {
        return app.core();
    }

    public void init() {
        app.init();
    }


    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void run() {
        double drawInterval = 1000.0 / ConstUtils.FPS;
        double nextDrawTime = System.currentTimeMillis() + drawInterval;
        while (gameThread != null) {

            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.currentTimeMillis();
                if (remainingTime > 0) {
                    Thread.sleep((long) remainingTime);
                }
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                logger.debug("Game loop interrupted.");
            }
        }
    }

    private void repaint() {
        app.draw();
    }

    public void update() {
        app.update();
    }
}
