import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import Model.Board;
import Model.Colour;
import Model.FishModel;
import Model.Hole;
import Model.IFishModel;
import Model.Penguin;
import Model.Pieces;
import Model.Player;
import Model.Pos2D;
import Model.Tile;
import Other.xstate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class xStateTest {

  private final InputStream systemIn = System.in;
  private final PrintStream systemOut = System.out;
  private ByteArrayInputStream testIn;
  private ByteArrayOutputStream testOut;

  JsonArray givenBoard = new JsonArray();
  Pieces[][] ourTiles = xstate.makeEmptyBoard(2,6);
  Board ourBoard = new Board(ourTiles);
  Player player1;
  Player player2;
  List<Player> playerList = new ArrayList<>();
  Penguin penguin1;
  Penguin penguin2;
  Penguin penguin3;
  Penguin penguin4;
  List<Penguin> penguinList1 = new ArrayList<>();
  List<Penguin> penguinList2 = new ArrayList<>();
  FishModel model1;

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

    penguin1 = new Penguin(Colour.RED, new Pos2D(0, 0));
    penguin2 = new Penguin(Colour.RED, new Pos2D(3, 0));
    penguin3 = new Penguin(Colour.WHITE, new Pos2D(4, 1));
    penguin4 = new Penguin(Colour.WHITE, new Pos2D(2, 1));

    penguinList1.add(penguin1);
    penguinList1.add(penguin2);
    penguinList2.add(penguin3);
    penguinList2.add(penguin4);

    player1 = new Player(13, Colour.RED, 1, penguinList1);
    player2 = new Player(16, Colour.WHITE, 0, penguinList2);

    playerList.add(player1);
    playerList.add(player2);

    model1 = new FishModel(ourBoard, playerList);
    model1.startGame();
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
    String testString = "{ \"players\" : [{\"color\": \"red\", \"score\": 4, \"places\": [[2,1],[0,1],[1,1],[1,2]]}, {\"color\": \"white\", \"score\": 5, \"places\": [[0,2],[3,1],[4,0],[2,2]]}],\n" +
            "  \"board\" : [[1, 2, 3], [0, 1, 2], [5, 2, 2], [4, 3, 5], [2, 1, 2]]}";
    provideInput(testString);

    xstate.main(new String[0]);

    assertEquals(getOutput(), "{\"board\":[[1,2,3],[0,1,2],[5,0,2],[4,3,5],[2,1,2]],\"players\":[{\"color\":\"white\",\"score\":5,\"places\":[[0,2],[3,1],[4,0],[2,2]]},{\"color\":\"red\",\"score\":6,\"places\":[[4,1],[0,1],[1,1],[1,2]]}]}");
  }

  // test method to make an empty board
  @Test
  public void testMakeEmptyBoard() {
    Pieces[][] emptyBoard = xstate.makeEmptyBoard(2,3);
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
        assertEquals(ourBoard.get2DTiles()[i][j].getFishNum(), xstate.getOurBoard(givenBoard).get2DTiles()[i][j].getFishNum());
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
    assertEquals(xstate.getLongestRow(4,givenBoard), 3);
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
    xstate.checkValidBoard(givenBoard);
  }

  // test conversion of given data representation of position to our representation of position
  @Test
  public void testConversionPosition() {
    JsonArray position = new JsonArray();
    position.add(1);
    position.add(1);
    assertEquals(xstate.getOurPos2D(position), new Pos2D(3, 0));
  }

  // test conversion of given data representation of position to our representation of position
  @Test
  public void testConversionPosition1() {
    JsonArray position = new JsonArray();
    position.add(0);
    position.add(1);
    assertEquals(xstate.getOurPos2D(position), new Pos2D(2, 0));
  }

  // test conversion of given data representation of position to our representation of position
  @Test
  public void testConversionPosition2() {
    JsonArray position = new JsonArray();
    position.add(2);
    position.add(0);
    assertEquals(xstate.getOurPos2D(position), new Pos2D(0, 1));
  }

  // test formatting output board
  @Test
  public void testFormatting() {
    JsonArray input = new JsonArray();
    JsonArray row1 = new JsonArray();
    input.add(row1);
    JsonArray output = new JsonArray();
    JsonArray row2 = new JsonArray();
    output.add(row2);
    JsonArray row3 = new JsonArray();
    output.add(row3);
    assertEquals(xstate.reformatOutputBoard(output,input).size(),1);
  }

  // test converting from our version of players to output version
  @Test
  public void testGeneratePlayerOutput() {
    JsonArray ans = new JsonArray();
    JsonObject player1 = new JsonObject();
    player1.add("color", new JsonPrimitive("red"));
    player1.add("score", new JsonPrimitive(1));
    JsonArray player1Penguins = new JsonArray();
    JsonArray player1Penguins1 = new JsonArray();
    player1Penguins1.add(0);
    player1Penguins1.add(0);
    player1Penguins.add(player1Penguins1);
    JsonArray player1Penguins2 = new JsonArray();
    player1Penguins2.add(1);
    player1Penguins2.add(1);
    player1Penguins.add(player1Penguins2);
    player1.add("places", player1Penguins);

    JsonObject player2 = new JsonObject();
    player2.add("color", new JsonPrimitive("white"));
    player2.add("score", new JsonPrimitive(0));
    JsonArray player2Penguins = new JsonArray();
    JsonArray player2Penguins1 = new JsonArray();
    player2Penguins1.add(2);
    player2Penguins1.add(2);
    player2Penguins.add(player2Penguins1);
    JsonArray player2Penguins2 = new JsonArray();
    player2Penguins2.add(2);
    player2Penguins2.add(1);
    player2Penguins.add(player2Penguins2);
    player2.add("places", player2Penguins);
    ans.add(player2);
    ans.add(player1);

    assertEquals(xstate.generatePlayerOutput(model1), ans);
  }

  // test converting from our version of board to output version
  @Test
  public void testGenerateBoardOutput() {
    JsonArray ans = new JsonArray();
    JsonArray row1 = new JsonArray();
    row1.add(1);
    row1.add(0);
    row1.add(1);
    ans.add(row1);
    JsonArray row2 = new JsonArray();
    row2.add(2);
    row2.add(1);
    row2.add(3);
    ans.add(row2);
    JsonArray row3 = new JsonArray();
    row3.add(1);
    row3.add(1);
    row3.add(1);
    ans.add(row3);
    JsonArray row4 = new JsonArray();
    row4.add(0);
    row4.add(0);
    row4.add(0);
    ans.add(row4);

    assertEquals(xstate.generateBoardOutput(model1), ans);
  }

  // test silly player strategy
  @Test
  public void testSillyPlayerStrategy() {
    IFishModel copy = new FishModel(model1);
    copy.movePenguin(penguin1, new Pos2D(1,0));
    assertTrue(xstate.sillyPlayerStrategy(model1));
    assertEquals(model1, copy);
  }

  // test getOurPenguins method
  @Test
  public void testGetOurPenguins(){
    List<Penguin> ans = new ArrayList<Penguin>();
    ans.add(penguin1);
    ans.add(new Penguin(Colour.RED, new Pos2D(2,0)));
    JsonArray penguins = new JsonArray();
    JsonArray penguin1Pos = new JsonArray();
    JsonArray penguin2Pos = new JsonArray();
    penguin1Pos.add(0);
    penguin1Pos.add(0);
    penguins.add(penguin1Pos);
    penguin2Pos.add(0);
    penguin2Pos.add(1);
    penguins.add(penguin2Pos);
    assertEquals(xstate.getOurPenguins(penguins, Colour.RED), ans);

  }

  // test getOurPlayers method
  @Test
  public void testGetOurPlayers(){
    List<Player> ans = new ArrayList<>();
    List<Penguin> penguinsans = new ArrayList<>();
    JsonObject player1 = new JsonObject();
    player1.add("color", new JsonPrimitive("red"));
    player1.add("score", new JsonPrimitive(1));
    JsonArray penguins = new JsonArray();
    JsonArray penguin1Pos = new JsonArray();
    JsonArray penguin2Pos = new JsonArray();
    penguin1Pos.add(0);
    penguin1Pos.add(0);
    penguins.add(penguin1Pos);
    penguin2Pos.add(0);
    penguin2Pos.add(1);
    penguins.add(penguin2Pos);
    player1.add("places", penguins);
    penguinsans.add(new Penguin(Colour.RED, new Pos2D(0,0)));
    penguinsans.add(new Penguin(Colour.RED, new Pos2D(2,0)));
    ans.add(new Player(1, Colour.RED, 1, penguinsans));

    JsonObject player2 = new JsonObject();
    List<Penguin> penguinsans2 = new ArrayList<>();
    player2.add("color", new JsonPrimitive("white"));
    player2.add("score", new JsonPrimitive(0));
    JsonArray player2Penguins = new JsonArray();
    JsonArray player2Penguins1 = new JsonArray();
    player2Penguins1.add(1);
    player2Penguins1.add(0);
    player2Penguins.add(player2Penguins1);
    JsonArray player2Penguins2 = new JsonArray();
    player2Penguins2.add(3);
    player2Penguins2.add(0);
    player2Penguins.add(player2Penguins2);
    player2.add("places", player2Penguins);
    penguinsans2.add(new Penguin(Colour.WHITE, new Pos2D(1,0)));
    penguinsans2.add(new Penguin(Colour.WHITE, new Pos2D(1,1)));
    ans.add(new Player(2, Colour.WHITE, 0, penguinsans2));

    JsonArray players = new JsonArray();
    players.add(player1);
    players.add(player2);

    assertEquals(xstate.getOurPlayers(players), ans);

  }

}