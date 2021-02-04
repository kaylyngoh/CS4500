import java.awt.*;
import java.awt.event.MouseListener;

import javax.swing.*;

public class View extends JFrame {

  private Panel panel;

  View(int sizeInPixel) {
    panel = new Panel(sizeInPixel);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel.setPreferredSize(new Dimension(2000, 2000));
    JScrollPane scroll = new JScrollPane(panel);
    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scroll.setBounds(0, 0, 0, 0);
    scroll.setPreferredSize(new Dimension(2000, 2000));
    this.add(scroll);
    this.pack();
  }

  public void makeVisible() {
    this.setVisible(true);
  }

  public boolean doesHexagonContainView(int x, int y) {
    return this.panel.doesHexagonContainPanel(x,y);
  }

  public void setMouseAdapter(MouseListener listener) {
    this.panel.addMouseListener(listener);
  }
}
