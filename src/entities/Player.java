package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;

public class Player extends Entity {

  private BufferedImage[][] animations;
  private int aniTick, aniIndex, aniSpeed = 15; // 120 / 15 = 8 animations per second
  private int playerAction = IDLE;
  private boolean moving, attacking;
  private boolean left, up, right, down;
  private float playerSpeed = 1.f;

  public Player(float x, float y) {
    super(x, y);
    loadAnimations();
  }

  public void update() {
    updatePos();
    updateAnimationTick();
    setAnimation();
  }

  public void render(Graphics g) {
    g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE),
        null);
  }

  private void loadAnimations() {
    BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);

    animations = new BufferedImage[9][6];
    for (int i = 0; i < animations.length; i++)
      for (int j = 0; j < getSpriteAmount(i); j++)
        animations[i][j] = img.getSubimage(64 * j, 40 * i, 64, 40);
  }

  private void updateAnimationTick() {
    aniTick++;
    if (aniTick == aniSpeed) {
      aniTick = 0;
      aniIndex++;
      if (aniIndex >= getSpriteAmount(playerAction)) {
        aniIndex = 0;
        attacking = false;
      }
    }
  }

  private void setAnimation() {
    int startAni = playerAction;

    if (moving)
      playerAction = RUNNING;
    else
      playerAction = IDLE;

    if (attacking) {
      playerAction = ATTACK_1;
    }

    if (startAni != playerAction)
      resetAniTick();
  }

  public void resetAniTick() {
    aniTick = 0;
    aniIndex = 0;
  }

  private void updatePos() {
    moving = false;

    if (left && !right) {
      x -= playerSpeed;
      moving = true;
    } else if (!left && right) {
      x += playerSpeed;
      moving = true;
    }

    if (up && !down) {
      y -= playerSpeed;
      moving = true;
    } else if (down && !up) {
      y += playerSpeed;
      moving = true;
    }
  }

  public void resetDirBooleans() {
    left = false;
    up = false;
    right = false;
    down = false;
  }

  public void setAttacking(boolean attacking) {
    this.attacking = attacking;
  }

  public boolean isLeft() {
    return left;
  }

  public void setLeft(boolean left) {
    this.left = left;
  }

  public boolean isUp() {
    return up;
  }

  public void setUp(boolean up) {
    this.up = up;
  }

  public boolean isRight() {
    return right;
  }

  public void setRight(boolean right) {
    this.right = right;
  }

  public boolean isDown() {
    return down;
  }

  public void setDown(boolean down) {
    this.down = down;
  }

}
