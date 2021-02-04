package Model;

/**
 * Represents a single piece on the board.
 */
public abstract class Pieces {

  private int fishNum;

  // Constants
  public static final int maxFishNum = 5;


  /**
   * Creates a piece on the board with the amount of fish it has.
   *
   * @param fishNum the number of fish on this piece.
   */
  public Pieces(int fishNum) {
    this.fishNum = checkFishNum(fishNum);
  }

  /**
   * Gets the number of fishes found in this tile.
   *
   * @return the number of fish on this tile
   */
  public int getFishNum() {
    return this.fishNum;
  }

  /**
   * Makes a copy of this piece.
   *
   */
  public abstract Pieces replicatePiece();

  /**
   * Checks if the given number of fish is not a negative number and less than or equals to
   * maxFishNum.
   *
   * @param fishNum the given number of fish
   * @return the number of fish on the tile
   * @throws IllegalArgumentException if the number of fish given is a negative number or more than
   *                                  maxFishNum.
   */
  public abstract int checkFishNum(int fishNum);

  /**
   * Checks if this position is a hole.
   *
   * @throws IllegalArgumentException if this position is a hole.
   */
  protected abstract void checkIfPosIsHole();
}
