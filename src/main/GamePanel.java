package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import main.Game;

public class GamePanel extends JPanel {

  private Game game;
  private MouseInputs mouseInputs;

  public GamePanel(Game game) {
    this.game = game;
    mouseInputs = new MouseInputs(this);

    setPanelSize();
    addKeyListener(new KeyboardInputs(this));
    addMouseListener(mouseInputs);
    addMouseMotionListener(mouseInputs);
  }

  private void setPanelSize() {
    Dimension size = new Dimension(1280, 800);
    setPreferredSize(size);
  }

  public void updateGame() {

  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    game.render(g);
  }


  public Game getGame() {
    return game;
  }

}
