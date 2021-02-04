import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import Model.Action;
import Model.Board;
import Model.Colour;
import Model.FishModel;
import Model.GameNode;
import Model.GameStatus;
import Model.Hole;
import Player.InternalPlayer;
import Model.Move;
import Model.Penguin;
import Model.Pieces;
import Model.Player;
import Model.Pos2D;
import Player.Strategy;
import Model.Tile;
import Other.xstate;

import static org.junit.Assert.assertEquals;

public class InternalPlayerTest {

  InternalPlayer iplayer1;
  InternalPlayer iplayer2;
  InternalPlayer iplayer3;

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
    iplayer1 = new InternalPlayer("Alice", 1, new Strategy(), 1);
    iplayer2 = new InternalPlayer("Bob", 2, new Strategy(), 1);
    iplayer3 = new InternalPlayer("Nick", 4, new Strategy(), 2);

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

//  // test for receiveInformationAboutEnd method
//  @Test
//  public void testReceiveInformationAboutEndTrue(){
//    assertEquals(iplayer1.receiveInformationAboutEnd(), true);
//  }
//
//  @Test
//  public void testReceiveInformationAboutEndFalse(){
//    assertEquals(iplayer2.receiveInformationAboutEnd(), false);
//  }

  // test for getPlayerName method
  @Test
  public void testGetPlayerName1(){
    assertEquals(iplayer1.getPlayerName(), "Alice");
  }

  @Test
  public void testGetPlayerName2(){
    assertEquals(iplayer2.getPlayerName(), "Bob");
  }

  // test for getPlayerAge method
  @Test
  public void testGetPlayerAge1(){
    assertEquals(iplayer1.getPlayerAge(),1);
  }

  @Test
  public void testGetPlayerAge2(){
    assertEquals(iplayer2.getPlayerAge(),2);
  }

  // test for startGame and endGame method
  @Test
  public void testStartAndEndGame(){
    assertEquals(iplayer1.getGameStatus(), GameStatus.PENDING);
    iplayer1.startGame();
    assertEquals(iplayer1.getGameStatus(), GameStatus.PLAYING);
    assertEquals(iplayer2.getGameStatus(), GameStatus.PENDING);
    iplayer2.endGame();
    assertEquals(iplayer2.getGameStatus(), GameStatus.OVER);
  }


//  // test for giveEndInformation method
//  @Test
//  public void testGiveEndInformation(){
//    HashMap<String, Integer> winnerList = new HashMap<>();
//    winnerList.put("Andy", 10);
//    winnerList.put("Sam", 10);
//    winnerList.put("Nick",10);
//    iplayer1.giveEndInformation(winnerList);
//    assertEquals(iplayer1.getWinnerInfo(), winnerList);
//    iplayer3.giveEndInformation(winnerList);
//    assertEquals(iplayer3.getWinnerInfo(), winnerList);
//  }


  // test for getPositionToPlace method when the board is not big enough to place all the penguins
  @Test(expected = IllegalArgumentException.class)
  public void testGetPositionToPlaceNotBigEnough(){
    iplayer1.getPositionToPlace(model3);
  }

  // test for GetPositionToPlace method
  @Test
  public void testGetPositionToPlace1(){
    assertEquals(iplayer1.getPositionToPlace(model1), new Pos2D(2,0));
  }


  // test for GetPositionToPlace method without any penguin on the board
  @Test
  public void testGetPositionToPlaceEmpty(){
    assertEquals(iplayer2.getPositionToPlace(model2), new Pos2D(0,0));
  }


  @Test
  public void testGetPositionToPlace2(){
    model2.placePenguin(new Pos2D(iplayer1.getPositionToPlace(model2)));
    assertEquals(iplayer1.getPositionToPlace(model2), new Pos2D(2,0));
    model2.placePenguin(new Pos2D(iplayer1.getPositionToPlace(model2)));
    assertEquals(iplayer1.getPositionToPlace(model2), new Pos2D(1,0));
    model2.placePenguin(new Pos2D(iplayer1.getPositionToPlace(model2)));
    assertEquals(iplayer1.getPositionToPlace(model2), new Pos2D(0,1));
  }

  // test for method getMove with full board and same fish numbers, player depth 1
  @Test
  public void getMove1() {
    assertEquals(iplayer2.getMove(model1),
            new Move(new Penguin(Colour.BROWN, new Pos2D(0,1)), new Pos2D(2,0)));
  }

  // test for method getMove with full board and same fish numbers, player depth 2
  @Test
  public void getMove2(){
    assertEquals(iplayer3.getMove(model4),
            new Move(new Penguin(Colour.RED, new Pos2D(2,0)),new Pos2D(1,0)));
  }


  // test for method getMove with hole board and different fish numbers, player depth 2
  @Test
  public void getMove3() {
    assertEquals(iplayer3.getMove(model5),
            new Move(new Penguin(Colour.WHITE, new Pos2D(0, 2)), new Pos2D(1, 1)));

  }





}
