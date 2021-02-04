
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Model.Board;
import Model.Colour;
import Model.FishModel;
import Model.GameStatus;
import Model.Penguin;
import Model.Pieces;
import Model.Player;
import Model.Pos2D;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FishModelTest {

  Board fullboard1;
  Board fullboard2;

  Player player1;
  Player player2;
  Player player3;
  Player player4;
  Player player5;

  Player player6;
  Player player7;
  Player player8;
  Player player9;

  Penguin penguinBrown;
  Penguin penguinWhite;
  Penguin penguinBlack;
  Penguin penguinRed;

  List<Player> playerList1;
  List<Player> playerList2; // wrong order
  List<Player> playerList3; // same as playerList1
  List<Player> playerListLessThan2;
  List<Player> playerListMoreThan4;
  FishModel model1;
  FishModel model2;

  @Before
  public void init() {

    fullboard1 = new Board(10, 10, 4);
    fullboard2 = new Board(10, 10, 4);

    player1 = new Player(13, Colour.BROWN);
    player2 = new Player(16, Colour.WHITE);
    player3 = new Player(20, Colour.BLACK);
    player4 = new Player(26, Colour.RED);
    player5 = new Player(30, Colour.RED);

    player6 = new Player(13, Colour.BROWN);
    player7 = new Player(16, Colour.WHITE);
    player8 = new Player(20, Colour.BLACK);
    player9 = new Player(26, Colour.RED);

    playerList1 = new ArrayList<Player>();
    playerList2 = new ArrayList<Player>();
    playerList3 = new ArrayList<Player>();
    playerListLessThan2 = new ArrayList<Player>();
    playerListMoreThan4 = new ArrayList<Player>();

    playerList1.add(player1);
    playerList1.add(player2);
    playerList1.add(player3);
    playerList1.add(player4);

    playerList2.add(player3);
    playerList2.add(player1);
    playerList2.add(player2);
    playerList2.add(player4);

    playerList3.add(player6);
    playerList3.add(player7);
    playerList3.add(player8);
    playerList3.add(player9);

    playerListLessThan2.add(player1);

    playerListMoreThan4.add(player1);
    playerListMoreThan4.add(player2);
    playerListMoreThan4.add(player3);
    playerListMoreThan4.add(player4);
    playerListMoreThan4.add(player5);

    penguinBrown = new Penguin(Colour.BROWN, new Pos2D(1, 1));
    penguinWhite = new Penguin(Colour.WHITE, new Pos2D(0, 1));
    penguinBlack = new Penguin(Colour.BLACK, new Pos2D(1, 2));
    penguinRed = new Penguin(Colour.RED, new Pos2D(0, 2));

    this.model1 = new FishModel(fullboard1, playerList1);
    this.model2 = new FishModel(fullboard2, playerList3);

    model1.placePenguin(new Pos2D(1, 1));
    model1.placePenguin(new Pos2D(0, 1));
    model1.placePenguin(new Pos2D(1, 2));
    model1.placePenguin(new Pos2D(0, 2));
    model1.placePenguin(new Pos2D(1, 0));
    model1.placePenguin(new Pos2D(0, 0));
    model1.placePenguin(new Pos2D(0, 3));
    model1.placePenguin(new Pos2D(1, 3));
  }


  // test checkValidNumOfPlayer method if there are less than 2 players
  @Test(expected = IllegalArgumentException.class)
  public void checkValidNumOfPlayerLessThan2() {
    new FishModel(fullboard1, playerListLessThan2);
  }

  // test checkValidNumOfPlayer method if there are more than 4 players
  @Test(expected = IllegalArgumentException.class)
  public void checkValidNumOfPlayerMoreThan4() {
    new FishModel(fullboard1, playerListMoreThan4);
  }

  // test checkValidOrder method
  @Test(expected = IllegalArgumentException.class)
  public void checkValidOrder() {
    new FishModel(fullboard1, playerList2);
  }

  // test placePenguin method
  @Test
  public void checkPlacePenguin1() {
    model2.placePenguin(new Pos2D(1, 1));
    model2.placePenguin(new Pos2D(3, 7));
    Penguin penguin = new Penguin(Colour.BROWN, new Pos2D(1, 1));
    Penguin penguin2 = new Penguin(Colour.WHITE, new Pos2D(3, 7));
    List<Penguin> penguinList = new ArrayList<Penguin>();
    penguinList.add(penguin);
    penguinList.add(penguin2);
    assertTrue(model2.getPenguins().containsAll(penguinList));
  }

  // test placePenguin method
  @Test
  public void checkPlacePenguin2() {
    model2.placePenguin(new Pos2D(1, 1));
    assertEquals(player6.getPenguins().size(), 1);
    assertEquals(player6.getScore(), 0);
    assertEquals(fullboard2.get2DTiles()[1][1].getFishNum(), 4);
    assertEquals(model2.getGameStatus(), GameStatus.PENDING);
    assertEquals(model2.getCurrentTurn(), player7);
    model2.placePenguin(new Pos2D(2, 2));
    model2.placePenguin(new Pos2D(3, 1));
    model2.placePenguin(new Pos2D(2, 1));
    model2.placePenguin(new Pos2D(5, 5));
    model2.placePenguin(new Pos2D(1, 4));
    model2.placePenguin(new Pos2D(9, 9));
    model2.placePenguin(new Pos2D(6, 1));
    assertTrue(model2.getPenguinPositions().containsAll(new ArrayList<Pos2D>(Arrays.asList(new Pos2D(1, 1), new Pos2D(2, 2),
            new Pos2D(3, 1), new Pos2D(2, 1),new Pos2D(5, 5), new Pos2D(1, 4), new Pos2D(9, 9),
            new Pos2D(6, 1)))));
    assertTrue(new ArrayList<Pos2D>(Arrays.asList(new Pos2D(1, 1), new Pos2D(2, 2),
            new Pos2D(3, 1), new Pos2D(2, 1),new Pos2D(5, 5), new Pos2D(1, 4), new Pos2D(9, 9),
            new Pos2D(6, 1))).containsAll(model2.getPenguinPositions()));
  }

  // test placePenguin method when game has starting playing
  @Test(expected = IllegalArgumentException.class)
  public void checkPlacePenguin3() {
    model1.startGame();
    model1.placePenguin(new Pos2D(9, 9));
  }

  // test checkValidPenguinToMove method
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidPenguinToMove1() {
    model1.movePenguin(penguinBlack, new Pos2D(1, 2));
  }

  // test checkValidPenguinToMove method
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidPenguinToMove2() {
    model1.movePenguin(penguinRed, new Pos2D(1, 2));
  }

  // test check invalid penguin move (invalid given position)
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPenguinMove1() {
    model1.movePenguin(penguinBrown, new Pos2D(10, 10));
  }

  // test check invalid penguin move (move not in possible move list)
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPenguinMove2() {
    model1.movePenguin(penguinBrown, new Pos2D(0, 0));
  }

  // test check game status (Playing -> Over)
  @Test
  public void testCheckGameStatus() {
    model1.startGame();
    assertEquals(model1.getGameStatus(), GameStatus.PLAYING);
    fullboard1.removeTile(new Pos2D(0, 4));
    fullboard1.removeTile(new Pos2D(1, 4));
    fullboard1.removeTile(new Pos2D(2, 4));
    fullboard1.removeTile(new Pos2D(2, 3));
    fullboard1.removeTile(new Pos2D(2, 2));
    fullboard1.removeTile(new Pos2D(2, 0));
    fullboard1.removeTile(new Pos2D(3, 0));
    fullboard1.removeTile(new Pos2D(3, 1));
    model1.movePenguin(penguinBrown, new Pos2D(2, 1));
    assertEquals(model1.getGameStatus(), GameStatus.OVER);
  }


  // test check game status (Pending -> Over)
  @Test
  public void testCheckGameStatus2() {
    assertEquals(model2.getGameStatus(), GameStatus.PENDING);
    model2.placePenguin(new Pos2D(1, 1));
    model2.placePenguin(new Pos2D(0, 1));
    model2.placePenguin(new Pos2D(1, 2));
    model2.placePenguin(new Pos2D(0, 2));
    model2.placePenguin(new Pos2D(1, 0));
    model2.placePenguin(new Pos2D(0, 0));
    model2.placePenguin(new Pos2D(0, 3));
    model2.placePenguin(new Pos2D(1, 3));
    model2.startGame();
    assertEquals(model2.getGameStatus(), GameStatus.PLAYING);
  }

  // test to move when game is over
  @Test(expected = IllegalArgumentException.class)
  public void testGameOver() {
    fullboard1.removeTile(new Pos2D(0, 4));
    fullboard1.removeTile(new Pos2D(1, 4));
    fullboard1.removeTile(new Pos2D(2, 4));
    fullboard1.removeTile(new Pos2D(2, 3));
    fullboard1.removeTile(new Pos2D(2, 2));
    fullboard1.removeTile(new Pos2D(2, 0));
    fullboard1.removeTile(new Pos2D(3, 0));
    fullboard1.removeTile(new Pos2D(3, 1));
    model1.movePenguin(penguinBrown, new Pos2D(2, 1));
    model1.movePenguin(penguinWhite, new Pos2D(0,5));
  }

  // test to check if penguin is not on the board
  @Test(expected = IllegalArgumentException.class)
  public void testNoPenguinFound() {
    Penguin p = new Penguin(Colour.BROWN, new Pos2D(1, 2));
    model1.movePenguin(p, new Pos2D(1, 4));
  }

  //test valid moves
  @Test
  public void testMovePenguin() {
    model1.startGame();
    model1.movePenguin(penguinBrown, new Pos2D(2,1));
    assertEquals(player1.getPenguins().get(0).getPosition(), new Pos2D(2,1));
    assertEquals(player1.getScore(), 4);
    assertEquals(model1.getBoard().get2DTiles()[1][1].getFishNum(), 0);
    assertEquals(model1.getCurrentTurn(), player2);
  }

  // test all getter methods
  @Test
  public void testGetterMethods() {
    List<Penguin> penguinList = new ArrayList<>();
    penguinList.add(penguinBrown);
    penguinList.add(penguinWhite);
    penguinList.add(penguinBlack);
    penguinList.add(penguinRed);
    penguinList.add(new Penguin(Colour.BROWN, new Pos2D(1, 0)));
    penguinList.add(new Penguin(Colour.WHITE, new Pos2D(0, 0)));
    penguinList.add(new Penguin(Colour.BLACK, new Pos2D(0, 3)));
    penguinList.add(new Penguin(Colour.RED, new Pos2D(1, 3)));
    assertEquals(model1.getPlayers(), playerList1);
    assertEquals(model1.getPenguins().containsAll(penguinList), true);
    assertEquals(penguinList.containsAll(model1.getPenguins()), true);
  }

  @Test
  public void testGetterMethods2() {
    assertEquals(model1.getCurrentTurn(), player1);
    assertEquals(model1.getGameStatus(), GameStatus.PENDING);
    assertEquals(model1.getScoreOfPlayer(player1.getColor()), 0);
    assertTrue(model1.getPenguinPositions().containsAll(
            new ArrayList<>(Arrays.asList(new Pos2D(1, 1), new Pos2D(0, 1),
                    new Pos2D(1, 2), new Pos2D(0, 2), new Pos2D(1, 0),
                    new Pos2D(0, 0), new Pos2D(0, 3), new Pos2D(1, 3)))));
    assertTrue(new ArrayList<Pos2D>(Arrays.asList(new Pos2D(1, 1), new Pos2D(0, 1),
            new Pos2D(1, 2), new Pos2D(0, 2), new Pos2D(1, 0),
            new Pos2D(0, 0), new Pos2D(0, 3), new Pos2D(1, 3))).containsAll(model1.getPenguinPositions()));
  }

  @Test
  public void testGetBoard() {
    Pieces[][] given = model1.getBoard().get2DTiles();
    Pieces[][] ans = fullboard1.get2DTiles();
    for (int i = 0; i < given.length ; i++) {
      for (int j = 0; j < given[0].length ; j++) {
        assertEquals(given[i][j].getFishNum(), ans[i][j].getFishNum());
      }
    }
  }

  // test remove player method
  @Test
  public void testRemovePlayer() {
    model1.removePlayer(player1);
    List<Player> playerListAns = new ArrayList<>();
    playerListAns.add(player2);
    playerListAns.add(player3);
    playerListAns.add(player4);
    assertEquals(model1.getPlayers(), playerListAns);
    List<Penguin> penguinListAns = new ArrayList<>();
    penguinListAns.add(penguinWhite);
    penguinListAns.add(penguinBlack);
    penguinListAns.add(penguinRed);
    penguinListAns.add(new Penguin(Colour.WHITE, new Pos2D(0, 0)));
    penguinListAns.add(new Penguin(Colour.BLACK, new Pos2D(0, 3)));
    penguinListAns.add(new Penguin(Colour.RED, new Pos2D(1, 3)));
    assertEquals(model1.getPenguins().containsAll(penguinListAns), true);
    assertEquals(penguinListAns.containsAll(model1.getPenguins()), true);
  }

  // test checkCurrentPlayerCanMove method
  @Test(expected = IllegalArgumentException.class)
  public void testCheckCurrentPlayerCanMove() {
    model1.skipPlayer();
    model1.movePenguin(new Penguin(Colour.WHITE, new Pos2D(0,0)), new Pos2D(0, 1));
  }

  // test skipPlayer method
  @Test
  public void testSkipPlayer() {
    assertEquals(model1.getCurrentTurn(), player1);
    model1.skipPlayer();
    assertEquals(model1.getCurrentTurn(), player2);
  }

}