package main;

import java.awt.Graphics;

import entities.Player;

public class Game implements Runnable {

  private GameWindow gameWindow;
  private GamePanel gamePanel;
  private Thread gameThread;
  private final int FPS_SET = 120;
  private final int UPS_SET = 200;

  private Player player;

  public Game() {
    initClasses();
    gamePanel = new GamePanel(this);
    gameWindow = new GameWindow(gamePanel);
    gamePanel.requestFocus();
    startGameLoop();
  }

  public void initClasses() {
    player = new Player(200.f, 200.f);
  }

  private void startGameLoop() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  public void update() {
    player.update();
  }

  public void render(Graphics g) {
    player.render(g);
  }

  @Override
  public void run() {
    double timePerFrame = 1000000000.0 / FPS_SET; // units are nano seconds
    double timePerUpdate = 1000000000.0 / UPS_SET;

    long previoiusTime = System.nanoTime();

    int frames = 0;
    int updates = 0;
    long lastFPSCheck = System.currentTimeMillis();

    double deltaU = 0;
    double deltaF = 0;

    // Game Loop
    while (true) {

      long currentTime = System.nanoTime();

      deltaU += (currentTime - previoiusTime) / timePerUpdate;
      deltaF += (currentTime - previoiusTime) / timePerFrame;
      previoiusTime = currentTime;

      if (deltaU >= 1) {
        update();
        updates++;
        deltaU--;
      }

      if (deltaF >= 1) {
        gamePanel.repaint();
        frames++;
        deltaF--;
      }

      if (System.currentTimeMillis() - lastFPSCheck >= 1000) {
        lastFPSCheck = System.currentTimeMillis();
        System.out.println("FPS: " + frames + " | UPS: " + updates);
        frames = 0;
        updates = 0;
      }

    }

  }

  public void windowFocusLost() {
    player.resetDirBooleans();
  }

  public Player getPlayer() {
    return player;
  }

}
