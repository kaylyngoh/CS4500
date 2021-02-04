package Model;

import java.util.Objects;

/**
 * Represents a position on the board, x = column and y = row.
 */
public class Pos2D {
  private int x;
  private int y;

  public Pos2D(int x, int y) {
    this.x = x;
    this.y = y;
    //this.x = checkPos(x);
    //this.y = checkPos(y);
  }

  /**
   * Copy constructor of a 2D position.
   *
   * @param v the position to copy
   */
  public Pos2D(Pos2D v) {
    this(v.x, v.y);
  }

  /**
   * Getter method to get the x, column in the board.
   *
   * @return the x position
   */
  public int getX() {
    return this.x;
  }

  /**
   * Getter method to get the y, row in the board.
   *
   * @return the y position
   */
  public int getY() {
    return this.y;
  }

  /**
   * Checks whether the given number is a positive number.
   *
   * @param pos x or y position
   * @return a non-negative integer when the given number is not negative. Otherwise, throw an
   * exception.
   */
  protected int checkPos(int pos) {
    if (pos >= 0) {
      return pos;
    } else {
      throw new IllegalArgumentException("The position can not be negative");
    }
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }
    if (!(a instanceof Pos2D)){
      return false;
    }
    Pos2D that = (Pos2D) a;
    return ((this.x == that.x) && (this.y == that.y));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }

}