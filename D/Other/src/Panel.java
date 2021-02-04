import java.awt.*;
import java.awt.geom.AffineTransform;

import javax.swing.*;

public class Panel extends JPanel {

  private int sizeInPixel;
  private Polygon hexagon;

  Panel(int sizeInPixel) {
    this.sizeInPixel = sizeInPixel;
    this.hexagon = createHexagon();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    setBackground(Color.WHITE);
    AffineTransform transform = g2d.getTransform();
    g2d.setColor(Color.red);
    g2d.drawPolygon(hexagon);
    g2d.setTransform(transform);
  }

  private Polygon createHexagon() {
    Polygon polygon = new Polygon();
    for (int i = 0 ; i < 6 ; i++) {
      int x = (int) (1000 + this.sizeInPixel
              * Math.cos(i * 2 * Math.PI / 6D));
      int y = (int) (1000 + this.sizeInPixel
              * Math.sin(i * 2 * Math.PI / 6D));
      polygon.addPoint(x, y);
    }
    return polygon;
  }

  public boolean doesHexagonContainPanel(int x, int y) {
    return hexagon.contains(x, y);
  }
}
