package Model;

/**
 * Represents a single tile in a board. A tile with zero fish num is a hole.
 */
public class Tile extends Pieces {

  /**
   * Makes a tile with the given number of fish and checks if it is within the range of 1 and
   * maxFishNum.
   */
  public Tile(int fishNum) {
    super(fishNum);
  }

  @Override
  public int checkFishNum(int fishNum) {
    if (fishNum > 0 && fishNum <= maxFishNum) {
      return fishNum;
    } else {
      throw new IllegalArgumentException("The minimum fish number should be more than zero or less than or equals to " + maxFishNum);
    }
  }

  @Override
  public Pieces replicatePiece() {
    return new Tile(this.getFishNum());
  }

  @Override
  protected void checkIfPosIsHole() {

  }

}