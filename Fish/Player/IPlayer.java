package Player;

import Model.*;

/**
 * Represents the interface of players that is passed to the referee to communicate its actions.
 */
public interface IPlayer {

  /**
   * Gets the next action from the player to indicate a move of an existing penguin to a different position.
   * @param state the current state of the game
   * @return an action that the player would like to take.
   */
  Action getMove(IFishModel state);

  /**
   * Gets the position from the player in which the player wants to place a penguin.
   * @param state the current state of the game
   * @return the position the player would like to place a penguin.
   */
  Pos2D getPositionToPlace(IFishModel state);

  /**
   * Gets the player name.
   * @return String that represents the player name.
   */
  String getPlayerName();

  /**
   * Gets the player age.
   * @return int that represents the player age.
   */
  int getPlayerAge();

  /**
   * Starts the game.
   */
  void startGame();

  /**
   * Ends the game.
   */
  void endGame();

  /**
   * Gets the current game status of the game this player is playing in.
   */
  GameStatus getGameStatus();

  /**
   * Gets the current game status of the game this player is playing in.
   */
  boolean getIsWinner();

  /**
   * Accepts or rejects a win for a winning player.
   */
  boolean acceptWin();
}