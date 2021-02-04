package Model;

/**
 * Represents a single hole on the board.
 */
public class Hole extends Pieces {

  /**
   * Creates a hole with 0 fishes on it.
   */
  public Hole() {
    super(0);
  }

  @Override
  public Pieces replicatePiece() {
    return new Hole();
  }

  @Override
  public int checkFishNum(int fishNum) {
    return 0;
  }

  @Override
  public void checkIfPosIsHole() {
    throw new IllegalArgumentException("This is a hole");
  }

}