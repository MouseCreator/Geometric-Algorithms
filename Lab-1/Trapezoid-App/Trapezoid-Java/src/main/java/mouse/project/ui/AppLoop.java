package mouse.project.ui;

import mouse.project.state.ConstUtils;

import java.awt.*;

public class AppLoop implements Runnable {
    private Thread gameThread;
    private DrawPanel gamePanel = null;
    private final Application app;
    public AppLoop() {
        app = new DrawApplication();
    }

    public DrawPanel getGamePanel() {
        if (gamePanel == null) {
            throw new IllegalStateException("Game loop is not initialized!");
        }
        return gamePanel;
    }

    public void init() {
        initGamePanel();
        app.init();
    }

    private void initGamePanel() {
        gamePanel = new DrawPanel(this);
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
                return;
            }
        }
    }

    private void repaint() {
        gamePanel.repaint();
    }

    public void update() {
        app.update();
    }

    public void draw(Graphics2D g2d) {
        app.draw(g2d);
    }
}
