package Admin;

import Player.IPlayer;

import java.util.HashMap;
import java.util.List;

/**
 * Represents the referee that would run a single game of View.Fish.
 */
public interface IReferee {

  /**
   * Gets the winners of the game that the referee supervises.
   */
  List<IPlayer> getWinners();

  /**
   * Gets the cheaters of the game that the referee supervises.
   */
  List<IPlayer> getCheaters();

  /**
   * Gets the winning score of the game that the referee supervises.
   */
  int getWinningScore();

  /**
   * Gets the overall result of the game that the referee supervises.
   */
  HashMap<IPlayer, Integer> getGameResult();

}