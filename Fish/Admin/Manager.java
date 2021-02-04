package Admin;

import Player.IPlayer;

import java.util.*;

/**
 * The Tournament Manager runs a single game tournament of Fish until one or several winners emerge.
 * It is handed a List of Player Interfaces that is already sorted by age in ascending order.
 * The Manager allocates players to games by creating games with the maximum number of players permitted.
 * When the Manager cannot create a game with the maximum number of players permitted, it backtracks and
 * creates games of size one less than the maximum number until all the players are assigned.
 * After allocating the Player Interfaces to games, it continuously runs games until either one of the
 * following three conditions are met:
 * - Two tournament rounds of a game in a row have the same winner(s)
 * - when there are not enough players to run a single game
 * - There are enough players to run one final game
 * Once one of these criteria is met, the game is over and all players who played
 * in the tournament are informed whether they won or lost. If a player who won does not accept this
 * message from the tournament manager, then they become losers.
 */
public class Manager {

  private final int MAX_PLAYERS_IN_GAME = 4;
  private final int NUM_ROWS = 4;
  private final int NUM_COLS = 4;
  private List<IPlayer> players;
  private List<IPlayer> playersInRunning;
  private List<Referee> referees;
  private List<IPlayer> winnersOfPreviousRound;

  /**
   * Creates a Manager with the given input list of players.
   * After informing all the players that the tournament is starting, the Manager runs the tournament and
   * informs all the players of their results once the game is over.
   * @param players List of Players representing the players who will play in the tournament.
   */
  public Manager(List<IPlayer> players) {
    this.checkValidInputPlayers(players);
    this.players = players;
    this.playersInRunning = new ArrayList<>(this.players);
    this.referees = new ArrayList<>();
    this.winnersOfPreviousRound = new ArrayList<>();
    this.informTournamentStart();
    this.runCompleteTournament();
    this.informTournamentResults();
  }

  /**
   * This constructor is used for testing purposes
   * @param players
   * @param test
   */
  public Manager(List<IPlayer> players, boolean test) {
    this.checkValidInputPlayers(players);
    this.players = players;
    this.playersInRunning = new ArrayList<>(this.players);
    this.referees = new ArrayList<>();
    this.winnersOfPreviousRound = new ArrayList<>();
  }

  /**
   * Informs all the players in the game that the tournament has started.
   */
  public void informTournamentStart() {
    for (IPlayer player : players) {
      player.startGame();
    }
  }

  /**
   * Allocates players to referees based on the maximal number of participants in a game.
   */
  public void allocatePlayersToGames() {
    // remember the winner's of the previous round
    this.winnersOfPreviousRound = new ArrayList<>(this.playersInRunning);
    int numPreviousWinners = this.winnersOfPreviousRound.size();
    // calculate number of games with maximal players
    int numFullGame = (int) Math.floor(numPreviousWinners / MAX_PLAYERS_IN_GAME);
    boolean backtrack = false;
    // determine if the last game has less than minimum number of players and needs to backtrack
    // by one game to try games of size one less than the maximal number and so on until all players are assigned.
    if (numPreviousWinners % 4 == 1) {
      numFullGame -= 1;
      backtrack = true;
    }
    this.createFullGames(numFullGame);
    //If there is a perfect fit of games, no need to backtrack or create a final referee
    if (numPreviousWinners % 4 != 0) {
      this.createBackTrackGame(backtrack);
      this.createLastReferee();
    }
  }

  /**
   * Runs a complete tournament of Fish until one of the end conditions are met.
   */
  public void runCompleteTournament() {
    while (!isTournamentOver()) {
      // Clears the referee list of the current round to prepare for the next
      this.referees = new ArrayList<>();
      this.allocatePlayersToGames();
      //If there are only enough players to run a single game, then end the tournament
      if (this.playersInRunning.size() <= MAX_PLAYERS_IN_GAME) {
        break;
      }
      // Adds all the players who won into the list of players still in the running
      for (Referee ref : this.referees) {
        this.playersInRunning.addAll(ref.getWinners());
      }
    }
  }

  /**
   * Goes through the end of tournament conditions to see if another round can be played.
   * @return boolean representing if there is a next round in the tournament.
   */
  public boolean isTournamentOver() {
    return this.playersInRunning.equals(this.winnersOfPreviousRound) || this.referees.size() <= 1 || this.playersInRunning.size() == 1;
  }

  /**
   * Informs all players in the game about the result of the tournament. If the player throws an exception when
   * we inform that they are a winner, then they are kicked out of the game.
   */
  public void informTournamentResults() {
    List<IPlayer> playersToRemove = new ArrayList<>();

    for (IPlayer player : this.players) {
      player.endGame();
      if (this.playersInRunning.contains(player)) {
        try {
          player.acceptWin();
        }
        catch (Exception e) {
          playersToRemove.add(player);
        }
      }
    }

    this.players.removeAll(playersToRemove);
    this.playersInRunning.removeAll(playersToRemove);
  }

  public List<IPlayer> getPlayers() {
    return this.players;
  }

  public List<IPlayer> getPlayersInRunning() {
    return this.playersInRunning;
  }

  public List<Referee> getReferees() {
    return this.referees;
  }

  /**
   * Creates games with the the maximal number of players.
   * @param numFullGame int representing the number of games to create.
   */
  private void createFullGames(int numFullGame) {
    // creates games with maximal number of players
    while (numFullGame > 0) {
      List<IPlayer> playersInAGame = new ArrayList<>();
      for (int i = 0; i < MAX_PLAYERS_IN_GAME; i++) {
        playersInAGame.add(this.playersInRunning.remove(0));
      }
      this.referees.add(new Referee(playersInAGame, this.NUM_ROWS, this.NUM_COLS));
      numFullGame -= 1;
    }
  }

  /**
   * Creates a game with size one less than the maximal number of players in a game if a backtrack is necessary.
   * @param backtrack boolean representing whether or not a backtrack is necessary to run a game.
   */
  private void createBackTrackGame(boolean backtrack) {
    // backtracks and creates a game of size one less than the maximal number
    if (backtrack) {
      List<IPlayer> playersInAGame = new ArrayList<>();
      for (int i = 0; i < MAX_PLAYERS_IN_GAME - 1; i++) {
        playersInAGame.add(this.playersInRunning.remove(0));
      }
      this.referees.add(new Referee(playersInAGame, this.NUM_ROWS, this.NUM_COLS));
    }
  }

  /**
   * Creates the last referee in the tournament during the player allocation.
   */
  private void createLastReferee() {
    // creates the last game with the remaining players
    List<IPlayer> playersInAGame = new ArrayList<>();
    int size = this.playersInRunning.size();
    for (int i = 0; i < size; i++) {
      playersInAGame.add(this.playersInRunning.remove(0));
    }
    this.referees.add(new Referee(playersInAGame, this.NUM_ROWS, this.NUM_COLS));
  }

  /**
   * Checks if the list of players given to the Manager is valid.
   * @param players List of Players representing the players handed to the Manager as input.
   */
  private void checkValidInputPlayers(List<IPlayer> players) {
    if (players.size() <= 1) {
      throw new IllegalArgumentException("Must have more than 1 player to run a tournament.");
    }
  }
}