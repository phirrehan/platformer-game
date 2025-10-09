package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;

public class GamePanel extends JPanel {

  private MouseInputs mouseInputs;
  private int xPos = 100, yPos = 100;
  private BufferedImage img;
  private BufferedImage[][] animations;
  private int aniTick, aniIndex, aniSpeed = 15; // 120 / 15 = 8 animations per second
  private int playerAction = IDLE;
  private int playerDir = -1;
  private boolean moving;

  public GamePanel() {
    importImg();
    loadAnimations();
    mouseInputs = new MouseInputs(this);
    setPanelSize();
    addKeyListener(new KeyboardInputs(this));
    addMouseListener(mouseInputs);
    addMouseMotionListener(mouseInputs);
  }

  public void setXPos(int x) {
    this.xPos = x;
  }

  public void setYPos(int y) {
    this.yPos = y;
  }

  public void setDirection(int direction) {
    this.playerDir = direction;
    setMoving(true);
  }

  public void setMoving(boolean moving) {
    this.moving = moving;
  }

  private void importImg() {
    File file = new File("../assets/player_sprites.png");
    try {
      img = ImageIO.read(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadAnimations() {
    animations = new BufferedImage[9][6];
    for (int i = 0; i < animations.length; i++)
      for (int j = 0; j < getSpriteAmount(i); j++)
        animations[i][j] = img.getSubimage(64 * j, 40 * i, 64, 40);
  }

  private void setPanelSize() {
    Dimension size = new Dimension(1280, 800);
    setPreferredSize(size);
  }

  private void updateAnimationTick() {
    aniTick++;
    if (aniTick == aniSpeed) {
      aniTick = 0;
      aniIndex++;
      if (aniIndex >= getSpriteAmount(playerAction))
        aniIndex = 0;
    }
  }

  private void setAnimation() {
    if (moving)
      playerAction = RUNNING;
    else
      playerAction = IDLE;
  }

  private void updatePos() {
    if (moving) {
      switch (playerDir) {
        case LEFT:
          xPos += -5;
          break;
        case UP:
          yPos += -5;
          break;
        case RIGHT:
          xPos += 5;
          break;
        case DOWN:
          yPos += 5;
          break;
      }
    }
  }

  public void updateGame() {
    updateAnimationTick();
    setAnimation();
    updatePos();
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.drawImage(animations[playerAction][aniIndex], xPos, yPos, 64 * 4, 40 * 4, null);
  }

}
