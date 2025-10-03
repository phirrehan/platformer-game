package main;

public class Game implements Runnable {

  private GameWindow gameWindow;
  private GamePanel gamePanel;
  private Thread gameThread;
  private final int FPS_SET = 120;

  public Game() {
    gamePanel = new GamePanel();
    gameWindow = new GameWindow(gamePanel);
    gamePanel.requestFocus();
    startGameLoop();
  }

  private void startGameLoop() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  @Override
  public void run() {
    double timePerFrame = 1000000000.0 / FPS_SET; // units are nano seconds
    long lastFrame = System.nanoTime();
    long now = System.nanoTime();

    int frames = 0;
    long lastFPSCheck = System.currentTimeMillis();

    // Game Loop
    while (true) {

      now = System.nanoTime();
      if (now - lastFrame >= timePerFrame) {
        lastFrame = now;
        gamePanel.repaint();
        frames++;
      }

      if (System.currentTimeMillis() - lastFPSCheck >= 1000) {
        lastFPSCheck = System.currentTimeMillis();
        System.out.println("FPS: " + frames);
        frames = 0;
      }

    }

  }

}
