import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import Model.Board;
import Model.Colour;
import Model.FishModel;
import Model.GameNode;
import Model.IFishModel;
import Model.Move;
import Model.Penguin;
import Model.Player;
import Model.Pos2D;

import static org.junit.Assert.assertEquals;

public class GameNodeTest {

  Board fullboard1;

  Player player1;
  Player player2;
  Player player3;
  Player player4;

  Penguin penguinBrown;
  Penguin penguinWhite;
  Penguin penguinBlack;
  Penguin penguinRed;

  List<Player> playerList1;

  FishModel model1;
  FishModel model2;

  @Before
  public void init() {

    fullboard1 = new Board(3, 3, 4);

    player1 = new Player(13, Colour.BROWN);
    player2 = new Player(16, Colour.WHITE);
    player3 = new Player(20, Colour.BLACK);
    player4 = new Player(26, Colour.RED);

    penguinBrown = new Penguin(Colour.BROWN, new Pos2D(1, 1));
    penguinWhite = new Penguin(Colour.WHITE, new Pos2D(0, 1));
    penguinBlack = new Penguin(Colour.BLACK, new Pos2D(1, 2));
    penguinRed = new Penguin(Colour.RED, new Pos2D(0, 2));

    playerList1 = new ArrayList<Player>();

    playerList1.add(player1);
    playerList1.add(player2);
    playerList1.add(player3);
    playerList1.add(player4);

    this.model1 = new FishModel(fullboard1, playerList1);

    model1.placePenguin(new Pos2D(0, 0));
    model1.placePenguin(new Pos2D(0, 1));
    model1.placePenguin(new Pos2D(0, 2));
    model1.placePenguin(new Pos2D(1, 0));
    model1.placePenguin(new Pos2D(1, 1));
    model1.placePenguin(new Pos2D(1, 2));
    model1.placePenguin(new Pos2D(2, 0));
    model1.placePenguin(new Pos2D(2, 1));

    model1.startGame();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetParentNull() {
    GameNode startingNode = new GameNode(model1, null);
    startingNode.getParent();
  }

  @Test
  public void testGetParentNonNull() {
    GameNode startingNode = new GameNode(model1, null);
    for (GameNode node : startingNode.generatePossibleGameState()) {
      assertEquals(node.getParent(), startingNode);
    }
  }

  // test generate possibleGameState method with a skip
  @Test
  public void testGeneratePossibleGameState1() {
    IFishModel copy1 = new FishModel(model1);
    copy1.skipPlayer();
    copy1.skipPlayer();
    GameNode startingNode = new GameNode(copy1, null);
    IFishModel copy2 = copy1.copyFishModel();
    copy2.skipPlayer();
    List<GameNode> ans = new ArrayList<>(Arrays.asList(new GameNode(copy2, startingNode)));
    assertEquals(startingNode.generatePossibleGameState(), ans);
  }

  // test generate possibleGameState method without a skip because game over
  @Test
  public void testGeneratePossibleGameState2() {
    GameNode startingNode = new GameNode(model1, null);
    GameNode node = new GameNode(startingNode.takeAction(new Move(new Penguin(Colour.BROWN, new Pos2D(1, 1)), new Pos2D(2, 2))), startingNode);
    List<GameNode> ans = new ArrayList<>();
    assertEquals(node.generatePossibleGameState(), ans);
  }

  // test takeAction method, legal move
  @Test
  public void testTakeAction1() {
    GameNode startingNode = new GameNode(model1, null);
    FishModel copy1 = new FishModel(model1);
    copy1.movePenguin(new Penguin(Colour.BROWN, new Pos2D(1,1)), new Pos2D(2,2));
    assertEquals(startingNode.takeAction(new Move(new Penguin(Colour.BROWN, new Pos2D(1,1)), new Pos2D(2,2))), copy1);
  }

  // test takeAction method, illegal move
  @Test(expected = IllegalArgumentException.class)
  public void testTakeAction2() {
    GameNode startingNode = new GameNode(model1, null);
    startingNode.takeAction(new Move(new Penguin(Colour.BROWN, new Pos2D(1,1)), new Pos2D(0,2)));
  }


  // test takeAction method, illegal move (to the same spot)
  @Test(expected = IllegalArgumentException.class)
  public void testTakeAction3() {
    GameNode startingNode = new GameNode(model1, null);
    startingNode.takeAction(new Move(new Penguin(Colour.BROWN, new Pos2D(0,1)), new Pos2D(0,1)));
  }

  // test applies function method
  @Test
  public void testAppliesFunction() {
    GameNode startingNode = new GameNode(model1, null);
    ArrayList<Integer> ans = new ArrayList<>(Arrays.asList(12));
    Function<IFishModel, Integer> getYoungestPlayerScore = a -> a.getPlayers().get(0).getScore();
    assertEquals(startingNode.appliesFunction(getYoungestPlayerScore), ans);
  }


//  // test takeAction method, illegal move (to the same spot)
//  @Test
//  public void testTakeAction4() {
//    Model.Board board = new Model.Board(3, 4, 1);
//    board.removeTile(new Model.Pos2D(3,0));
//    board.removeTile(new Model.Pos2D(2,1));
//    Model.Player player1 = new Model.Player(1, Model.Colour.WHITE);
//    Model.Player player2 = new Model.Player(2, Model.Colour.RED);
//    List<Model.Player> lop = new ArrayList<>();
//    lop.add(player1);
//    lop.add(player2);
//    Model.FishModel m = new Model.FishModel(board, lop);
//    m.placePenguin(new Model.Pos2D(0,1));
//    m.placePenguin(new Model.Pos2D(2,0));
//    m.placePenguin(new Model.Pos2D(1,1));
//    m.placePenguin(new Model.Pos2D(3,1));
//    m.placePenguin(new Model.Pos2D(0,2));
//    m.placePenguin(new Model.Pos2D(2,2));
//    m.placePenguin(new Model.Pos2D(1,2));
//    m.placePenguin(new Model.Pos2D(3,2));
//    m.startGame();
//    m.movePenguin(new Model.Penguin(Model.Colour.WHITE, new Model.Pos2D(0,1)), new Model.Pos2D(1,0));
//    m.movePenguin(new Model.Penguin(Model.Colour.RED, new Model.Pos2D(2,0)), new Model.Pos2D(2,0));
//    Model.GameNode n = new Model.GameNode(m, null);
//    n.takeAction(new Model.Move(new Model.Penguin(Model.Colour.RED, new Model.Pos2D(2,0)), new Model.Pos2D(2,0)));
//  }
}