import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Model.Board;
import Model.Pieces;
import Model.Pos2D;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class BoardTest {
  Board board1 = new Board(1, 1, 3);
  Board board2 = new Board(4, 8, 5);
  Board board3 = new Board(10, 5, 4);
  Board board4 = new Board(6, 6, 1);
  Pos2D pos1 = new Pos2D(1, 2);
  Pos2D pos2 = new Pos2D(3, 3);
  Pos2D pos3 = new Pos2D(2, 3);
  Pos2D pos4 = new Pos2D(3, 1);
  List<Pos2D> holesList = new ArrayList<Pos2D>();
  List<Pos2D> holesList2 = new ArrayList<Pos2D>();

  @Before
  public void init() {
    holesList.add(pos1);
    holesList.add(pos2);
    holesList.add(pos3);
    holesList2.add(pos4);
  }

  // checks all the tile in the board has the same fish number
  @Test
  public void testGenerateFullBoard1() {
    Pieces[][] board = board1.get2DTiles();
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        Pieces currentPiece = board[i][j];
        assertEquals(currentPiece.getFishNum(), 3);
      }
    }
  }

  // checks all the tile in the board has the same fish number and is not a hole
  @Test
  public void testGenerateFullBoard2() {
    Pieces[][] board = board2.get2DTiles();
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        Pieces currentPiece = board[i][j];
        assertEquals(currentPiece.getFishNum(), 5);
      }
    }
  }

  // checks invalid hole positions and if it added to the list
  @Test
  public void checkHolePosition1() {
    List<Pos2D> holesList = new ArrayList<Pos2D>();
    Pos2D hole1 = new Pos2D(11, 2);
    Pos2D hole2 = new Pos2D(8, 3);
    Pos2D hole3 = new Pos2D(2, 9);
    holesList.add(hole1);
    holesList.add(hole2);
    holesList.add(hole3);
    board3.checkHolePosition(10, 5, holesList);
    List<Pos2D> ans = new ArrayList<Pos2D>();
    ans.add(hole2);
    assertEquals(holesList, ans);
  }

  // test checkValidMinNum1FishTile method
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidMinNum1FishTile() {
    new Board(4, 4, holesList, 2, 1, 15);
  }

  // test checkValidMinNum1FishTile method, some tiles position not in bounds, so it was removed
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidMinNum1FishTile2() {
    new Board(2, 2, holesList, 2, 1, 4);
  }

  // checks removeTile method
  @Test
  public void testRemove() {
    Pos2D pos = new Pos2D(1, 2);
    board2.removeTile(pos);
    assertEquals(board2.get2DTiles()[2][1].getFishNum(), 0);
  }

  // checks if the board generated has the correct number of holes
  @Test
  public void testHoleBoard1() {
    int count = 0;
    int OneFishCount = 0;
    Board holeBoard1 = new Board(10, 10, holesList, 2, 1, 1);
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        if (holeBoard1.get2DTiles()[i][j].getFishNum() == 0) {
          count++;
        } else if (holeBoard1.get2DTiles()[i][j].getFishNum() == 1) {
          OneFishCount++;
        }
      }
    }
    assertTrue(count == holesList.size());
    assertTrue(OneFishCount >= 1);
  }

  // checks if the board generated has holes at the right places
  @Test
  public void testHoleBoard2() {
    Board holeBoard1 = new Board(10, 10, holesList, 2, 1, 1);
    for (int i = 0; i < holesList.size(); i++) {
      int x = holesList.get(i).getX();
      int y = holesList.get(i).getY();
      assertEquals(holeBoard1.get2DTiles()[y][x].getFishNum(), 0);
    }
  }

  // checks if the board generated has the correct number of fish in each tile
  @Test
  public void testHoleBoard3() {
    Board holeBoard1 = new Board(4, 4, holesList, 2, 1, 1);
    List<Integer> fishNums = new ArrayList<Integer>();
    List<Integer> ans = new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
    int numOneFishTiles = 0;
    for (int i = 0; i < holeBoard1.get2DTiles().length; i++) {
      for (int j = 0; j < holeBoard1.get2DTiles()[0].length; j++) {
        if (holeBoard1.get2DTiles()[i][j].getFishNum() != 0) {
          fishNums.add(holeBoard1.get2DTiles()[i][j].getFishNum());
        }

        if (holeBoard1.get2DTiles()[i][j].getFishNum() == 1) {
          numOneFishTiles++;
        }
      }
    }
    assertEquals(fishNums.size(), 13);
    assertTrue(numOneFishTiles > 1);
  }

  // checks the possible moves, no penguins
  @Test
  public void testPossibleTilesBeforeHole() {
    Board holeBoard1 = new Board(4, 4, holesList2, 2, 2, 1);
    List<Pos2D> tiles = holeBoard1.possibleTileToMoveTo(new Pos2D(1, 1), new ArrayList<>());
    assertEquals(tiles.size(), 9);
    List<Pos2D> ans = new ArrayList<Pos2D>(Arrays.asList(new Pos2D(0,1), new Pos2D(1,0),
            new Pos2D(0,2), new Pos2D(1,2), new Pos2D(2,1), new Pos2D(3,0),
            new Pos2D(3,2), new Pos2D(1,3), new Pos2D(2,2)));
    assertTrue(tiles.containsAll(ans));
  }

  // checks the possible moves, no penguins
  @Test
  public void testPossibleTilesBeforeHole2() {
    Board holeBoard1 = new Board(4, 4, holesList2, 2, 2, 1);
    List<Pos2D> tiles = holeBoard1.possibleTileToMoveTo(new Pos2D(3, 3), new ArrayList<>());
    assertEquals(tiles.size(), 4);
    List<Pos2D> ans = new ArrayList<Pos2D>(Arrays.asList(new Pos2D(3,2), new Pos2D(2,3),
            new Pos2D(1,2), new Pos2D(0,2)));
    assertTrue(tiles.containsAll(ans));
  }

  // checks the possible moves, no penguins
  @Test
  public void testPossibleTilesBeforeHole3() {
    Board holeBoard1 = new Board(4, 4, holesList2, 2, 2, 1);
    List<Pos2D> tiles = holeBoard1.possibleTileToMoveTo(new Pos2D(1, 3), new ArrayList<>());
    assertEquals(tiles.size(), 6);
    List<Pos2D> ans = new ArrayList<Pos2D>(Arrays.asList(new Pos2D(0,3), new Pos2D(2,3),
            new Pos2D(3,2), new Pos2D(1,2), new Pos2D(1,1), new Pos2D(1,0)));
    assertTrue(tiles.containsAll(ans));
  }

  // checks the possible moves, with penguins
  @Test
  public void testPossibleTilesBeforePenguin() {
    Board holeBoard1 = new Board(4, 4, holesList2, 2, 2, 1);
    List<Pos2D> pos = new ArrayList<>();
    pos.add(new Pos2D(1,2));
    List<Pos2D> tiles = holeBoard1.possibleTileToMoveTo(new Pos2D(1, 3), pos);
    assertEquals(tiles.size(), 3);
    List<Pos2D> ans = new ArrayList<Pos2D>(Arrays.asList(new Pos2D(0,3), new Pos2D(2,3),
            new Pos2D(3,2)));
    assertTrue(tiles.containsAll(ans));
  }

  // checks checkValidPos
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidPos() {
    board1.checkValidPos(1, 1);
  }

  // checks checkValidPos
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidPos1() {
    Board board = new Board(2, 1,1);
    board.checkValidPos(0, 2);
  }

  // checks checkValidPos
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidPos2() {
    Board board = new Board(1, 2,1);
    board.checkValidPos(2, 0);
  }

  // checks checkValidMove (invalid position not in straight lines)
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidMove1() {
    List<Pos2D> pos = new ArrayList<Pos2D>();
    pos.add(new Pos2D(1, 1));
    board4.checkValidMove(new Pos2D(3, 4), new Pos2D(4, 1), pos);
  }

  // checks checkValidMove (invalid position due to penguin position)
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidMove2() {
    List<Pos2D> pos = new ArrayList<Pos2D>();
    pos.add(new Pos2D(3, 3));
    board4.checkValidMove(new Pos2D(3, 4), new Pos2D(3, 3), pos);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPosXTooBig() {
    Pos2D pos = new Pos2D(9, 5);
    board2.checkPosInBoard(pos);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPosXTooSmall() {
    Pos2D pos = new Pos2D(-1, 1);
    board2.checkPosInBoard(pos);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPosYTooBig() {
    Pos2D pos = new Pos2D(0, 8);
    board2.checkPosInBoard(pos);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPosYTooSmall() {
    Pos2D pos = new Pos2D(0, -5);
    board2.checkPosInBoard(pos);
  }

}