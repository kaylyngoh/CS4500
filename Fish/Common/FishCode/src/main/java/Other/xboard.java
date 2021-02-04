package Other;

import Model.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents the test harness to check if the possible number of moves of our game is correct.
 * The harness consumes a well-formed and valid JSON input and outputs the number of tiles on the
 * board that can be reached from the specified position.
 * Input: { "position" : Position, "board" : Model.Board} where Position is a JSON array that contains
 * two natural numbers: board-row,board-column] and Model.Board is a JSON array of JSON arrays where
 * each element is either 0 or a number between 1 and 5.
 */
public class xboard {

  /**
   * Reads and parses input and outputs the possible number of tiles the avatar can move to from
   * the given position.
   * @param args input
   */
  public static void main(String[] args) {
    // reads the input stream and parses it into two json arrays: board and position
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    JsonObject BoardPosn = (JsonObject) JsonParser.parseReader(reader);
    JsonArray board = BoardPosn.getAsJsonArray("board");
    JsonArray position = BoardPosn.getAsJsonArray("position");
    // checks if the board does not exceed 25 tiles
    checkValidBoard(board);
    // converts into our own our version of position
    Pos2D ourPos = getOurPos2D(position);
    // converts into our version of board
    Board ourBoard = getOurBoard(board);
    // determines the possible number of tiles the avatar can move to from the given position
    int possibleNumberOfMoves = ourBoard.possibleTileToMoveTo(ourPos, new ArrayList<>()).size();
    System.out.print(possibleNumberOfMoves);
  }

  /**
   * Converts the board of given data representation into our own data representation of a board,
   * using our own coordinate system.
   *
   * @param board  board in given data representation
   * @return  board in our own data representation
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
   * @param rows the number of rows in the given board
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

  /**
   * Converts position in given data representation into our own representation using our own
   * coordinate system
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
}