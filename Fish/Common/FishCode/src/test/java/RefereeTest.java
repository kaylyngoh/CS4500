import Model.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Player.IPlayer;
import Player.InternalPlayer;
import Admin.Referee;
import Player.Strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RefereeTest {

  InternalPlayer iplayer1;
  InternalPlayer iplayer2;
  InternalPlayer iplayer3;
  InternalPlayer iplayer4;
  InternalPlayer iplayer5;
  InternalPlayer iplayer6;
  InternalPlayer iplayer7;
  InternalPlayer iplayer8;
  InternalPlayer iplayer9;
  InternalPlayer iplayer10;

  List<IPlayer> iplayerList1;
  List<IPlayer> iplayerList2;
  List<IPlayer> iplayerList3;
  List<IPlayer> iplayerList4;

  Referee referee1;
  Referee referee2;
  Referee referee3;
  Referee referee4;

  @Before
  public void init() {
    this.iplayer1 = new InternalPlayer("Alice", 1, new Strategy(), 1);
    this.iplayer2 = new InternalPlayer("Bob", 2, new Strategy(), 1);
    this.iplayer3 = new InternalPlayer("Sam", 3, new Strategy(), 2);
    this.iplayer4 = new InternalPlayer("Tina", 4, new Strategy(), 1);
    this.iplayer5 = new InternalPlayer("Julia", 5, new Strategy(), 2);
    this.iplayer6 = new InternalPlayer("Mike", 6, new Strategy(), 1);
    this.iplayer7 = new InternalPlayer("Laura", 9, new Strategy(), 2);
    this.iplayer8 = new InternalPlayer("Peter", 10, new Strategy(), 1);
    this.iplayer9 = new InternalPlayer("John", 11, new Strategy(), 1);
    this.iplayer10 = new InternalPlayer("David", 12, new Strategy(), 2);

    this.iplayerList1 = new ArrayList<IPlayer>();
    this.iplayerList2 = new ArrayList<IPlayer>();
    this.iplayerList3 = new ArrayList<IPlayer>();
    this.iplayerList4 = new ArrayList<IPlayer>();

    this.iplayerList1.add(iplayer1);
    this.iplayerList1.add(iplayer2);
    this.iplayerList2.add(iplayer3);
    this.iplayerList2.add(iplayer4);
    this.iplayerList3.add(iplayer5);
    this.iplayerList3.add(iplayer6);
    this.iplayerList4.add(iplayer7);
    this.iplayerList4.add(iplayer8);
    this.iplayerList4.add(iplayer9);
    this.iplayerList4.add(iplayer10);


    this.referee1 = new Referee(iplayerList1, 3,3);
    this.referee2 = new Referee(iplayerList2, 2,4);
    this.referee3 = new Referee(iplayerList3,2,5);
    this.referee4 = new Referee(iplayerList4, 4,4);
  }


  // checks whether the referee did the correct actions when there is only one move by checking the penguin positions
  @Test
  public void testCorrectRefereeAction1(){
    // check whether all the penguins are on the correct positions after being moved by the referee
    List<Pos2D> penguinPos = new ArrayList<Pos2D>(Arrays.asList(new Pos2D(0,0),new Pos2D(2,0),
            new Pos2D(1,0), new Pos2D(0,1), new Pos2D(2,1), new Pos2D(1,1), new Pos2D(2,2),
            new Pos2D(1,2)));
    assertTrue(referee1.getGameState().getPenguinPositions().containsAll(penguinPos));
    assertTrue(penguinPos.containsAll(referee1.getGameState().getPenguinPositions()));

    // check whether the fish number in (2,0) is zero (move)
    assertEquals(referee1.getGameState().getBoard().get2DTiles()[2][0].getFishNum(), 0);

  }

  // check whether the referee did the correct actions when there is no more available moves after placing
  // all the penguins, by checking the penguin positions
  @Test
  public void testCorrectRefereeAction2(){
    // check whether all the penguins are placed on the correct positions by the referee
    List<Pos2D> penguinPos = new ArrayList<Pos2D>(Arrays.asList(new Pos2D(0,0),new Pos2D(2,0),
            new Pos2D(1,0), new Pos2D(3,0), new Pos2D(0,1), new Pos2D(2,1),
            new Pos2D(1,1), new Pos2D(3,1)));
    assertTrue(referee2.getGameState().getPenguinPositions().containsAll(penguinPos));
    assertTrue(penguinPos.containsAll(referee2.getGameState().getPenguinPositions()));
  }

  // check whether the referee did the correct actions when there are two movements by checking
  // the penguin positions
  @Test
  public void testCorrectRefereeAction3(){
    // check whether all the penguins are on the correct positions after being moved by the referee
    List<Pos2D> penguinPos = new ArrayList<Pos2D>(Arrays.asList(new Pos2D(0,0),new Pos2D(2,0),
            new Pos2D(4,0), new Pos2D(0,1),new Pos2D(2,1), new Pos2D(4,1),
            new Pos2D(1,1),new Pos2D(3,1)));

    assertTrue(referee3.getGameState().getPenguinPositions().containsAll(penguinPos));
    assertTrue(penguinPos.containsAll(referee3.getGameState().getPenguinPositions()));

    //check whether the fish numbers in (0,1) and (0,3) are zero (move)
    assertEquals(referee3.getGameState().getBoard().get2DTiles()[0][1].getFishNum(),0);
    assertEquals(referee3.getGameState().getBoard().get2DTiles()[0][3].getFishNum(),0);
  }

  // test getWinner method when there is only one possible move
  @Test
  public void testGetWinner1(){
    List<IPlayer> winner = new ArrayList<>();
    winner.add(iplayer1);
    assertEquals(referee1.getWinners(),winner);
  }

  // test getWinner method when there is no possible move after placing all penguins
  @Test
  public void testGetWinner2(){
    List<IPlayer> winners = new ArrayList<>();
    winners.add(iplayer3);
    winners.add(iplayer4);
    assertEquals(referee2.getWinners(),winners);
  }

  // test getWinner method when there are two movements
  @Test
  public void testGetWinner3(){
    List<IPlayer> winners = new ArrayList<>();
    winners.add(iplayer5);
    winners.add(iplayer6);
    assertEquals(referee3.getWinners(),winners);

  }

  // test whether the referee assigned different colors to players
  @Test
  public void testAssignColor(){
    List<Colour> playerColors = new ArrayList<Colour>(referee1.getPlayersInThisGame().keySet());
    assertFalse(playerColors.get(0).equals(playerColors.get(1)));
  }

  // test whether the referee has gotten the correct scores when there is only one movement
  @Test
  public void testWinnerScore1(){
    assertEquals(referee1.getWinningScore(),15);
  }

  // test whether the referee has gotten the correct scores when there is no available move after
  // placing all penguins (2 players have same score)
  @Test
  public void testWinnerScore2(){
    assertEquals(referee2.getWinningScore(), 12);
  }

  // test whether the referee has gotten teh correct scores when there are two movements, one
  // for each player (2 players have same score)
  @Test
  public void testWinnerScore3(){
    assertEquals(referee3.getWinningScore(), 15);
  }

  // test for getGameResult method
  @Test
  public void testGetGameResult1(){
    HashMap<IPlayer, Integer> result = new HashMap<>();
    result.put(iplayer1, 15);
    result.put(iplayer2, 12);
    assertEquals(referee1.getGameResult(), result);
  }

  @Test
  public void testGetGameResult2(){
    HashMap<IPlayer, Integer> result = new HashMap<>();
    result.put(iplayer3, 12);
    result.put(iplayer4, 12);
    assertEquals(referee2.getGameResult(), result);
  }

  @Test
  public void testGetGameResult3(){
    HashMap<IPlayer, Integer> result = new HashMap<>();
    result.put(iplayer5, 15);
    result.put(iplayer6, 15);
    assertEquals(referee3.getGameResult(), result);
  }

   //test for method getCheaters
  @Test
  public void testGetCheaters(){
    referee4.removePlayerFromGameMove(referee4.getGameState());
    List<IPlayer> cheaters = new ArrayList<>();
    cheaters.add(iplayer7);
    assertEquals(referee4.getCheaters(),cheaters);
  }

  @Test
  public void testPlacePenguinOutsideBoard() {
    List<Pos2D> toPlace = new ArrayList<>();
    toPlace.add(new Pos2D(0, -1));
    Referee ref = new Referee(this.iplayerList1, toPlace);
    IFishModel currState = ref.getGameState();

    assertEquals(0, currState.getPenguins().size());
    assertEquals(1, ref.getCheaters().size());
  }

  @Test
  public void testPlacePenguinOnAnotherPenguin() {
    List<Pos2D> toPlace = new ArrayList<>();
    toPlace.add(new Pos2D(0, 0));
    toPlace.add(new Pos2D(0, 0));
    Referee ref = new Referee(this.iplayerList1, toPlace);
    IFishModel currState = ref.getGameState();

    assertEquals(1, currState.getPenguins().size());
    assertEquals(1, ref.getCheaters().size());
  }

  @Test
  public void testCheatMoveOutsideBoard() {
    List<Pos2D> toPlace = new ArrayList<>();
    toPlace.add(new Pos2D(0, 0));
    toPlace.add(new Pos2D(0, 1));
    Referee ref = new Referee(this.iplayerList1, toPlace);

    Move badMove = new Move(new Penguin(Colour.RED, new Pos2D(0, 0)), new Pos2D(0, -1));

    ref.validateMovePenguin(ref.getGameState(), badMove);

    assertEquals(1, ref.getGameState().getPenguins().size());
    assertEquals(1, ref.getCheaters().size());
  }

  @Test
  public void testCheatMoveInvalidMove() {
    List<Pos2D> toPlace = new ArrayList<>();
    toPlace.add(new Pos2D(0, 0));
    toPlace.add(new Pos2D(0, 1));
    Referee ref = new Referee(this.iplayerList1, toPlace);

    Move badMove = new Move(new Penguin(Colour.RED, new Pos2D(0, 0)), new Pos2D(1, 1));

    ref.validateMovePenguin(ref.getGameState(), badMove);

    assertEquals(1, ref.getGameState().getPenguins().size());
    assertEquals(1, ref.getCheaters().size());
  }

  @Test
  public void testCheatMoveOnPenguin() {
    List<Pos2D> toPlace = new ArrayList<>();
    toPlace.add(new Pos2D(0, 0));
    toPlace.add(new Pos2D(0, 1));
    Referee ref = new Referee(this.iplayerList1, toPlace);

    Move badMove = new Move(new Penguin(Colour.RED, new Pos2D(0, 0)), new Pos2D(0, 1));

    ref.validateMovePenguin(ref.getGameState(), badMove);

    assertEquals(1, ref.getGameState().getPenguins().size());
    assertEquals(1, ref.getCheaters().size());
  }

  //TODO: Think about how to unit test the skipping of a player
}
