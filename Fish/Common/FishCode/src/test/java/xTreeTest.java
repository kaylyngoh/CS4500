import com.google.gson.JsonArray;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import Model.Action;
import Model.Board;
import Model.Colour;
import Model.FishModel;
import Model.Move;
import Model.Penguin;
import Model.Pieces;
import Model.Player;
import Model.Pos2D;
import Model.Tile;
import Other.xstate;
import Other.xtree;

import static org.junit.Assert.assertEquals;

public class xTreeTest {

  private final InputStream systemIn = System.in;
  private final PrintStream systemOut = System.out;
  private ByteArrayInputStream testIn;
  private ByteArrayOutputStream testOut;

  Pieces[][] ourTiles = xstate.makeEmptyBoard(2,6);
  Board ourBoard = new Board(ourTiles);
  Player player1;
  Player player2;
  List<Player> playerList1 = new ArrayList<>();
  Penguin penguin1;
  Penguin penguin2;
  Penguin penguin3;
  Penguin penguin4;
  List<Penguin> penguinList1 = new ArrayList<>();
  List<Penguin> penguinList2 = new ArrayList<>();
  FishModel model1;

  Player player3;
  Player player4;
  List<Player> playerList2 = new ArrayList<>();
  Penguin penguin5;
  Penguin penguin6;
  Penguin penguin7;
  Penguin penguin8;
  List<Penguin> penguinList3 = new ArrayList<>();
  List<Penguin> penguinList4 = new ArrayList<>();
  FishModel model2;


  @Before
  public void setUpOutput() {
    testOut = new ByteArrayOutputStream();
    System.setOut(new PrintStream(testOut));

    ourTiles[0][0] = new Tile(1);
    ourTiles[0][2] = new Tile(1);
    ourTiles[0][4] = new Tile(1);
    ourTiles[0][1] = new Tile(2);
    ourTiles[0][3] = new Tile(1);
    ourTiles[0][5] = new Tile(3);
    ourTiles[1][0] = new Tile(1);
    ourTiles[1][2] = new Tile(1);
    ourTiles[1][4] = new Tile(1);

    penguin1 = new Penguin(Colour.RED, new Pos2D(2, 0));
    penguin2 = new Penguin(Colour.RED, new Pos2D(0, 2));
    penguin3 = new Penguin(Colour.WHITE, new Pos2D(1, 0));
    penguin4 = new Penguin(Colour.WHITE, new Pos2D(5, 0));

    penguinList1.add(penguin1);
    penguinList1.add(penguin2);
    penguinList2.add(penguin3);
    penguinList2.add(penguin4);

    player1 = new Player(13, Colour.RED, 1, penguinList1);
    player2 = new Player(16, Colour.WHITE, 0, penguinList2);

    playerList1.add(player1);
    playerList1.add(player2);

    model1 = new FishModel(ourBoard, playerList1);
    model1.startGame();

    penguin5 = new Penguin(Colour.RED, new Pos2D(0, 0));
    penguin6 = new Penguin(Colour.RED, new Pos2D(5, 0));
    penguin7 = new Penguin(Colour.WHITE, new Pos2D(3, 0));
    penguin8 = new Penguin(Colour.WHITE, new Pos2D(2, 1));

    penguinList3.add(penguin5);
    penguinList3.add(penguin6);
    penguinList4.add(penguin7);
    penguinList4.add(penguin8);

    player3 = new Player(13, Colour.RED, 1, penguinList3);
    player4 = new Player(16, Colour.WHITE, 0, penguinList4);

    playerList2.add(player3);
    playerList2.add(player4);

    model2 = new FishModel(ourBoard, playerList2);
    model2.startGame();

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
    String testString = "{ \"state\": { \"players\" : [{\"color\": \"red\", \"score\": 4, \"places\": [[2,1],[0,1],[1,1],[1,2]]},\n" +
            "  {\"color\": \"white\", \"score\": 5, \"places\": [[0,2],[3,1],[4,0],[2,2]]}],\n" +
            "  \"board\" : [[1, 2, 3], [0, 1, 2], [5, 2, 2], [4, 3, 5], [2, 1, 2]]},\n" +
            "\"from\":  [1,2],\n" +
            "\"to\":  [3,2]}";
    provideInput(testString);

    xtree.main(new String[0]);

    assertEquals(getOutput(), "[[2,2],[4,2]]");
  }

  // test method to generate action output
  @Test
  public void testGenerateActionOutput() {
    Action action = new Move(new Penguin(Colour.RED, new Pos2D(1,1)), new Pos2D(2,2));
    JsonArray outputAction = new JsonArray();
    JsonArray fromAction = new JsonArray();
    fromAction.add(3);
    fromAction.add(0);
    outputAction.add(fromAction);
    JsonArray toAction = new JsonArray();
    toAction.add(4);
    toAction.add(1);
    outputAction.add(toAction);
    assertEquals(xtree.generateActionOutput(action), outputAction);
  }

  // test method to the neighboring position to move to (moving from Model.Pos2D(2,0) -> Model.Pos2D(3,0))
  // multiple directions to choose from, should go clockwise
  @Test
  public void testPossibleNeighboringAction1() {
    assertEquals(model1.getCurrentTurn(), player1);
    model1.movePenguin(penguin1, new Pos2D(3,0));
    assertEquals(xtree.possibleNeighboringAction(model1, new Pos2D(3,0)), new Move(penguin4, new Pos2D(4,0)));
  }

  // test method to the neighboring position to move to
  // tie breaker test (multiple penguins going to the same tile)
  @Test
  public void testPossibleNeighboringAction2() {
    assertEquals(model2.getCurrentTurn(), player3);
    model2.movePenguin(penguin5, new Pos2D(1,0));
    Action ans = xtree.possibleNeighboringAction(model2, new Pos2D(1,0));
    assertEquals(ans, new Move(penguin7, new Pos2D(2,0)));
  }

}