package Model;

import java.awt.*;
import java.util.Objects;

/**
 * Represents the penguin avatar used by a player.
 */
public class Penguin {

  private final Colour color;
  private Pos2D position;

  public Penguin(Colour color, Pos2D position) {
    this.color = color;
    this.position = position;
  }

  /**
   * Copy constructor of a penguin.
   *
   * @param p the penguin to copy
   */
  public Penguin(Penguin p) {
    this(p.color, new Pos2D(p.position));
  }

  /**
   * Determines the color to be used to draw the penguin on the panel.
   *
   * @return the color
   */
  public Color determineColor() {
    switch (this.color) {
      case WHITE:
        return Color.WHITE;
      case RED:
        return Color.RED;
      case BLACK:
        return Color.BLACK;
      case BROWN:
        return new Color(130, 79, 35);
      default:
        throw new IllegalArgumentException("No other colors to choose from");
    }
  }

  /**
   * Getter method for the color of a penguin.
   */
  public Colour getColor() {
    return this.color;
  }

  /**
   * Getter method for the current position of the penguin.
   */
  public Pos2D getPosition() {
    return new Pos2D(this.position);
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }
    if (!(a instanceof Penguin)){
      return false;
    }
    Penguin that = (Penguin) a;
    return ((this.color.equals(that.color)) && (this.position.equals(that.position)));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.color, this.position);
  }

  /**
   * Updates the position of this penguin to the given position after a move.
   */
  public void updatePos(Pos2D position) {
    this.position = position;
  }

}