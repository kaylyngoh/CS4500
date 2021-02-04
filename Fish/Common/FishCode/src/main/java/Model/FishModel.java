package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the game state of the Fish game.
 * A game state keeps track of the current board used in the game.
 * The players are also stored in the game state, and are sorted by age in ascending order.
 * To keep track of whose turn it is, there is a currentTurn field that changes per game state.
 * The status is used to figure out what phase of the game the state is in, and is either pending, playing, or over.
 */
public class FishModel implements IFishModel {

  private final Board board;
  private List<Player> players;
  private Player currentTurn;
  private GameStatus status;

  /**
   * Makes a single starting game state by giving it a starter board and list of players who will
   * join this game.
   *
   * @param board   starter board
   * @param players list of players who will join this game
   * @throws IllegalArgumentException if the size of the list of players is not between 2-4 and if
   *                                  the list of players is not sorted by age.
   */
  public FishModel(Board board, List<Player> players) {
    this.board = board;
    this.players = checkValidOrder(checkValidNumOfPlayer(players));
    this.currentTurn = this.players.get(0);
    this.status = GameStatus.PENDING;
  }

  /**
   * Copy constructor for Model.FishModel.
   */
  public FishModel(FishModel model) {
    this.board = model.getBoard();
    this.players = model.getPlayers();
    this.currentTurn = findCurrentTurn(model.getCurrentTurn());
    this.status = model.getGameStatus();
  }

  /**
   * Used for copy constructor. Determines the current turn from the list of players.
   *
   * @param currentTurn the current player to make an action
   * @return the current player from the list of players.
   * @throws IllegalArgumentException if the current turn is not found from the list of players
   *                                  (should not happen)
   */
  private Player findCurrentTurn(Player currentTurn) {
    for (Player p : this.players) {
      if (p.equals(currentTurn))
        return p;
    }
    throw new IllegalArgumentException("No such player");
  }

  /**
   * Checks to see if the given list of players is ordered correctly by age (ascending order).
   *
   * @param players the list of players
   * @return the ordered list of players
   * @throws IllegalArgumentException if the given list of players is not ordered correctly.
   */
  private List<Player> checkValidOrder(List<Player> players) {
    List<Player> correctlySorted = sortByAge(players);
    if (players.equals(correctlySorted)) {
      return players;
    } else {
      throw new IllegalArgumentException("List is not correctly sorted");
    }
  }

  /**
   * Sorts the given list of players by age.
   *
   * @return the list from youngest to oldest.
   */
  private List<Player> sortByAge(List<Player> players) {
    List<Player> copyPlayers = new ArrayList<>(players);
    List<Player> playersByAge = new ArrayList<Player>();
    while (copyPlayers.size() > 0) {
      Player youngestPlayer = determineYoungestPlayer(copyPlayers);
      playersByAge.add(youngestPlayer);
      copyPlayers.remove(youngestPlayer);
    }
    return playersByAge;
  }

  /**
   * Determines the youngest player in the given list of players.
   *
   * @return the youngest player in the list.
   */
  private Player determineYoungestPlayer(List<Player> players) {
    Player youngestPlayer = players.get(0);
    for (int i = 1; i < players.size(); i++) {
      if (players.get(i).getAge() < youngestPlayer.getAge()) {
        youngestPlayer = players.get(i);
      }
    }
    return youngestPlayer;
  }

  /**
   * Checks if there is 2 to 4 players in the given list of players.
   *
   * @return the list of players if it has 2 to 4 players in it.
   * @throws if there is not enough players or too many players.
   */
  private List<Player> checkValidNumOfPlayer(List<Player> players) {
    if (players.size() >= 2 && players.size() <= 4) {
      return players;
    } else {
      throw new IllegalArgumentException("Game should have 2 to 4 players");
    }
  }

  @Override
  public void placePenguin(Pos2D position) {
    if (this.status == GameStatus.PENDING) {
      this.board.checkValidPlacement(position, this.getPenguinPositions());
      this.currentTurn.addPenguin(position);
      skipPlayer();
    } else {
      throw new IllegalArgumentException("Game has started or has ended.");
    }
  }

  @Override
  public void movePenguin(Penguin penguin, Pos2D position) {
    Pos2D oldPos = penguin.getPosition();
    if (this.status == GameStatus.PLAYING) {
      if (!this.currentTurn.hasNoMoveAvailable(this.board, getPenguinPositions())) {
        checkValidPenguinToMove(penguin);
        this.board.checkValidPos(position.getX(), position.getY());
        this.board.checkValidMove(oldPos, position, getPenguinPositions());
        this.currentTurn.updatePenguin(penguin, position);
        this.currentTurn.addScore(this.board.get2DTiles()[oldPos.getY()][oldPos.getX()].getFishNum());
        this.board.removeTile(oldPos);
        checkGameStatus();
        skipPlayer();
      } else {
        throw new IllegalArgumentException("Current player has no more moves");
      }
    } else {
      throw new IllegalArgumentException("Game is over or has not started.");
    }
  }

  /**
   * Checks and updates the game status after every move to determine if the game has ended.
   */
  private void checkGameStatus() {
    if (hasGameEnded()) {
      updateAllPlayersWithFishScores();
      this.status = GameStatus.OVER;
    }
  }

  /**
   * Adds all the remaining fish scores for the players after the game has ended.
   */
  private void updateAllPlayersWithFishScores() {
    for (Player player : this.players) {
      for (Penguin penguin : player.getPenguins()) {
        player.addScore(this.board.get2DTiles()[penguin.getPosition().getY()][penguin.getPosition().getX()].getFishNum());
      }
    }
  }

  @Override
  public boolean hasGameEnded() {
    boolean gameOver = true;
    if (this.players.size() > 0) {
      for (Player p : this.players) {
        gameOver = gameOver && p.hasNoMoveAvailable(this.board, getPenguinPositions());
      }
    }
    return gameOver;
  }


  /**
   * Checks if the current player is able to move the given penguin.
   *
   * @throws IllegalArgumentException if the current player does not own the penguin.
   */
  private void checkValidPenguinToMove(Penguin penguin) {
    if (penguin.getColor() != this.currentTurn.getColor()) {
      throw new IllegalArgumentException("Current player do not own this penguin");
    }
  }

  @Override
  public void removePlayer(Player player) {
    if (player.equals(this.currentTurn)) {
      skipPlayer();
    }
    this.players.remove(player);
    checkGameStatus();
  }

  @Override
  public void skipPlayer() {
    this.currentTurn = this.players.get((this.players.indexOf(this.currentTurn) + 1) % this.players.size());
  }

  @Override
  public IFishModel copyFishModel() {
    return new FishModel(this);
  }

  @Override
  public void startGame() {
    this.status = GameStatus.PLAYING;
    checkGameStatus();
  }

  @Override
  public boolean each6NPenguins() {
    for (Player p : this.players) {
      if (p.getPenguins().size() != (6 - this.players.size())) {
        return false;
      }
    }
    return true;
  }

  @Override
  public Board getBoard() {
    return new Board(this.board.get2DTiles());
  }

  @Override
  public List<Penguin> getPenguins() {
    List<Penguin> clone = new ArrayList<Penguin>();
    for (Player p : this.players) {
      clone.addAll(p.getPenguins());
    }
    return clone;
  }

  @Override
  public List<Pos2D> getPenguinPositions() {
    List<Pos2D> clone = new ArrayList<>();
    for (Player player : this.players) {
      for (Penguin penguin : player.getPenguins()) {
        clone.add(penguin.getPosition());
      }
    }
    return clone;
  }

  @Override
  public Player getCurrentTurn() {
    return new Player(this.currentTurn);
  }

  @Override
  public List<Player> getPlayers() {
    List<Player> clone = new ArrayList<Player>();
    for (Player p : this.players) {
      clone.add(new Player(p));
    }
    return clone;
  }

  @Override
  public int getScoreOfPlayer(Colour colour) {
    for (Player p : this.players) {
      if (p.getColor().equals(colour)) {
        return p.getScore();
      }
    }
    throw new IllegalArgumentException("No such player found");
  }

  @Override
  public GameStatus getGameStatus() {
    return this.status;
  }

  @Override
  public List<Player> getWinners() {
    int winningScore = 0;
    for (Player p : getPlayers()) {
      if (p.getScore() > winningScore) {
        winningScore = p.getScore();
      }
    }
    List<Player> winners = new ArrayList<>();
    for (Player p : getPlayers()) {
      if (p.getScore() == winningScore) {
        winners.add(p);
      }
    }

    return winners;
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }
    if (!(a instanceof FishModel)) {
      return false;
    }
    FishModel that = (FishModel) a;
    return ((this.board.equals(that.board))
            && (this.players.equals(that.players))
            && (this.currentTurn.equals(that.currentTurn))
            && (this.status.equals(that.status)));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.board, this.players, this.currentTurn, this.status);
  }
}