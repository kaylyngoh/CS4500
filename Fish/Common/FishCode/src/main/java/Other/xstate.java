package Other;

import Model.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the integration test to ensure that our game state representation suffices to express
 * complete turns. The harness consumes a well-formed and valid JSON input and outputs a JSON object
 * that represents the current game state after taking a silly player strategy.
 * Input: { "players" : Model.Player*, "board" : Model.Board} where players is a JSON array that contains
 * an object to represent a player with the format { "color" : Color, "score" : Natural,
 * "places" : [Position, ..., Position]} and Model.Board is a JSON array of JSON arrays where
 * each element is either 0 or a number between 1 and 5.
 */
public class xstate {

  /**
   * Reads and parses input and outputs the effect of a silly player strategy of taking a turn.
   * @param args input
   */
  public static void main(String[] args) {
    // reads the input stream and parses it into two json arrays: board and position
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    JsonObject BoardPosn = (JsonObject) JsonParser.parseReader(reader);
    JsonArray board = BoardPosn.getAsJsonArray("board");
    JsonArray players = BoardPosn.getAsJsonArray("players");
    // checks if the board does not exceed 25 tiles
    checkValidBoard(board);
    // converts into our version of board
    Board ourBoard = getOurBoard(board);
    // converts into our version of players
    List<Player> ourPlayers = getOurPlayers(players);
    // makes the model
    IFishModel model = new FishModel(ourBoard, ourPlayers);
    // generate output
    generateOutput(model, board);
  }

  /**
   * Generates the output by converting our version of the game state after the move into
   * JSON version and prints it.
   * @param model our game state.
   * @param theirInputBoard the received input board in JSON
   */
  private static void generateOutput(IFishModel model, JsonArray theirInputBoard) {
    JsonObject output = new JsonObject();
    if (sillyPlayerStrategy(model)) {
      output.add("board", reformatOutputBoard(generateBoardOutput(model), theirInputBoard));
      output.add("players", generatePlayerOutput(model));
      System.out.print(output);
    } else {
      System.out.print("False");
    }
  }

  /**
   * Reformats the output to have the same number of rows as the original input JSON.
   *
   * @param theirOutputBoard output in JSON
   * @param theirInputBoard input in JSON
   * @return output in JSON with the correct number of rows
   */
  public static JsonArray reformatOutputBoard(JsonArray theirOutputBoard, JsonArray theirInputBoard) {
    JsonArray finalFormatedOutput = new JsonArray();
    for (int i = 0; i < theirOutputBoard.size(); i++) {
      if (i >= theirInputBoard.size()) {
        break;
      } else {
        finalFormatedOutput.add(theirOutputBoard.get(i));
      }
    }
    return finalFormatedOutput;
  }

  /**
   * Converts our version of player into the JSON output version.
   *
   * @param model our game state.
   * @return JSON output of players
   */
  public static JsonArray generatePlayerOutput(IFishModel model) {
    List<Player> ourPlayers = model.getPlayers();
    List<Player> reorderedPlayers = reorderPlayers(ourPlayers);
    JsonArray theirPlayers = new JsonArray();
    for (Player player : ourPlayers) {
      JsonObject theirSinglePlayer = new JsonObject();
      theirSinglePlayer.add("color", generateColorOutput(player.getColor()));
      theirSinglePlayer.add("score", new JsonPrimitive(player.getScore()));
      theirSinglePlayer.add("places", generatePenguinOutput(player.getPenguins()));
      theirPlayers.add(theirSinglePlayer);
    }
    return theirPlayers;
  }

  /**
   * Reorders the list of players where the current player should be first in the list.
   *
   * @param ourPlayers the list to be reordered
   * @return reordered list by turn
   */
  private static List<Player> reorderPlayers(List<Player> ourPlayers) {
    Player playerToMove = ourPlayers.get(0);
    ourPlayers.remove(playerToMove);
    ourPlayers.add(playerToMove);
    return ourPlayers;
  }

  /**
   * Converts our version of penguins into the JSON output version.
   *
   * @param penguins the list of penguins in our data representation that needs to be converted
   * @return JSON output of penguins
   */
  protected static JsonArray generatePenguinOutput(List<Penguin> penguins) {
    JsonArray theirPositions = new JsonArray();
    for (Penguin p : penguins) {
      JsonArray theirPos = new JsonArray();
      Pos2D penguinPos = p.getPosition();
      if (penguinPos.getX() % 2 == 0) {
        int theirY = penguinPos.getY() * 2;
        int theirX = penguinPos.getX() / 2;
        theirPos.add(theirY);
        theirPos.add(theirX);
      } else {
        int theirY = (penguinPos.getY() * 2) + 1;
        int theirX = Math.round(penguinPos.getX() / 2);
        theirPos.add(theirY);
        theirPos.add(theirX);
      }
      theirPositions.add(theirPos);
    }
    return theirPositions;
  }

  /**
   * Converts our version of color into the JSON output version.
   *
   * @param color our data representation of color
   * @return JSON output
   */
  private static JsonPrimitive generateColorOutput(Colour color) {
    switch (color) {
      case RED:
        return new JsonPrimitive("red");
      case WHITE:
        return new JsonPrimitive("white");
      case BLACK:
        return new JsonPrimitive("black");
      case BROWN:
        return new JsonPrimitive("brown");
      default:
        throw new IllegalArgumentException("No such color");
    }
  }

  /**
   * Converts our version of the board into the JSON output version.
   *
   * @param model our game state
   * @return JSON output
   */
  public static JsonArray generateBoardOutput(IFishModel model) {
    Pieces[][] ourBoard = model.getBoard().get2DTiles();
    JsonArray theirBoard = new JsonArray();
    for (int i = 0; i < ourBoard.length; i++) {
      JsonArray row1 = new JsonArray();
      JsonArray row2 = new JsonArray();
      for (int j = 0; j < ourBoard[0].length; j++) {
        if (j % 2 == 0) {
          row1.add(ourBoard[i][j].getFishNum());
        } else {
          row2.add(ourBoard[i][j].getFishNum());
        }
      }
      theirBoard.add(row1);
      theirBoard.add(row2);
    }
    return theirBoard;
  }


  /**
   * Determines if there is a possible silly player strategy move and makes a move on the game state
   * if there is.
   *
   * @param model our game state.
   * @return true if there is a possible silly player strategy move, false if not.
   */
  public static boolean sillyPlayerStrategy(IFishModel model) {
    Player firstPlayer = model.getPlayers().get(0);
    Penguin firstPenguin = firstPlayer.getPenguins().get(0);
    Pos2D firstPenguinPos = firstPenguin.getPosition();
    List<Pos2D> possibleMoves = model.getBoard().possibleTileToMoveTo(firstPenguinPos, model.getPenguinPositions());
    List<Pos2D> orderedDirections;

    if (firstPenguinPos.getX() % 2 == 0) {
      int[] possibleX = {firstPenguinPos.getX(), firstPenguinPos.getX() + 1,
              firstPenguinPos.getX() + 1, firstPenguinPos.getX(), firstPenguinPos.getX() - 1,
              firstPenguinPos.getX() - 1};
      int[] possibleY = {firstPenguinPos.getY() - 1, firstPenguinPos.getY() - 1,
              firstPenguinPos.getY(), firstPenguinPos.getY() + 1, firstPenguinPos.getY(),
              firstPenguinPos.getY() - 1};
      orderedDirections = getValidDirections(model, possibleX, possibleY);
    } else {
      int[] possibleX = {firstPenguinPos.getX(), firstPenguinPos.getX() + 1,
              firstPenguinPos.getX() + 1, firstPenguinPos.getX(), firstPenguinPos.getX() - 1,
              firstPenguinPos.getX() - 1};
      int[] possibleY = {firstPenguinPos.getY() - 1, firstPenguinPos.getY(),
              firstPenguinPos.getY() + 1, firstPenguinPos.getY() + 1, firstPenguinPos.getY() + 1,
              firstPenguinPos.getY()};
      orderedDirections = getValidDirections(model, possibleX, possibleY);
    }
    return isThereAMove(model, firstPenguin, possibleMoves, orderedDirections);
  }

  /**
   * Determines if any of the positions in the list orderedDirections is found in the list possibleMoves.
   * Makes a move according to the first one found.
   *
   * @param model our game state
   * @param firstPenguin the penguin to move
   * @param possibleMoves the possible moves a player can make in the current game state
   * @param orderedDirections one step either North, NorthEast, SouthEast, South, SoutheWest, or
   *                          Northwest (in this order)
   * @return true if there is a possible silly player strategy move, false if not.
   */
  private static boolean isThereAMove(IFishModel model, Penguin firstPenguin, List<Pos2D> possibleMoves, List<Pos2D> orderedDirections) {
    for (Pos2D pos : orderedDirections) {
      if (possibleMoves.contains(pos)) {
        model.startGame();
        model.movePenguin(firstPenguin, pos);
        return true;
      }
    }
    return false;
  }


  /**
   * Creates a list of valid positions from possibleX and possibleY is found within the board.
   * possibleX and possibleY forms a pair of x and y-coordinates.
   *
   * @param model our game state
   * @param possibleX the possible x-coordinates from the silly player strategy
   * @param possibleY the possible y-coordinates from the silly player strategy
   * @return a list of valid positions
   */
  private static List<Pos2D> getValidDirections(IFishModel model, int[] possibleX, int[] possibleY) {
    List<Pos2D> orderedDirections = new ArrayList<>();
    for (int i = 0; i < possibleX.length; i++) {
      if (possibleX[i] >= 0 && possibleY[i] >= 0 && possibleX[i] <= model.getBoard().get2DTiles()[0].length
              && possibleY[i] <= model.getBoard().get2DTiles().length) {
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
  public static List<Player> getOurPlayers(JsonArray players) {
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
   * @param color the color each penguin should be in
   * @return a list of penguins in our own representation
   */
  public static List<Penguin> getOurPenguins(JsonArray places, Colour color) {
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
  public static Pos2D getOurPos2D(JsonArray position) {
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
  public static Board getOurBoard(JsonArray board) {
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
  public static Pieces[][] makeEmptyBoard(int ourRow, int ourCol) {
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
  public static void checkValidBoard(JsonArray board) {
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
  public static int getLongestRow(int rows, JsonArray board) {
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