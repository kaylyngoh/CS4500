package Other;

import Model.*;
import Player.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the integration test to ensure that our game tree representation suffices to express
 * an entire game. The harness consumes a well-formed and valid JSON input and outputs a JSON array
 * of two positions, representing an action that the next player can take to get a penguin to a
 * place that neighbors the one that the first player just conquered. The input takes the form of
 * { "state" : State, "from" : Position, "to" : Position }
 */
public class xtree {

  /**
   * Reads and parses input and outputs the action that the next player can take to get a penguin to
   * a place that neighbors the one that the first player just conquered.
   * @param args input
   */
  public static void main(String[] args) {
    // reads the input stream and parses it into two json arrays: board and position
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    JsonObject input = (JsonObject) JsonParser.parseReader(reader);
    JsonObject state = input.getAsJsonObject("state");
    JsonArray from = input.getAsJsonArray("from");
    JsonArray to = input.getAsJsonArray("to");
    JsonArray board = state.getAsJsonArray("board");
    JsonArray players = state.getAsJsonArray("players");
    // checks if the board does not exceed 25 tiles
    checkValidBoard(board);
    // converts into our version of board
    Board ourBoard = getOurBoard(board);
    // converts into our version of players
    List<Player> ourPlayers = getOurPlayers(players);
    // makes the model
    IFishModel model = new FishModel(ourBoard, ourPlayers);
    model.startGame();
    //convert from position to our version
    Pos2D ourFrom = getOurPos2D(from);
    Pos2D ourTo = getOurPos2D(to);
    model.movePenguin(new Penguin(model.getCurrentTurn().getColor(), ourFrom), ourTo);
    // generate output
    generateOutput(model, ourTo);
  }

  /**
   * Generates the output by finding the possible action that the next player can take to get a
   * penguin to a place that neighbors the one that the first player just conquered.
   *
   * Prints false if no possible move to the neighboring tiles of the "to" position.
   *
   * @param model our game state (updated with the move from the first player
   * @param to the position of the penguin from the previous move of the first player
   */
  private static void generateOutput(IFishModel model, Pos2D to) {
    Action action = possibleNeighboringAction(model, to);
    if (action != null) {
      JsonArray output = generateActionOutput(action);
      System.out.print(output);
    } else {
      System.out.print("false");
    }
  }

  /**
   * Generates the actions output by converting the action to an array of two positions, first
   * representing the origin of the penguin, second is the neighboring position.
   *
   * @param action the action we are converting
   * @return the array of two positions representing the action in JSON version
   */
  public static JsonArray generateActionOutput(Action action) {
    JsonArray theirPositions = new JsonArray();
    theirPositions.add(generatePositionOutput(action.getPenguin().getPosition()));
    theirPositions.add(generatePositionOutput(action.getAfterPosition()));
    return theirPositions;
  }

  /**
   * Converts our coordinate system to the common coordinate system used (JSON) and making them into
   * arrays.
   *
   * @param pos the position we are converting
   * @return an array representing the position
   */
  private static JsonArray generatePositionOutput(Pos2D pos) {
    JsonArray theirPos = new JsonArray();
    if (pos.getX() % 2 == 0) {
      int theirY = pos.getY() * 2;
      int theirX = pos.getX() / 2;
      theirPos.add(theirY);
      theirPos.add(theirX);
    } else {
      int theirY = (pos.getY() * 2) + 1;
      int theirX = Math.round(pos.getX() / 2);
      theirPos.add(theirY);
      theirPos.add(theirX);
    }
    return theirPos;
  }

  /**
   * Determines the action that the next player can take to get a penguin to a place that neighbors
   * the one that the first player just conquered. The neighboring tiles are searched in the
   * following order: North, NorthEast, SouthEast, South, SouthWest, and NorthWest. If there are more
   * than one penguin that is able to move to the same neighboring tile, a tie breaker will determine
   * the action.
   *
   * @param model current state (after taking into account the first player's move)
   * @param to the position of the penguin after the move from the first player
   * @return the possible action after taking into account the tie breaker
   */
  public static Action possibleNeighboringAction(IFishModel model, Pos2D to) {
    List<Pos2D> orderedDirections;
    if (to.getX() % 2 == 0) {
      int[] possibleX = {to.getX(), to.getX() + 1, to.getX() + 1, to.getX(), to.getX() - 1, to.getX() - 1};
      int[] possibleY = {to.getY() - 1, to.getY() - 1, to.getY(), to.getY() + 1, to.getY(), to.getY() - 1};
      orderedDirections = getValidDirections(model, possibleX, possibleY);
    } else {
      int[] possibleX = {to.getX(), to.getX() + 1, to.getX() + 1, to.getX(), to.getX() - 1, to.getX() - 1};
      int[] possibleY = {to.getY() - 1, to.getY(), to.getY() + 1, to.getY() + 1, to.getY() + 1, to.getY()};
      orderedDirections = getValidDirections(model, possibleX, possibleY);
    }
    List<Action> possibleMoves = getPossibleMoves(model, orderedDirections);
    Action ans = null;
    if (possibleMoves.size() > 1) {
      ans = new Strategy().tieBreaker(possibleMoves);
    } else if (possibleMoves.size() == 1) {
      ans = possibleMoves.get(0);
    }
    return ans;
  }

  /**
   * Determines a list of action that represents the neighboring tile to move to in the given
   * clockwise order and the penguins that can move there.
   *
   * @param model current state (after taking into account the first player's move)
   * @param orderedDirections one step either North, NorthEast, SouthEast, South, SoutheWest, or
   *                          Northwest (in this order)
   * @return a list of possible actions (different penguins to the same neighboring tile)
   */
  private static List<Action> getPossibleMoves(IFishModel model, List<Pos2D> orderedDirections) {
    List<Action> possibleMoves = new ArrayList<>();
    for (Pos2D nextMove : orderedDirections) {
      for (Penguin p : model.getCurrentTurn().getPenguins()) {
        IGameTree currNode = new GameNode(model.copyFishModel(), null);
        try {
          currNode.takeAction(new Move(p, nextMove));
          possibleMoves.add(new Move(p, nextMove));
        } catch (IllegalArgumentException e) {
          continue;
        }
      }
      if (!possibleMoves.isEmpty()) {
        break;
      }
    }
    return possibleMoves;
  }

  /**
   * Creates a list of valid positions from possibleX and possibleY is found within the board.
   * possibleX and possibleY forms a pair of x and y-coordinates.
   *
   * @param model     our game state
   * @param possibleX the possible x-coordinates from the silly player strategy
   * @param possibleY the possible y-coordinates from the silly player strategy
   * @return a list of valid positions
   */
  private static List<Pos2D> getValidDirections(IFishModel model, int[] possibleX, int[] possibleY) {
    List<Pos2D> orderedDirections = new ArrayList<>();
    for (int i = 0; i < possibleX.length; i++) {
      if (possibleX[i] >= 0 && possibleY[i] >= 0 && possibleX[i] < model.getBoard().get2DTiles()[0].length
              && possibleY[i] < model.getBoard().get2DTiles().length) {
        orderedDirections.add(new Pos2D(possibleX[i], possibleY[i]));
      }
    }
    return orderedDirections;
  }

  /**
   * Converts players in given data representation into our own representation of players.
   *
   * @param players
   * @return a list of players in our own representation
   */
  protected static List<Player> getOurPlayers(JsonArray players) {
    List<Player> ourPlayers = new ArrayList<>();
    for (int i = 0; i < players.size(); i++) {
      JsonObject currPlayer = (JsonObject) players.get(i);
      Colour ourColour = getOurColor(currPlayer.getAsJsonPrimitive("color").getAsString());
      int ourScore = currPlayer.getAsJsonPrimitive("score").getAsInt();
      List<Penguin> ourPenguins = getOurPenguins(currPlayer.getAsJsonArray("places"), ourColour);
      ourPlayers.add(new Player(i + 1, ourColour, ourScore, ourPenguins));
    }
    return ourPlayers;
  }

  /**
   * Converts penguins in given data representation into our own representation of penguins.
   *
   * @param places the penguins location in given data representation
   * @param color  the color each penguin should be in
   * @return a list of penguins in our own representation
   */
  protected static List<Penguin> getOurPenguins(JsonArray places, Colour color) {
    List<Penguin> penguins = new ArrayList<>();
    for (int i = 0; i < places.size(); i++) {
      Pos2D ourPos = getOurPos2D(places.get(i).getAsJsonArray());
      penguins.add(new Penguin(color, ourPos));
    }
    return penguins;
  }

  /**
   * Converts position in given data representation into our own representation using our own
   * coordinate system
   *
   * @param position position in given data representation
   * @return position in our own data representation using our own coordinate system
   */
  protected static Pos2D getOurPos2D(JsonArray position) {
    int theirX = position.get(1).getAsInt();
    int theirY = position.get(0).getAsInt();
    int ourX;
    int ourY;
    if (theirY % 2 == 0) {
      ourX = theirX * 2;
      ourY = theirY / 2;
    } else {
      ourY = Math.round(theirY / 2);
      ourX = (theirX * 2) + 1;
    }
    return new Pos2D(ourX, ourY);
  }

  /**
   * Converts color in given data representation into our own representation.
   *
   * @param color color in given data representation
   * @throws IllegalArgumentException if given color does not exist in this game
   */
  private static Colour getOurColor(String color) {
    switch (color) {
      case "red":
        return Colour.RED;
      case "white":
        return Colour.WHITE;
      case "black":
        return Colour.BLACK;
      case "brown":
        return Colour.BROWN;
      default:
        throw new IllegalArgumentException("No such color");
    }
  }

  /**
   * Converts the board of given data representation into our own data representation of a board,
   * using our own coordinate system.
   *
   * @param board board in given data representation
   * @return board in our own data representation
   */
  protected static Board getOurBoard(JsonArray board) {
    JsonArray longestRow = (JsonArray) board.get(getLongestRow(board.size(), board));
    int ourRow = Math.round(board.size() / 2) + 1;
    int ourCol = longestRow.size() * 2;
    Pieces[][] ourTiles = makeEmptyBoard(ourRow, ourCol);
    for (int i = 0; i < board.size(); i++) {
      for (int j = 0; j < longestRow.size(); j++) {
        JsonArray currRow = (JsonArray) board.get(i);
        if (currRow.size() <= j) {
          break;
        } else {
          int fishNum = currRow.get(j).getAsInt();
          if (fishNum != 0) {
            if (i % 2 == 0) {
              ourTiles[i / 2][j * 2] = new Tile(fishNum);
            } else {
              ourTiles[Math.round(i / 2)][(j * 2) + 1] = new Tile(fishNum);
            }
          }
        }
      }
    }
    return new Board(ourTiles);
  }

  /**
   * Creates an empty board filled with holes.
   *
   * @param ourRow number of rows in our version of board
   * @param ourCol number of column in our version of board
   * @return empty board in our data representation
   */
  protected static Pieces[][] makeEmptyBoard(int ourRow, int ourCol) {
    Pieces[][] board = new Pieces[ourRow][ourCol];
    for (Pieces[] row : board) {
      Arrays.fill(row, new Hole());
    }
    return board;
  }

  /**
   * Checks if the given board (in given data representation) does not exceed a total of 25 tiles.
   *
   * @param board board in given data representation
   * @throws IllegalArgumentException if size of board exceeds 25 tiles.
   */
  protected static void checkValidBoard(JsonArray board) {
    int rows = board.size();
    int longestRowSize = board.get(getLongestRow(rows, board)).getAsJsonArray().size();
    if (longestRowSize * rows > 25) {
      throw new IllegalArgumentException("The size of the board may not exceed a total of 25 tiles.");
    }
  }

  /**
   * Determines the index of the row with the longest length.
   *
   * @param rows  the number of rows in the given board
   * @param board board in given data representation
   * @returns the index of the row with the longest length
   */
  protected static int getLongestRow(int rows, JsonArray board) {
    int longestRow = 0;
    if (rows > 1) {
      for (int i = 1; i < rows; i++) {
        if (board.get(longestRow).getAsJsonArray().size() < board.get(i).getAsJsonArray().size()) {
          longestRow = i;
        }
      }
    }
    return longestRow;
  }

}