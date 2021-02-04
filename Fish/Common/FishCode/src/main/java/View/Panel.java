package View;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import Model.Penguin;
import Model.Pieces;
import Model.ReadOnlyFishModel;
import Model.Tile;

/**
 * Represents the panel used to draw the View.Fish game.
 */
public class Panel extends JPanel {

  // constant
  public static final int penguinSize = 40;

  private ReadOnlyFishModel model;
  private final int fishHeight;
  private final int fishWidth;
  private final int tileSize;

  public Panel(ReadOnlyFishModel model) {
    this.model = model;
    this.fishHeight = this.model.getBoard().fishSize;
    this.fishWidth = fishHeight * 3;
    this.tileSize = this.model.getBoard().tileSize;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    setBackground(Color.WHITE);
    AffineTransform transform = g2d.getTransform();
    try {
      drawTiles(g2d);
    } catch (IOException e) {
      e.printStackTrace();
    }
    drawPenguins(g2d);
    g2d.setTransform(transform);
  }

  /**
   * Draws a penguin on the tile.
   *
   * @param g2d the graphics to add the penguin onto
   */
  private void drawPenguins(Graphics2D g2d) {
    for (Penguin p : this.model.getPenguins()) {
      // push to show all of penguin, determine the coloumn/row, (determine if push down is needed), align center
      int x = tileSize + (tileSize * 2 * p.getPosition().getX()) - (penguinSize / 2);
      int y = tileSize + (tileSize * 2 * p.getPosition().getY()) + (determinePushDownNeeded(p.getPosition().getY()) * tileSize) - (penguinSize / 2);
      g2d.setColor(p.determineColor());
      g2d.fillOval(x, y, penguinSize, penguinSize);
    }
  }

  /**
   * Draws the tiles in the board onto the panel.
   *
   * @param g the graphics to draw the tiles into
   */
  private void drawTiles(Graphics2D g) throws IOException {
    // row first, then column
    for (int i = 0; i < this.model.getBoard().get2DTiles().length; i++) {
      for (int j = 0; j < this.model.getBoard().get2DTiles()[0].length; j++) {
        Pieces currentPiece = this.model.getBoard().get2DTiles()[i][j];
        if (currentPiece instanceof Tile) {
          drawTile(g, i, j, currentPiece.getFishNum());
        }
      }
    }
  }

  /**
   * Draws a single tile onto the graphics, including the fish and penguin on it.
   *
   * @param g   the graphics to add the tile onto
   * @param row the row of this tile
   * @param col the column of this tile
   */
  protected void drawTile(Graphics2D g, int row, int col, int fishNum) throws IOException {
    Polygon tile = createHexagon(row, col);
    g.setColor(Color.orange);
    g.fillPolygon(tile);
    drawFish(g, row, col, fishNum);
  }


  /**
   * Draws fishes on the tile.
   *
   * @param g   the graphics to add the tile onto
   * @param row the row of this tile
   * @param col the column of this tile
   */
  private void drawFish(Graphics2D g, int row, int col, int fishNum) throws IOException {
    BufferedImage fishImg = ImageIO.read(getClass().getResourceAsStream("/fish.png"));
    int totalFishHeight = fishHeight * fishNum;
    int pushFishBoxToMiddle = tileSize - (totalFishHeight / 2);
    for (int i = 1; i <= fishNum; i++) {
      // push to show all of fish, determine the column, align center
      int x = tileSize + (tileSize * 2 * col) - (fishWidth / 2);
      // make box of fish, push box to middle, determine the row, determine if push down is needed, align center
      int y = (i * fishHeight) - (fishHeight / 2) + pushFishBoxToMiddle + (tileSize * 2 * row) + (determinePushDownNeeded(col) * tileSize) - (fishHeight / 2);
      g.drawImage(fishImg, x, y, fishWidth, fishHeight, null);
    }
  }

  /**
   * Creates a single hexagonal tile, depending on the row and column it is on.
   *
   * @param r the row of this tile
   * @param c the column of this tile
   * @return the hexagon
   */
  private Polygon createHexagon(int r, int c) {
    int pushDown = determinePushDownNeeded(c);
    Polygon polygon = new Polygon();
    for (int i = 0; i < 6; i++) {
      int x = (int) (tileSize + tileSize * Math.cos(i * 2 * Math.PI / 6)) + (tileSize * 2 * c);
      int y = (int) (tileSize + tileSize * Math.sin(i * 2 * Math.PI / 6)) + (tileSize * 2 * r) + (pushDown * tileSize);
      polygon.addPoint(x, y);
    }
    return polygon;
  }

  /**
   * Determines if the hexagon needs to be lower than its neighbor to create the honeycomb pattern.
   * If the column is an odd number, it would need to be pushed down.
   *
   * @param c the column of this tile
   * @return 1 if needs to be pushed down, 0 if not needed.
   */
  protected int determinePushDownNeeded(int c) {
    if (c % 2 == 1) {
      return 1;
    } else {
      return 0;
    }
  }

}