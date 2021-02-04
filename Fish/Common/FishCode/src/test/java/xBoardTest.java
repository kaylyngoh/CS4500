import com.google.gson.JsonArray;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import Model.Board;
import Model.Hole;
import Model.Pieces;
import Model.Pos2D;
import Model.Tile;
import Other.xboard;

import static org.junit.Assert.assertEquals;

public class xBoardTest {

  private final InputStream systemIn = System.in;
  private final PrintStream systemOut = System.out;
  private ByteArrayInputStream testIn;
  private ByteArrayOutputStream testOut;

  JsonArray givenBoard = new JsonArray();
  Pieces[][] ourTiles = xboard.makeEmptyBoard(2,6);
  Board ourBoard = new Board(ourTiles);

  @Before
  public void setUpOutput() {
    testOut = new ByteArrayOutputStream();
    System.setOut(new PrintStream(testOut));

    JsonArray row1 = new JsonArray();
    row1.add(1);
    row1.add(0);
    row1.add(1);
    JsonArray row2 = new JsonArray();
    row2.add(2);
    row2.add(1);
    row2.add(3);
    JsonArray row3 = new JsonArray();
    row3.add(1);
    row3.add(1);
    row3.add(1);
    givenBoard.add(row1);
    givenBoard.add(row2);
    givenBoard.add(row3);
    ourTiles[0][0] = new Tile(1);
    ourTiles[0][2] = new Hole();
    ourTiles[0][4] = new Tile(1);
    ourTiles[0][1] = new Tile(2);
    ourTiles[0][3] = new Tile(1);
    ourTiles[0][5] = new Tile(3);
    ourTiles[1][0] = new Tile(1);
    ourTiles[1][2] = new Tile(1);
    ourTiles[1][4] = new Tile(1);
  }

  private void provideInput(String data) {
    testIn = new ByteArrayInputStream(data.getBytes());
    System.setIn(testIn);
  }

  private String getOutput() {
    return testOut.toString();
  }

  @After
  public void restoreSystemInputOutput() {
    System.setIn(systemIn);
    System.setOut(systemOut);
  }

  // test read, parse and print
  @Test
  public void testCase1() {
    String testString = "{ \"position\" : [1, 1],\n \"board\" : [[1, 2, 3], [0, 1, 2], [5, 1, 2]]}";
    provideInput(testString);

    xboard.main(new String[0]);

    assertEquals(getOutput(), "4");
  }

  // test method to make an empty board
  @Test
  public void testMakeEmptyBoard() {
    Pieces[][] emptyBoard = xboard.makeEmptyBoard(2,3);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(emptyBoard[i][j].getFishNum(), 0);
      }
    }
  }

  // test conversion of given data representation of board to our representation of board
  @Test
  public void testConversionBoard() {
    for (int i = 0; i < ourBoard.get2DTiles().length; i++) {
      for (int j = 0; j < ourBoard.get2DTiles()[0].length; j++) {
        assertEquals(ourBoard.get2DTiles()[i][j].getFishNum(), xboard.getOurBoard(givenBoard).get2DTiles()[i][j].getFishNum());
      }
    }
  }

  // test get longest row method
  @Test
  public void testLongestRowMethod() {
    JsonArray row = new JsonArray();
    row.add(1);
    row.add(1);
    row.add(1);
    row.add(1);
    row.add(1);
    givenBoard.add(row);
    assertEquals(xboard.getLongestRow(4,givenBoard), 3);
  }

  // test if it's less than 25 tiles total
  @Test(expected = IllegalArgumentException.class)
  public void testValidBoard() {
    JsonArray row = new JsonArray();
    row.add(1);
    row.add(1);
    row.add(1);
    row.add(1);
    row.add(1);
    row.add(1);
    row.add(1);
    givenBoard.add(row);
    xboard.checkValidBoard(givenBoard);
  }

  // test conversion of given data representation of position to our representation of position
  @Test
  public void testConversionPosition() {
    JsonArray position = new JsonArray();
    position.add(1);
    position.add(1);
    assertEquals(xboard.getOurPos2D(position), new Pos2D(3, 0));
  }

  // test conversion of given data representation of position to our representation of position
  @Test
  public void testConversionPosition1() {
    JsonArray position = new JsonArray();
    position.add(0);
    position.add(1);
    assertEquals(xboard.getOurPos2D(position), new Pos2D(2, 0));
  }

  // test conversion of given data representation of position to our representation of position
  @Test
  public void testConversionPosition2() {
    JsonArray position = new JsonArray();
    position.add(2);
    position.add(0);
    assertEquals(xboard.getOurPos2D(position), new Pos2D(0, 1));
  }

}