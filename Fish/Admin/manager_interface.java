package Admin;

import Player.IPlayer;

import java.util.HashMap;
import java.util.List;

public interface manager_interface{

  /**
   * Informs on the players that are competing in all running games.
   * @return a HashMap of list of players and the running game they are in. (Integer represents
   * the game number they are in)
   */
  HashMap<Integer, List<IPlayer>> getAllRunningGamesInfo();

  /**
   * Provides the statistics of the winner's information (name, score) for a specific game using
   * the given game number.
   * @param gameNumber the requested game
   * @return a HashMap which pairs the winner's name and winner's score for a specific game
   */
  HashMap<String, Integer> getEndedGamesResults(int gameNumber);

  /**
   * Provides the statistics for all previous games (game number, winner's score)
   * @return a HashMap which pairs the game number and winner's score for all previous games.
   */
  HashMap<Integer, Integer> getAllEndedGamesResults();

  /**
   * Provides a tournament tree that represents the whole tournament and the winner for each
   * game and each round.
   * @return a tournament tree
   */
  // TournamentTree getTournamentTree();

  /**
   * Provides the player of the most recent game and round it was in.
   * @return a game number it was most recently in.
   */
  int getPlayerTournamentStatus(IPlayer player);

  /**
   * Provides the player's score in the past game specified.
   * @param player the player requesting this information
   * @param gameNumber the game requested
   * @return the score of the player
   */
  int getPlayerGameInfo(IPlayer player, int gameNumber);

  /**
   * Indicates if the given player is still competing in the tournament or it has been eliminated.
   * @param player the player requesting this information
   * @return true if the player is still competing, otherwise false
   */
  boolean getPlayerStatus(IPlayer player);

  /**
   * Indicate if the most current game this player is competing in is still running or has ended.
   * @param player the player requesting this information
   * @return true current game this player is competing in is still running, otherwise false
   */
  boolean isCompeting(IPlayer player);

  /**
   * Indicate the most recent round the player has played in.
   * @param player the player requesting this information
   * @return the most recent round number the player has played in
   */
  int getCurrentRound(IPlayer player);

  /**
   * Provides a list of game numbers the given player has played in.
   * @param player the player requesting this information
   * @return a list of game numbers the given player has played in
   */
  List<Integer> getGamesPlayed(IPlayer player);

}
