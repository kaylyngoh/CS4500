package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import javafx.geometry.Pos;

/**
 * Represents the 2D board used to play View.Fish, consisting of pieces of either tiles and holes.
 */
public class Board {
  private final Pieces[][] board;
  private final Random random;

  //constants
  public static final int fishSize = 15;
  public static final int maxFishNum = 5;
  // tile size is the radius of a hexagon
  public static final int tileSize = fishSize * maxFishNum;

  /**
   * Makes a full 2D board with r number of rows and c number of columns with each tile having
   * fishNum number of fish.
   */
  public Board(int r, int c, int fishNum) {
    this.board = new Pieces[r][c];
    generateFullBoard(r, c, fishNum);
    this.random = new Random();
  }

  /**
   * Makes a 2D board with r number of rows and c number of columns with each tile having
   * fishNum number of fish and with holes at the given positions as in the list.
   */
  public Board(int r, int c, int fishNum, List<Pos2D> holes) {
    this.board = new Pieces[r][c];
    generateBoardWithHolesAndSameFish(r, c, fishNum, holes);
    this.random = new Random();
  }

  /**
   * Makes a 2D board with r number of rows and c number of columns with a given minimum number of
   * 1-fish tiles and other tiles having a random number of fishes using maxFishNum as an upper
   * bound. The board will be built will holes in the given positions.
   */
  public Board(int r, int c, List<Pos2D> holes, int maxFishNum, int minNumOneFishTile) {
    this.random = new Random();
    checkHolePosition(r, c, holes);
    this.board = new Pieces[r][c];
    generateHoleBoard(r, c, holes, maxFishNum, minNumOneFishTile);
  }

  /**
   * Constructor used for testing with random: Makes a 2D board with r number of rows and c number
   * of columns with a given minimum number of 1-fish tile and other tiles having a random number of
   * fishes using maxFishNum as an upper bound and seed as the seed for the random generator.
   * The board will be built will holes in the given positions.
   */
  public Board(int row, int col, List<Pos2D> holes, int maxFishNum, int seed, int minNumOneFishTile) {
    this.random = new Random(seed);
    checkHolePosition(row, col, holes);
    this.board = new Pieces[row][col];
    generateHoleBoard(row, col, holes, maxFishNum, minNumOneFishTile);
  }

  /**
   * Copy constructor for board.
   */
  public Board(Pieces[][] pieces) {
    this.random = new Random();
    this.board = pieces;
  }

  /**
   * A getter method for board.
   *
   * @return the board which is a 2d array list of Model.Tile
   */
  public Pieces[][] get2DTiles() {
    Pieces[][] clone = new Pieces[this.board.length][this.board[0].length];
    for (int i = 0; i < this.board.length; i++) {
      for (int j = 0; j < this.board[0].length; j++) {
        clone[i][j] = this.board[i][j].replicatePiece();
      }
    }
    return clone;
  }

  /**
   * Generates a full board that has the same number of fish on every tile and has no holes.
   *
   * @param row     number of rows for the board
   * @param col     number of columns for the board
   * @param fishNum number of fish on each tile
   */
  private void generateFullBoard(int row, int col, int fishNum) {
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        board[i][j] = new Tile(fishNum);
      }
    }
  }

  /**
   * Generates a board that has the same number of fish on every tile and has holes at the specified positions.
   *
   * @param row     number of rows for the board
   * @param col     number of columns for the board
   * @param fishNum number of fish on each tile
   * @param holes positions of holes in the board
   */
  private void generateBoardWithHolesAndSameFish(int row, int col, int fishNum, List<Pos2D> holes) {
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        Pos2D currPos = new Pos2D(j, i);
        if (holes.contains(currPos)) {
          board[i][j] = new Hole();
        } else {
          board[i][j] = new Tile(fishNum);
        }
      }
    }
  }

  /**
   * Generates a board that has holes in specific places and is set up with a minimum number of
   * 1-fish tiles, using a random generator to determine the number of fishes on other boards to be
   * found on a single tile where the range should be in between 1 and given maximum fish number.
   *
   * @param row               number of rows for the board
   * @param col               number of columns for the board
   * @param maxFishNum        the maximum number of fishes in a single tile
   * @param holes             the list of positions/index of the holes in the board
   * @param minNumOneFishTile the minimum number of 1-fish tile
   * @return a 2d array board
   */
  private void generateHoleBoard(int row, int col, List<Pos2D> holes, int maxFishNum, int minNumOneFishTile) {
    checkValidMinNum1FishTile((row * col), minNumOneFishTile, holes.size());
    int numOf1FishTileToCreate = minNumOneFishTile;
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        Pos2D tilePos = new Pos2D(j, i);
        if (!holes.contains(tilePos)) {
          if(numOf1FishTileToCreate > 0) {
            board[i][j] = new Tile(1);
            numOf1FishTileToCreate--;
          } else {
            int fishNum = random.nextInt(maxFishNum) + 1;
            board[i][j] = new Tile(fishNum);
          }
//          numOf1FishTileToCreate -= createTileWithOneOrRandomFishNum(maxFishNum, i, j, numOf1FishTileToCreate);
        } else {
          board[i][j] = new Hole();
        }
      }
    }
  }

  /**
   * Checks if the board has enough tiles to satisfy minimum number of 1-fish tiles requirement,
   * after taking into account the number of holes given.
   *
   * @param totalTiles        the total number of position available on the board
   * @param holeListSize      the number of holes
   * @param minNumOneFishTile the minimum number of 1-fish tile
   * @throws IllegalArgumentException if the given number of minimum number of 1-fish tiles is too
   *                                  big.
   */
  private void checkValidMinNum1FishTile(int totalTiles, int minNumOneFishTile, int holeListSize) {
    if (minNumOneFishTile > (totalTiles - holeListSize)) {
      throw new IllegalArgumentException("Given number of minimum number of 1-fish tiles is too big");
    }
  }

//  /**
//   * Initialize a tile in the given position on the board with a tile with either one or a random
//   * number of fish. If there are still 1-fish tiles needed to be created (numOf1FishTileToCreate >
//   * 0), we will determine if this position should be a 1-fish tile using a random boolean
//   * generator.
//   *
//   * @param maxFishNum             the maximum fish number that can be found in any tile on the
//   *                               board
//   * @param i                      the row on the board
//   * @param j                      the column on the board
//   * @param numOf1FishTileToCreate the number of 1 fish tile still needed to be created
//   * @return 1 if a 1-fish tile is created, 0 if not
//   */
//  private int createTileWithOneOrRandomFishNum(int maxFishNum, int i, int j, int numOf1FishTileToCreate) {
//    // 0 = make a tile with random number of fish
//    // 1 = make a tile with 1 number of fish
//    int oneOrRandom = this.random.nextInt(2);
//    if (numOf1FishTileToCreate > 0 && oneOrRandom == 1) {
//      board[i][j] = new Model.Tile(1);
//    } else {
//      int fishNum = random.nextInt(maxFishNum) + 1;
//      board[i][j] = new Model.Tile(fishNum);
//    }
//    return oneOrRandom;
//  }

  /**
   * Checks whether the given list of holes positions are available. If the position is greater than
   * board row or board column, or if it is less than 0, removes it from the list.
   *
   * @param row   number of rows for the board
   * @param col   number of columns for the board
   * @param holes the list of positions/index of the holes in the board
   */
  public void checkHolePosition(int col, int row, List<Pos2D> holes) {
    for (int i = 0; i < holes.size(); i++) {
      Pos2D hole = holes.get(i);
      int x = hole.getX();
      int y = hole.getY();
      if (x >= col || y >= row) {
        holes.remove(hole);
      }
    }
  }

  /**
   * Removes a tile from a board, given the position of the tile.
   *
   * @param position position of the tile to remove
   * @throws IllegalArgumentException if the position is outside of the board or a hole.
   */
  public void removeTile(Pos2D position) {
    int x = position.getX();
    int y = position.getY();
    checkValidPos(x, y);
    this.board[y][x] = new Hole();
  }

  /**
   * Checks if the position given is valid, if it is inside the board and not a hole.
   *
   * @param x x-axis of the position
   * @param y y-axis of the position
   * @throws IllegalArgumentException if the position is outside of the board or a hole.
   */
  public void checkValidPos(int x, int y) {
    if (x >= this.board[0].length || y >= this.board.length) {
      throw new IllegalArgumentException("Position not on board");
    } else {
      this.board[y][x].checkIfPosIsHole();
    }
  }

  /**
   * Determines the tiles reachable from a given position via straight lines up to but not
   * including holes in the board and penguins from the given list of penguins on tiles.
   * Please read the README for more specifications on this method.
   *
   * @param position position on the board
   * @param penguinPositions positions of penguins that are found on the board
   * @return list of position reachable from the given position
   */
  public List<Pos2D> possibleTileToMoveTo(Pos2D position, List<Pos2D> penguinPositions) {
    int x = position.getX();
    int y = position.getY();
    List<Pos2D> possibleMoves = new ArrayList<Pos2D>();

    // i and j determines the direction of the straight line
    // i = -1 and j = -1 -> upper left
    // i = -1 and j = 0 -> top
    // i = -1 and j = 1 -> upper right
    // i = 1 and j = -1 -> lower left
    // i = 1 and j = 0 -> bottom
    // i = 1 and j = 1 -> lower right

    possibleMoves.addAll(addTopDownTiles(-1, x, y, penguinPositions));
    possibleMoves.addAll(addTopDownTiles(1, x, y, penguinPositions));

    if (x % 2 == 0) {
      possibleMoves.addAll(addDiagonalTiles(-1, 1, x + 1, y - 1, true, penguinPositions));
      possibleMoves.addAll(addDiagonalTiles(-1, -1, x - 1, y - 1, true, penguinPositions));
      possibleMoves.addAll(addDiagonalTiles(1, 1, x + 1, y, false, penguinPositions));
      possibleMoves.addAll(addDiagonalTiles(1, -1, x - 1, y, false, penguinPositions));
    } else {
      possibleMoves.addAll(addDiagonalTiles(-1, 1, x + 1, y, false, penguinPositions));
      possibleMoves.addAll(addDiagonalTiles(-1, -1, x - 1, y, false, penguinPositions));
      possibleMoves.addAll(addDiagonalTiles(1, 1, x + 1, y + 1, true, penguinPositions));
      possibleMoves.addAll(addDiagonalTiles(1, -1, x - 1, y + 1, true, penguinPositions));
    }
    return possibleMoves;
  }


  /**
   * Determines the tiles reachable via straight lines (vertically) from a given position(x, y)
   * before a hole or penguin from the given list of penguins is found.
   *
   * @param i the row direction (-1: up, 1: down)
   * @param x the current row
   * @param y the current column
   * @param penguinPositions positions of penguins that are found on the board
   * @return list of position of tiles reachable from the given position before a hole
   */
  private List<Pos2D> addTopDownTiles(int i, int x, int y, List<Pos2D> penguinPositions) {
    int newY = y + i;
    List<Pos2D> possiblePos = new ArrayList<Pos2D>();

    while (0 <= newY && newY < this.board.length) {
      Pieces currentPiece = this.board[newY][x];
      if ((currentPiece instanceof Tile) && (!(penguinPositions.contains(new Pos2D(x, newY))))) {
        possiblePos.add(new Pos2D(x, newY));
        newY = newY + i;
      } else {
        break;
      }
    }
    return possiblePos;
  }


  /**
   * Determines the tiles reachable via diagonal straight lines from a given position(x, y) before a
   * hole or penguin from the given list of penguins is found.
   *
   * @param i            the row direction (-1: up, 1: down)
   * @param j            the column direction (-1: left, 1: right)
   * @param x            the row of the next possible tile to move to
   * @param y            the column of the next possible tile to move to
   * @param startAddSide if we are to add the tile on the side or diagonally for the next move
   * @param penguinPositions positions of penguins that are found on the board
   * @return list of position of tiles reachable from the given position before a hole
   */
  private List<Pos2D> addDiagonalTiles(int i, int j, int x, int y, boolean startAddSide, List<Pos2D> penguinPositions) {
    List<Pos2D> possiblePos = new ArrayList<Pos2D>();
    boolean addSide = startAddSide;
    int newX = x;
    int newY = y;

    while (0 <= newX && newX < this.board[0].length && 0 <= newY && newY < this.board.length) {
      Pieces currentPiece = this.board[newY][newX];
      if ((currentPiece instanceof Tile) && (!(penguinPositions.contains(new Pos2D(newX, newY))))) {
        possiblePos.add(new Pos2D(newX, newY));
        newX = newX + j;
        if (addSide) {
          addSide = false;
        } else {
          newY = newY + i;
          addSide = true;
        }
      } else {
        break;
      }
    }
    return possiblePos;
  }


  /**
   * Checks to see if the move from current position to the next position is valid.
   *
   * @param currentPos the current position
   * @param nextPos    the next position
   * @throws IllegalArgumentException if the move is not valid
   */
  public void checkValidMove(Pos2D currentPos, Pos2D nextPos, List<Pos2D> penguinPositions) {
    if (!(possibleTileToMoveTo(currentPos, penguinPositions).contains(nextPos))) {
      throw new IllegalArgumentException("Not a valid move");
    }
  }

  /**
   * Checks to see if the placement is valid.
   * @param toPlace Position where the penguin will be placed.
   * @param penguinPositions List of all the positions of the penguins on the board.
   */
  public void checkValidPlacement(Pos2D toPlace, List<Pos2D> penguinPositions) {
    try {
      this.checkPosInBoard(toPlace);
      this.checkPenguinExistsAtTile(toPlace, penguinPositions);
    }
    catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Cannot place penguin at the given position");
    }
  }

  /**
   * Checks to see if there is already a penguin at the tile.
   * @param toPlace Position where the penguin will be placed.
   * @param penguinPositions List of all the positions of the penguins on the board.
   */
  public void checkPenguinExistsAtTile(Pos2D toPlace, List<Pos2D> penguinPositions) {
    for (Pos2D position : penguinPositions) {
      if (toPlace.equals(position)) {
        throw new IllegalArgumentException("Cannot place a penguin where there is already a penguin");
      }
    }
  }

  /**
   * Checks that the input position is valid given the board. Throws an exception if the position is not in the board.
   * @param position Pos2D representing the position we are checking in the board.
   */
  public void checkPosInBoard(Pos2D position) {
    if (position.getY() < 0 || position.getY() >= board.length
    || position.getX() < 0 || position.getX() >= board[position.getY()].length) {
      throw new IllegalArgumentException("Position is outside the board.");
    }
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }
    if (!(a instanceof Board)) {
      return false;
    }
    Board that = (Board) a;
    boolean isSameBoard = true;
    if (this.board.length == that.board.length) {
      if (this.board[0].length == that.board[0].length) {
        for (int i = 0; i < this.board.length; i++) {
          for (int j = 0; j < this.board[0].length; j++) {
            if (this.board[i][j].getFishNum() != that.board[i][j].getFishNum()) {
              isSameBoard = false;
              break;
            }
          }
        }
      } else {
        isSameBoard = false;
      }
    } else {
      isSameBoard = false;
    }
    return isSameBoard;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.board);
  }
}