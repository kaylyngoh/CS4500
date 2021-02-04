package Player;

import Model.Action;
import Model.IFishModel;
import Model.Player;
import Model.Pos2D;

/**
 * Represents a strategy that a player can take to determine its next action to play a game of View.Fish.
 */
public interface IStrategy {

  /**
   * Gets the next desired action of the player, calculated using the strategy.
   */
  Action getPlayerAction(IFishModel model, int depth);

  /**
   * Gets the next desired placement of a penguin for the player, calculated using the strategy.
   */
  Pos2D getPenguinPlacement(IFishModel model);

}