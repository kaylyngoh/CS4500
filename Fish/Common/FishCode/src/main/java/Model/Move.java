package Model;

import java.util.Objects;

/**
 * Represents a move action.
 */
public class Move implements Action {

  Penguin penguin;
  Pos2D nextPosition;

  /**
   * Creates a move action, moving the given penguin to the given position.
   *
   * @param penguin the penguin to move
   * @param nextPosition the position the player would like to move the penguin to
   *
   */
  public Move(Penguin penguin, Pos2D nextPosition) {
    this.penguin = penguin;
    this.nextPosition = nextPosition;
  }

  @Override
  public void go(IFishModel m) {
    m.movePenguin(penguin, nextPosition);
  }

  @Override
  public Penguin getPenguin() {
    return new Penguin(penguin);
  }

  @Override
  public Pos2D getAfterPosition() {
    return new Pos2D(nextPosition);
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }
    if (!(a instanceof Move)){
      return false;
    }
    Move that = (Move) a;
    return ((this.penguin.equals(that.penguin)) && (this.nextPosition.equals(that.nextPosition)));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.penguin, this.nextPosition);
  }

}