package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel {

  private MouseInputs mouseInputs;
  private int x = 200;
  private int y = 200;
  private int xDir = 2;
  private int yDir = 2;
  private Color color;
  private Random random;

  public GamePanel() {
    random = new Random();
    mouseInputs = new MouseInputs(this);
    addKeyListener(new KeyboardInputs(this));
    addMouseListener(mouseInputs);
    addMouseMotionListener(mouseInputs);
  }

  public void setRectPos(int x, int y) {
    this.x = x;
    this.y = y;
  }

  private void updateRectangle() {
    x += xDir;
    if (x > 400 || x < 0) {
      xDir *= -1;
      color = getRndColor();
    }

    y += yDir;
    if (y > 400 || y < 0) {
      yDir *= -1;
      color = getRndColor();
    }
  }

  private Color getRndColor() {
    int r = random.nextInt(256);
    int g = random.nextInt(256);
    int b = random.nextInt(256);

    return new Color(r, g, b);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    updateRectangle();
    g.setColor(color);
    g.fillRect(x, y, 200, 50);
  }

}
