package View;

import java.awt.*;

import javax.swing.*;

import Model.ReadOnlyFishModel;

/**
 * Represents the view/frame for the View.Fish game.
 */
public class View extends JFrame {
  private Panel panel;

  public View(ReadOnlyFishModel model) {
    panel = new Panel(model);
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

  /**
   * Makes this window visible.
   */
  protected void makeVisible() {
    this.setVisible(true);
  }

}