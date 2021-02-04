package Model;

/**
 * Represents an action a player or referee may take.
 */
public interface Action {

  /**
   * Calls the action onto the given model.
   */
  void go(IFishModel m);

  /**
   * Gets the penguin that the player will like to do the action on.
   */
  Penguin getPenguin();

  /**
   * Gets the new position of the penguin after the action has been made.
   */
  Pos2D getAfterPosition();

}
