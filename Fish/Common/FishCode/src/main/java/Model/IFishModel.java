package Model;

public interface IFishModel extends ReadOnlyFishModel {

  /**
   * Places a penguin on behalf of a player on the given position.
   *
   * @throws IllegalArgumentException if the position given is invalid.
   */
  void placePenguin(Pos2D position);

  /**
   * Moves an existing penguin from its current position to the given position on behalf of the
   * player.
   *
   * @throws IllegalArgumentException if the position given is invalid, or if the move is invalid,
   *                                  or if the player does not own the penguin, or if the penguin
   *                                  does not exist on the current board.
   */
  void movePenguin(Penguin penguin, Pos2D position);

  /**
   * Removes an existing player from this game state, and all its penguins.
   *
   * @throws IllegalArgumentException if the player does not exist.
   */
  void removePlayer(Player player);

  /**
   * Skips the current player.
   */
  void skipPlayer();

  /**
   * Returns a copy of the fish model.
   */
  IFishModel copyFishModel();

  /**
   * Starts the game, game status goes from pending to playing.
   */
  void startGame();

  /**
   * Determines if there are 6-N penguins placed for each player, N being the number of players
   * in the game.
   */
  boolean each6NPenguins();

}