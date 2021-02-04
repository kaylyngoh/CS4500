package Model;

import java.util.List;

public interface ReadOnlyFishModel {

  /**
   * A getter method to get a copy of the board of this fish game model.
   *
   * @return a board that represents the current board of the game.
   */
  Board getBoard();

  /**
   * A getter method to get a copy of the list of penguins in the game.
   *
   * @return a list of penguins.
   */
  List<Penguin> getPenguins();

  /**
   * A getter method to get a copy of the list of penguins positions in the game.
   *
   * @return a list of positions.
   */
  List<Pos2D> getPenguinPositions();

  /**
   * A getter method to get a copy of the current player.
   *
   * @return the current player.
   */
  Player getCurrentTurn();

  /**
   * A getter method to get a copy of the list of players in the game.
   *
   * @return a list of players.
   */
  List<Player> getPlayers();

  /**
   * A getter method to get the score of the given player, using colour as a representation.
   *
   * @param colour the colour of the player
   * @return the score of a given player
   */
  int getScoreOfPlayer(Colour colour);

  /**
   * Determines if the game is over, as there are no more possible moves to make by any of the players.
   *
   * @return true if the game is over, false if not.
   */
  boolean hasGameEnded();

  /**
   * Determines if the game is over or is still going.
   *
   * @return true if game is over, false if not.
   */
  GameStatus getGameStatus();

  /**
   * Gets the winners of this game.
   *
   * @return a list of winners of this game.
   */
  List<Player> getWinners();

}