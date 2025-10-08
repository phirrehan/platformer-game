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

public class GamePanel extends JPanel {

  private MouseInputs mouseInputs;
  private BufferedImage img, subImg;

  public GamePanel() {
    importImg();
    mouseInputs = new MouseInputs(this);
    setPanelSize();
    addKeyListener(new KeyboardInputs(this));
    addMouseListener(mouseInputs);
    addMouseMotionListener(mouseInputs);
  }

  private void importImg() {
    File file = new File("../res/player_sprites.png");
    try {
      img = ImageIO.read(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void setPanelSize() {
    Dimension size = new Dimension(1280, 800);
    setPreferredSize(size);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    subImg = img.getSubimage(1 * 64, 8 * 40, 64, 40);
    g.drawImage(subImg, 0, 0, 128, 80, null);
  }

}
