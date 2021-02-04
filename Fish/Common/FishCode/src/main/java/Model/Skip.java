package Model;

/**
 * Represents a skip action, skipping the current player in the current game state.
 */
public class Skip implements Action {

  @Override
  public void go(IFishModel m) {
    m.skipPlayer();
  }

  @Override
  public Penguin getPenguin() {
    throw new IllegalArgumentException("should not be here");
  }

  @Override
  public Pos2D getAfterPosition() {
    throw new IllegalArgumentException("should not be here");
  }
}