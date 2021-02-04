import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import Model.Action;
import Model.Board;
import Model.Colour;
import Model.FishModel;
import Model.GameNode;
import Model.Hole;
import Model.Move;
import Model.Penguin;
import Model.Pieces;
import Model.Player;
import Model.Pos2D;
import Player.Strategy;
import Model.Tile;
import Other.xstate;

import static org.junit.Assert.assertEquals;

public class StrategyTest {

  Board fullboard1;
  Board fullboard2;
  Board fullboard3;
  Board fullboard4;
  Pieces[][] ourTiles = xstate.makeEmptyBoard(3,3);
  Board holeBoard = new Board(ourTiles);


  Player player1;
  Player player2;
  Player player3;
  Player player4;
  Player player5;
  Player player6;
  Player player7;
  Player player8;
  Player player9;
  Player player10;

  Action action1;
  Action action2;
  Action action3;
  Action action4;
  Action action5;
  Action action6;



  List<Player> playerList1;
  List<Player> playerList2;
  List<Player> playerList3;
  List<Player> playerList4;
  List<Player> playerList5;

  List<Action> actionList1;
  List<Action> actionList2;
  List<Action> actionList3;
  List<Action> actionList4;

  FishModel model1;
  FishModel model2;
  FishModel model3;
  FishModel model4;
  FishModel model5;

  GameNode startingNode;

  @Before
  public void init() {

    fullboard1 = new Board(2, 3, 4);
    fullboard2 = new Board(3, 3, 4);
    fullboard3 = new Board(2,2,3);
    fullboard4 = new Board(5,5,2);

    ourTiles[0][0] = new Hole();
    ourTiles[0][2] = new Hole();
    ourTiles[0][1] = new Tile(2);
    ourTiles[1][0] = new Hole();
    ourTiles[1][2] = new Tile(4);
    ourTiles[1][1] = new Tile(3);
    ourTiles[2][0] = new Tile(4);
    ourTiles[2][1] = new Tile(2);
    ourTiles[2][2] = new Hole();



    player1 = new Player(13, Colour.BROWN);
    player2 = new Player(16, Colour.WHITE);
    player3 = new Player(19, Colour.WHITE);
    player4 = new Player(20,Colour.RED);
    player5 = new Player(21, Colour.RED);
    player6 = new Player(27, Colour.BLACK);
    player7 = new Player(30, Colour.RED);
    player8 = new Player(32, Colour.BLACK);
    player9 = new Player(33, Colour.WHITE);
    player10 = new Player(40, Colour.BROWN);

    action1 = new Move(new Penguin(Colour.WHITE, new Pos2D(0,2)), new Pos2D(1,1));
    action2 = new Move(new Penguin(Colour.WHITE, new Pos2D(0,1)), new Pos2D(1,1));
    action3 = new Move(new Penguin(Colour.WHITE, new Pos2D(2, 2)), new Pos2D(1,1));
    action4 = new Move(new Penguin(Colour.BLACK, new Pos2D(2,0)), new Pos2D(2,1));
    action5 = new Move(new Penguin(Colour.BLACK, new Pos2D(2,0)), new Pos2D(1,0));
    action6 = new Move(new Penguin(Colour.BLACK, new Pos2D(2,0)), new Pos2D(2,2));



    playerList1 = new ArrayList<Player>();
    playerList2 = new ArrayList<Player>();
    playerList3 = new ArrayList<Player>();
    playerList4 = new ArrayList<Player>();
    playerList5 = new ArrayList<Player>();

    actionList1 = new ArrayList<Action>();
    actionList2 = new ArrayList<Action>();
    actionList3 = new ArrayList<Action>();
    actionList4 = new ArrayList<Action>();


    playerList1.add(player1);
    playerList1.add(player2);
    playerList2.add(player3);
    playerList2.add(player4);
    playerList3.add(player5);
    playerList3.add(player6);
    playerList4.add(player7);
    playerList4.add(player8);
    playerList5.add(player9);
    playerList5.add(player10);

    actionList1.add(action1);
    actionList1.add(action2);
    actionList2.add(action1);
    actionList2.add(action3);
    actionList3.add(action4);
    actionList3.add(action5);
    actionList4.add(action4);
    actionList4.add(action6);



    this.model1 = new FishModel(fullboard1, playerList1);
    this.model2 = new FishModel(fullboard2, playerList2);
    this.model3 = new FishModel(fullboard3, playerList4);
    this.model4 = new FishModel(fullboard2, playerList3);
    this.model5 = new FishModel(holeBoard, playerList5);


    model1.placePenguin(new Pos2D(0, 1));
    model1.placePenguin(new Pos2D(0, 0));
    model1.placePenguin(new Pos2D(1, 1));
    model1.placePenguin(new Pos2D(2, 1));

    model1.startGame();


    this.startingNode = new GameNode(model1, null);

    model3.placePenguin(new Pos2D(0,0));
    model3.placePenguin(new Pos2D(0,1));
    model3.placePenguin(new Pos2D(1,0));
    model3.placePenguin(new Pos2D(1,1));

    model4.placePenguin(new Pos2D(2,0));
    model4.placePenguin(new Pos2D(0,1));
    model4.startGame();

    model5.placePenguin(new Pos2D(0,2));
    model5.placePenguin(new Pos2D(2,1));
    model5.startGame();


  }


  // test for zigZagPenguinPlacement method when the board is not big enough to place all the penguins
  @Test(expected = IllegalArgumentException.class)
  public void testZigZagNotBigEnough(){
    new Strategy().getPenguinPlacement(model3);
  }

  // test for zigZagPenguinPlacement method
  @Test
  public void testZigZag1(){
    assertEquals(new Strategy().getPenguinPlacement(model1), new Pos2D(2,0));
  }


  // test for zigZagPenguinPlacement method without any penguin on the board
  @Test
  public void testZigZagEmpty(){
    assertEquals(new Strategy().getPenguinPlacement(model2), new Pos2D(0,0));
  }


  @Test
  public void testZigZag2(){
    model2.placePenguin(new Pos2D(new Strategy().getPenguinPlacement(model2)));
    assertEquals(new Strategy().getPenguinPlacement(model2), new Pos2D(2,0));
    model2.placePenguin(new Pos2D(new Strategy().getPenguinPlacement(model2)));
    assertEquals(new Strategy().getPenguinPlacement(model2), new Pos2D(1,0));
    model2.placePenguin(new Pos2D(new Strategy().getPenguinPlacement(model2)));
    assertEquals(new Strategy().getPenguinPlacement(model2), new Pos2D(0,1));

  }

  // test for method minMaxAction with full board and same fish numbers, dept = 2
  @Test
  public void minMaxAction1() {
    assertEquals(new Strategy().getPlayerAction(model1, 2),
            new Move(new Penguin(Colour.BROWN, new Pos2D(0,1)), new Pos2D(2,0)));
  }

  // test for method minMaxAction with full board and same fish numbers, dept = 2
  @Test
  public void minMaxAction2(){
    assertEquals(new Strategy().getPlayerAction(model4,2),
            new Move(new Penguin(Colour.RED, new Pos2D(2,0)),new Pos2D(1,0)));
  }


  // test for method minMaxAction with hole board and different fish numbers, dept = 2
  @Test
  public void minMaxAction3() {
    assertEquals(new Strategy().getPlayerAction(model5, 2),
            new Move(new Penguin(Colour.WHITE, new Pos2D(0, 2)), new Pos2D(1, 1)));
  }

  // test for method minMaxAction with full board and same fish numbers, dept = 1
  @Test
  public void minMaxAction4() {
    assertEquals(new Strategy().getPlayerAction(model1, 1),
            new Move(new Penguin(Colour.BROWN, new Pos2D(0,1)), new Pos2D(2,0)));
  }

  // test for method minMaxAction with full board and same fish numbers, dept = 1
  @Test
  public void minMaxAction5(){
    assertEquals(new Strategy().getPlayerAction(model4,1),
            new Move(new Penguin(Colour.RED, new Pos2D(2,0)),new Pos2D(1,0)));
  }


  // test for method minMaxAction with hole board and different fish numbers, dept = 1
  @Test
  public void minMaxAction6() {
    assertEquals(new Strategy().getPlayerAction(model5, 1),
            new Move(new Penguin(Colour.WHITE, new Pos2D(0, 2)), new Pos2D(1, 1)));
  }


  // test for tieBreaker method when row numbers of from position are different
  @Test
  public void tieBreaker1(){
    Action ans = new Move(new Penguin(Colour.WHITE, new Pos2D(0,1)), new Pos2D(1,1));
    assertEquals(new Strategy().tieBreaker(actionList1), ans);
  }

  // test for tieBreaker method when row numbers of from position are the same, but column numbers
  // are different
  @Test
  public void tieBreaker2(){
    Action ans = new Move(new Penguin(Colour.WHITE, new Pos2D(0,2)), new Pos2D(1,1));
    assertEquals(new Strategy().tieBreaker(actionList2), ans);

  }

  // test for tieBreaker method when from positions are the same,
  // but row numbers for to position are different
  @Test
  public void tieBreaker3(){
    Action ans = new Move(new Penguin(Colour.BLACK, new Pos2D(2,0)), new Pos2D(1,0));
    assertEquals(new Strategy().tieBreaker(actionList3), ans);
  }

  // test for tieBreaker method when from positions are the same, and row numbers for to positions
  // are the same, but column numbers for to positions are different
  @Test
  public void tieBreaker4(){
    Action ans = new Move(new Penguin(Colour.BLACK, new Pos2D(2,0)), new Pos2D(2,1));
    assertEquals(new Strategy().tieBreaker(actionList4), ans);
  }
}