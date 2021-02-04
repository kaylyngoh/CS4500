package Admin;

import Model.*;
import Player.*;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Represents a single referee that will run a single game of View.Fish.
 */
public class Referee implements IReferee {

  private IGameTree gameTree;
  private HashMap<Colour, IPlayer> playersInThisGame;
  // Future Implementations:
  // private List<Observers> observers;

  // constants
  private int timeOut = 30;
  private int fishNum = 3;

  private int testRow = 3;
  private int testCol = 3;

  /**
   * The referee will assign random colours to the players and keeps a HashMap of the players and
   * the assigned colors. It will create a board filled with tiles (no holes) with 3 fishes with the given
   * row-by-col dimensions given by the tournament manager and initialize the starting game state.
   * It will then keep placing penguins for the players until all players in this game has 6 - N penguins,
   * N being the total number of players. Once all the penguins are placed, it will officially starts
   * the game and tells all the players the game has started. It also creates a game tree to be used
   * for rule checking and keep track of the game state. It supervises the game by moving penguins
   * for players that has the current turn until there are no possible moves for any of the players.
   * Once the game has ended, it tells the players the game has ended and notifies players of the end
   * game information (winners and winning scores) if they choose to do so.
   *
   * FUTURE: All keep track of a list of observers that it will need to notify game actions to.
   *
   * @param players list of players given by the tournament manager
   * @param row the row dimensions given by the tournament manager
   * @param col the column dimensions given by the tournament manager
   */
  public Referee(List<IPlayer> players, int row, int col) {
    this.playersInThisGame = assignColorToPlayers(players);
    IFishModel startingState = this.setupGame(row, col, fishNum, new ArrayList<>());
    this.gameTree = new GameNode(startingState, null);
    moveForPlayers();
  }

  /**
   * Test constructor for penguin placement
   * @param players List of Players in the game.
   * @param toPlace List of Positions that
   */
  public Referee(List<IPlayer> players, List<Pos2D> toPlace) {
    this.playersInThisGame = assignColorToPlayers(players);
    Board board = new Board(testRow, testCol, fishNum);
    List<Player> playersInModel = sortByAge(getModelPlayer());
    IFishModel startingState = new FishModel(board, playersInModel);

    for (Pos2D placement : toPlace) {
      startingState = validatePlacePenguin(startingState, placement);
    }

    this.gameTree = new GameNode(startingState, null);
  }

  /**
   * Similar constructor, only addition is to allow the specification of the fish number on the tile.
   *
   * @param players list of players given by the tournament manager
   * @param row the row dimensions given by the tournament manager
   * @param col the column dimensions given by the tournament manager
   * @param fishNum the number of fishes on each and every tile.
   * @param holes the position of holes that should be on the board
   */
  public Referee(List<IPlayer> players, int row, int col, int fishNum, List<Pos2D> holes) {
    this.playersInThisGame = assignColorToPlayers(players);
    IFishModel startingState = this.setupGame(row, col, fishNum, holes);
    this.gameTree = new GameNode(startingState, null);
    moveForPlayers();
  }

  /**
   * Sets up the board, players and place initial penguins before officially starting the game.
   *
   * @param row the row dimensions given by the tournament manager
   * @param col the column dimensions given by the tournament manager
   * @param fishNum the number of fishes on each and every tile.
   * @param holes the position of holes that should be on the board
   */
  private IFishModel setupGame(int row, int col, int fishNum, List<Pos2D> holes) {
    Board board = new Board(row, col, fishNum, holes);
    List<Player> playersInModel = sortByAge(getModelPlayer());
    IFishModel startingState = placePenguins(new FishModel(board, playersInModel));
    startingState.startGame();

    return startingState;
  }

  /**
   * Creates the end game information, a HashMap of the winners name and their score.
   * @param winners the winners from the game state, this method will convert it to the Model.Player
   *                component that interacts with the referee.
   * @return a HashMap of the winners name and their score.
   */
  private HashMap<String, Integer> getEndInfo(List<Player> winners) {
    HashMap<String, Integer> endInfo = new HashMap<>();
    for (Player p : winners) {
      IPlayer player = this.playersInThisGame.get(p.getColor());
      endInfo.put(player.getPlayerName(), p.getScore());
    }
    return endInfo;
  }

  /**
   * Recursively move penguins on behalf of players until there are no actions that can be done by
   * any players. It will first ask the player for its next action and if the action provided it not
   * legal, it will kick the cheater out of the game or if the player does not provide an action within
   * 30 seconds, it will also kick the failing player out of the game.
   *
   * The referee will kick the player out of the game state but they will still remain in the list
   * of players kept by the referee to determine who are the cheaters/failing players at the end
   * of the game.
   */
  private void moveForPlayers() {
    while (this.gameTree.generatePossibleGameState().size() != 0) {
      //Skip turn if there is only one child node and there are no valid moves for the current player
      if (this.gameTree.generatePossibleGameState().size() == 1
      && this.gameTree.currentSkipTurn()) {
        this.gameTree = this.gameTree.generatePossibleGameState().get(0);
        continue;
      }

      //And then replace game tree with the new gameState
      IFishModel currState = this.gameTree.getGameState();
      IPlayer currPlayer = findCurrentPlayer(currState.getCurrentTurn());
      ExecutorService executor = Executors.newCachedThreadPool();
      Callable<Object> task = new Callable<Object>() {
        public Object call() {
          return currPlayer.getMove(currState);
        }
      };
      Future<Object> future = executor.submit(task);
      try {
        Action result = (Action) future.get(this.timeOut, TimeUnit.SECONDS);
        this.validateMovePenguin(currState, result);
      } catch (TimeoutException ex) {
        removePlayerFromGameMove(currState);
      } catch (InterruptedException e) {
        removePlayerFromGameMove(currState);
      } catch (ExecutionException e) {
        removePlayerFromGameMove(currState);
      } finally {
        future.cancel(true);
        // report each move to the observers
      }
    }
  }

  public void validateMovePenguin(IFishModel currState, Action result) {
    try {
      IFishModel newState = this.gameTree.takeAction(result);
      this.gameTree = new GameNode(newState, this.gameTree);
    }
    catch (IllegalArgumentException e) {
      removePlayerFromGameMove(currState);
    }
  }

  /**
   * Removes the current player out of the game (game state) and builds a new game tree out of the new
   * game state.
   *
   * @param currState the current state of the game that we will remove the player from.
   */
  public void removePlayerFromGameMove(IFishModel currState) {
    currState.removePlayer(currState.getCurrentTurn());
    this.gameTree = new GameNode(currState, this.gameTree);
  }

  /**
   * Recursively place penguins on behalf of players until each player have 6 - N penguins placed on the board,
   * N being the total number of players in the game. It will first ask the player for its next position to
   * place a penguin and if the position provided it not legal, it will kick the cheater out of the game
   * or if the player does not provide a position within 30 seconds, it will also kick the failing player
   * out of the game.
   *
   * The referee will kick the player out of the game state but they will still remain in the list
   * of players kept by the referee to determine who are the cheaters/failing players at the end
   * of the game.
   *
   * @param model  the game state that we will place the penguins on
   * @return a game state after placing all the penguins for the players.
   */
  private IFishModel placePenguins(IFishModel model) {
    while (!model.each6NPenguins() && model.getGameStatus() != GameStatus.OVER) {
      IPlayer currPlayer = findCurrentPlayer(model.getCurrentTurn());
      ExecutorService executor = Executors.newCachedThreadPool();
      Callable<Object> task = new Callable<Object>() {
        public Object call() {
          return currPlayer.getPositionToPlace(model);
        }
      };
      Future<Object> future = executor.submit(task);
      try {
        Pos2D result = (Pos2D) future.get(this.timeOut, TimeUnit.SECONDS);
        model.getBoard().checkPosInBoard(result);
        this.validatePlacePenguin(model, result);
      } catch (TimeoutException ex) {
        model.removePlayer(model.getCurrentTurn());
      } catch (InterruptedException e) {
        model.removePlayer(model.getCurrentTurn());
      } catch (ExecutionException e) {
        model.removePlayer(model.getCurrentTurn());
      } finally {
        future.cancel(true);
        // report each move to the observers
      }
    }
    return model;
  }

  /**
   * Validates that the penguin can be placed at the position and performs the action if possible.
   * Otherwise, it removes the player from the game.
   * @param model IFishModel representing the state of the game.
   * @param result Pos2D representing where the penguin is to be placed
   */
  public IFishModel validatePlacePenguin(IFishModel model, Pos2D result) {
    try {
      model.placePenguin(result);
    }
    catch (IllegalArgumentException e) {
      model.removePlayer(model.getCurrentTurn());
    }

    return model;
  }

  /**
   * Finds the current Model.Player component using the current player in the game state.
   *
   * @param player the current player from the game state
   * @return the player component that is the current player of the game
   */
  private IPlayer findCurrentPlayer(Player player) {
    for (Map.Entry<Colour, IPlayer> p : this.playersInThisGame.entrySet()) {
      if (p.getKey().equals(player.getColor())) {
        return p.getValue();
      }
    }
    throw new IllegalArgumentException("Can't find player");
  }

  /**
   * Creates a list of players used in the game model/game state by getting certain information from
   * the Model.Player component that interacts with the referee.
   *
   * @return a list of players used in the game model/state
   */
  private List<Player> getModelPlayer() {
    List<Player> playersInModel = new ArrayList<>();
    for (Map.Entry<Colour, IPlayer> p : this.playersInThisGame.entrySet()) {
      Player playerInModel = new Player(p.getValue().getPlayerAge(), p.getKey());
      playersInModel.add(playerInModel);
    }
    return playersInModel;
  }

  /**
   * Assigns a color to the player.
   *
   * @param players the players in the game
   * @return a HashMap of players and its assigned color
   */
  private HashMap<Colour, IPlayer> assignColorToPlayers(List<IPlayer> players) {
    HashMap<Colour, IPlayer> playersInGame = new HashMap<>();
    Colour[] colorAvaliable = Colour.values();
    for (int i = 0; i < players.size(); i++) {
      playersInGame.put(colorAvaliable[i], players.get(i));
    }
    return playersInGame;
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

  @Override
  public List<IPlayer> getWinners() {
    List<IPlayer> ans = new ArrayList<>();
    for (Player p : this.gameTree.getGameState().getWinners()) {
      IPlayer player = this.playersInThisGame.get(p.getColor());
      ans.add(player);
    }
    return ans;
  }

  @Override
  public List<IPlayer> getCheaters() {
    List<IPlayer> remainingPlayers = convertToIPlayer(this.gameTree.getGameState().getPlayers());
    List<IPlayer> cheaters = new ArrayList<>();
    for (IPlayer player : playersInThisGame.values()) {
      if (!remainingPlayers.contains(player)) {
        cheaters.add(player);
      }
    }
    return cheaters;
  }

  /**
   * Converts the list of players used in the game model/state to a list of Model.Player components that
   * interacts with the referee.
   *
   * @return a list of players used in the game model/state
   */
  private List<IPlayer> convertToIPlayer(List<Player> players) {
    List<IPlayer> iplayers = new ArrayList<>();
    for (Player p : players) {
      iplayers.add(this.playersInThisGame.get(p.getColor()));
    }
    return iplayers;
  }

  @Override
  public int getWinningScore() {
    return this.gameTree.getGameState().getWinners().get(0).getScore();
  }

  @Override
  public HashMap<IPlayer, Integer> getGameResult() {
    HashMap<IPlayer, Integer> ans = new HashMap<>();
    for (Player p : this.gameTree.getGameState().getPlayers()) {
      IPlayer player = this.playersInThisGame.get(p.getColor());
      ans.put(player, p.getScore());
    }
    return ans;
  }

  /**
   * Getter method for testing purposes.
   *
   * Get the game state of the game supervised by the referee.
   *
   * @return the game state
   */
  public IFishModel getGameState() {
    return this.gameTree.getGameState();
  }

  /**
   * Getter method for testing purposes.
   *
   * Gets the list of Model.Player components that interacts with the referee.
   *
   * @return a HashMap of the players in this game.
   */
  public HashMap<Colour, IPlayer> getPlayersInThisGame() {
    return new HashMap<>(this.playersInThisGame);
  }

}