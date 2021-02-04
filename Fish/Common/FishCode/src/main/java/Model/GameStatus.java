package Model;

/**
 * Enum representing the three types of state the game can have. Pending (not started, player has
 * not finished playing their available penguins on the board), playing (has started) and
 * over (game ended).
 */
public enum GameStatus {
  PENDING, PLAYING, OVER
}
