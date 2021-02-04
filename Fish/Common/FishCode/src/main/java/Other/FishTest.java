//package Other;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonPrimitive;
//
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.awt.*;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.PrintStream;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.function.Function;
//
//import Model.Action;
//import Model.Board;
//import Model.Colour;
//import Model.FishModel;
//import Model.GameNode;
//import Model.GameStatus;
//import Model.Hole;
//import Model.IFishModel;
//import Player.IPlayer;
//import Player.InternalPlayer;
//import Model.Move;
//import Model.Penguin;
//import Model.Pieces;
//import Model.Player;
//import Model.Pos2D;
//import Admin.Referee;
//import Player.Strategy;
//import Model.Tile;
//import Other.xboard;
//import Other.xstate;
//import Other.xtree;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//public class FishTest {
//  //-------------------Model.Board Test-----------------------------
//
//  Board board1 = new Board(1, 1, 3);
//  Board board2 = new Board(4, 8, 5);
//  Board board3 = new Board(10, 5, 4);
//  Board board4 = new Board(6, 6, 1);
//  Pos2D pos1 = new Pos2D(1, 2);
//  Pos2D pos2 = new Pos2D(3, 3);
//  Pos2D pos3 = new Pos2D(2, 3);
//  Pos2D pos4 = new Pos2D(3, 1);
//  List<Pos2D> holesList = new ArrayList<Pos2D>();
//  List<Pos2D> holesList2 = new ArrayList<Pos2D>();
//
//  //-----------------------Model.FishModel Test-------------------
//
//  Board modelfullboard1;
//  Board modelfullboard2;
//
//  Player modelplayer1;
//  Player modelplayer2;
//  Player modelplayer3;
//  Player modelplayer4;
//  Player modelplayer5;
//
//  Player modelplayer6;
//  Player modelplayer7;
//  Player modelplayer8;
//  Player modelplayer9;
//
//  Penguin modelpenguinBrown;
//  Penguin modelpenguinWhite;
//  Penguin modelpenguinBlack;
//  Penguin modelpenguinRed;
//
//  List<Player> modelplayerList1;
//  List<Player> modelplayerList2; // wrong order
//  List<Player> modelplayerList3; // same as playerList1
//  List<Player> modelplayerListLessThan2;
//  List<Player> modelplayerListMoreThan4;
//  FishModel modelmodel1;
//  FishModel modelmodel2;
//
//
//
//  //---------------------Model.GameNode Test------------------
//
//  Board nodefullboard1;
//
//  Player nodeplayer1;
//  Player nodeplayer2;
//  Player nodeplayer3;
//  Player nodeplayer4;
//
//  Penguin nodepenguinBrown;
//  Penguin nodepenguinWhite;
//  Penguin nodepenguinBlack;
//  Penguin nodepenguinRed;
//
//  List<Player> nodeplayerList1;
//
//  FishModel nodemodel1;
//  FishModel nodemodel2;
//
//
//
//  //----------------Model.Penguin Test------------------
//  Penguin penguinRed = new Penguin(Colour.RED, new Pos2D(2, 3));
//  Penguin penguinWhite = new Penguin(Colour.WHITE, new Pos2D(7, 2));
//  Penguin penguinBrown = new Penguin(Colour.BROWN, new Pos2D(6, 4));
//  Penguin penguinBlack = new Penguin(Colour.BLACK, new Pos2D(9, 5));
//
//  //-----------------------Piece Test------------------
//  Tile piecetile1 = new Tile(5);
//  Hole piecehole1 = new Hole();
//
//  //----------------------Model.Player Test-----------------
//  Board playerfullBoard = new Board(2, 2, 1);
//
//  Penguin playerpenguin1 = new Penguin(Colour.RED, new Pos2D(1, 1));
//  Penguin playerpenguin2 = new Penguin(Colour.RED, new Pos2D(0, 1));
//
//  Player playerplayer1 = new Player(13, Colour.BROWN);
//  Player playerplayer2 = new Player(13, Colour.RED, 5, new ArrayList<>(Arrays.asList(playerpenguin1, playerpenguin2)));
//
//  //-----------------------Model.Pos2D Test-----------------------
//  Pos2D pospos1 = new Pos2D(1, 2);
//
//  //--------------------------Player.Strategy Test-------------------
//
//  Board strategyfullboard1;
//  Board strategyfullboard2;
//  Board strategyfullboard3;
//  Board strategyfullboard4;
//  Pieces[][] strategyourTiles = xstate.makeEmptyBoard(3,3);
//  Board strategyholeBoard = new Board(strategyourTiles);
//
//
//  Player strategyplayer1;
//  Player strategyplayer2;
//  Player strategyplayer3;
//  Player strategyplayer4;
//  Player strategyplayer5;
//  Player strategyplayer6;
//  Player strategyplayer7;
//  Player strategyplayer8;
//  Player strategyplayer9;
//  Player strategyplayer10;
//
//  Action strategyaction1;
//  Action strategyaction2;
//  Action strategyaction3;
//  Action strategyaction4;
//  Action strategyaction5;
//  Action strategyaction6;
//
//
//
//  List<Player> strategyplayerList1;
//  List<Player> strategyplayerList2;
//  List<Player> strategyplayerList3;
//  List<Player> strategyplayerList4;
//  List<Player> strategyplayerList5;
//
//  List<Action> strategyactionList1;
//  List<Action> strategyactionList2;
//  List<Action> strategyactionList3;
//  List<Action> strategyactionList4;
//
//  FishModel strategymodel1;
//  FishModel strategymodel2;
//  FishModel strategymodel3;
//  FishModel strategymodel4;
//  FishModel strategymodel5;
//
//  GameNode strategystartingNode;
//
//  //--------------------xBoard Test--------------------------
//  private final InputStream xboardsystemIn = System.in;
//  private final PrintStream xboardsystemOut = System.out;
//  private ByteArrayInputStream xboardtestIn;
//  private ByteArrayOutputStream xboardtestOut;
//
//  JsonArray xboardgivenBoard = new JsonArray();
//  Pieces[][] xboardourTiles = xboard.makeEmptyBoard(2, 6);
//  Board xboardourBoard = new Board(xboardourTiles);
//
//  //-------------------xState Test----------------------
//
//  private final InputStream xstatesystemIn = System.in;
//  private final PrintStream xstatesystemOut = System.out;
//  private ByteArrayInputStream xstatetestIn;
//  private ByteArrayOutputStream xstatetestOut;
//
//  JsonArray xstategivenBoard = new JsonArray();
//  Pieces[][] xstateourTiles = xstate.makeEmptyBoard(2,6);
//  Board xstateourBoard = new Board(xstateourTiles);
//  Player xstateplayer1;
//  Player xstateplayer2;
//  List<Player> xstateplayerList = new ArrayList<>();
//  Penguin xstatepenguin1;
//  Penguin xstatepenguin2;
//  Penguin xstatepenguin3;
//  Penguin xstatepenguin4;
//  List<Penguin> xstatepenguinList1 = new ArrayList<>();
//  List<Penguin> xstatepenguinList2 = new ArrayList<>();
//  FishModel xstatemodel1;
//
//  //-------------------------xTree Test----------------------
//  private final InputStream xtreesystemIn = System.in;
//  private final PrintStream xtreesystemOut = System.out;
//  private ByteArrayInputStream xtreetestIn;
//  private ByteArrayOutputStream xtreetestOut;
//
//  Pieces[][] xtreeourTiles = xstate.makeEmptyBoard(2,6);
//  Board xtreeourBoard = new Board(xtreeourTiles);
//  Player xtreeplayer1;
//  Player xtreeplayer2;
//  List<Player> xtreeplayerList1 = new ArrayList<>();
//  Penguin xtreepenguin1;
//  Penguin xtreepenguin2;
//  Penguin xtreepenguin3;
//  Penguin xtreepenguin4;
//  List<Penguin> xtreepenguinList1 = new ArrayList<>();
//  List<Penguin> xtreepenguinList2 = new ArrayList<>();
//  FishModel xtreemodel1;
//
//  Player xtreeplayer3;
//  Player xtreeplayer4;
//  List<Player> xtreeplayerList2 = new ArrayList<>();
//  Penguin xtreepenguin5;
//  Penguin xtreepenguin6;
//  Penguin xtreepenguin7;
//  Penguin xtreepenguin8;
//  List<Penguin> xtreepenguinList3 = new ArrayList<>();
//  List<Penguin> xtreepenguinList4 = new ArrayList<>();
//  FishModel xtreemodel2;
//
//  //--------------------------------Player.InternalPlayer Test----------------------------
//  InternalPlayer internaliplayer1;
//  InternalPlayer internaliplayer2;
//  InternalPlayer internaliplayer3;
//
//  Board internalfullboard1;
//  Board internalfullboard2;
//  Board internalfullboard3;
//  Board internalfullboard4;
//  Pieces[][] internalourTiles = xstate.makeEmptyBoard(3,3);
//  Board internalholeBoard = new Board(internalourTiles);
//
//
//  Player internalplayer1;
//  Player internalplayer2;
//  Player internalplayer3;
//  Player internalplayer4;
//  Player internalplayer5;
//  Player internalplayer6;
//  Player internalplayer7;
//  Player internalplayer8;
//  Player internalplayer9;
//  Player internalplayer10;
//
//  Action internalaction1;
//  Action internalaction2;
//  Action internalaction3;
//  Action internalaction4;
//  Action internalaction5;
//  Action internalaction6;
//
//
//
//  List<Player> internalplayerList1;
//  List<Player> internalplayerList2;
//  List<Player> internalplayerList3;
//  List<Player> internalplayerList4;
//  List<Player> internalplayerList5;
//
//  List<Action> internalactionList1;
//  List<Action> internalactionList2;
//  List<Action> internalactionList3;
//  List<Action> internalactionList4;
//
//  FishModel internalmodel1;
//  FishModel internalmodel2;
//  FishModel internalmodel3;
//  FishModel internalmodel4;
//  FishModel internalmodel5;
//
//  GameNode internalstartingNode;
//
//  //--------------------------------Admin.Referee Test--------------------
//  InternalPlayer refereeiplayer1;
//  InternalPlayer refereeiplayer2;
//  InternalPlayer refereeiplayer3;
//  InternalPlayer refereeiplayer4;
//  InternalPlayer refereeiplayer5;
//  InternalPlayer refereeiplayer6;
//
//  List<IPlayer> refereeiplayerList1;
//  List<IPlayer> refereeiplayerList2;
//  List<IPlayer> refereeiplayerList3;
//
//  Referee refereereferee1;
//  Referee refereereferee2;
//  Referee refereereferee3;
//
//
//  @Before
//  public void init() {
//
//    //---------------------Model.Board Test------------------
//
//    holesList.add(pos1);
//    holesList.add(pos2);
//    holesList.add(pos3);
//    holesList2.add(pos4);
//
//    //-------------------Model.FishModel Test-------------------
//
//    modelfullboard1 = new Board(10, 10, 4);
//    modelfullboard2 = new Board(10, 10, 4);
//
//    modelplayer1 = new Player(13, Colour.BROWN);
//    modelplayer2 = new Player(16, Colour.WHITE);
//    modelplayer3 = new Player(20, Colour.BLACK);
//    modelplayer4 = new Player(26, Colour.RED);
//    modelplayer5 = new Player(30, Colour.RED);
//
//    modelplayer6 = new Player(13, Colour.BROWN);
//    modelplayer7 = new Player(16, Colour.WHITE);
//    modelplayer8 = new Player(20, Colour.BLACK);
//    modelplayer9 = new Player(26, Colour.RED);
//
//    modelplayerList1 = new ArrayList<Player>();
//    modelplayerList2 = new ArrayList<Player>();
//    modelplayerList3 = new ArrayList<Player>();
//    modelplayerListLessThan2 = new ArrayList<Player>();
//    modelplayerListMoreThan4 = new ArrayList<Player>();
//
//    modelplayerList1.add(modelplayer1);
//    modelplayerList1.add(modelplayer2);
//    modelplayerList1.add(modelplayer3);
//    modelplayerList1.add(modelplayer4);
//
//    modelplayerList2.add(modelplayer3);
//    modelplayerList2.add(modelplayer1);
//    modelplayerList2.add(modelplayer2);
//    modelplayerList2.add(modelplayer4);
//
//    modelplayerList3.add(modelplayer6);
//    modelplayerList3.add(modelplayer7);
//    modelplayerList3.add(modelplayer8);
//    modelplayerList3.add(modelplayer9);
//
//    modelplayerListLessThan2.add(modelplayer1);
//
//    modelplayerListMoreThan4.add(modelplayer1);
//    modelplayerListMoreThan4.add(modelplayer2);
//    modelplayerListMoreThan4.add(modelplayer3);
//    modelplayerListMoreThan4.add(modelplayer4);
//    modelplayerListMoreThan4.add(modelplayer5);
//
//    modelpenguinBrown = new Penguin(Colour.BROWN, new Pos2D(1, 1));
//    modelpenguinWhite = new Penguin(Colour.WHITE, new Pos2D(0, 1));
//    modelpenguinBlack = new Penguin(Colour.BLACK, new Pos2D(1, 2));
//    modelpenguinRed = new Penguin(Colour.RED, new Pos2D(0, 2));
//
//    this.modelmodel1 = new FishModel(modelfullboard1, modelplayerList1);
//    this.modelmodel2 = new FishModel(modelfullboard2, modelplayerList3);
//
//    modelmodel1.placePenguin(new Pos2D(1, 1));
//    modelmodel1.placePenguin(new Pos2D(0, 1));
//    modelmodel1.placePenguin(new Pos2D(1, 2));
//    modelmodel1.placePenguin(new Pos2D(0, 2));
//    modelmodel1.placePenguin(new Pos2D(1, 0));
//    modelmodel1.placePenguin(new Pos2D(0, 0));
//    modelmodel1.placePenguin(new Pos2D(0, 3));
//    modelmodel1.placePenguin(new Pos2D(1, 3));
//
//
//    //---------------------Model.GameNode Test---------------------
//
//    nodefullboard1 = new Board(3, 3, 4);
//
//    nodeplayer1 = new Player(13, Colour.BROWN);
//    nodeplayer2 = new Player(16, Colour.WHITE);
//    nodeplayer3 = new Player(20, Colour.BLACK);
//    nodeplayer4 = new Player(26, Colour.RED);
//
//    nodepenguinBrown = new Penguin(Colour.BROWN, new Pos2D(1, 1));
//    nodepenguinWhite = new Penguin(Colour.WHITE, new Pos2D(0, 1));
//    nodepenguinBlack = new Penguin(Colour.BLACK, new Pos2D(1, 2));
//    nodepenguinRed = new Penguin(Colour.RED, new Pos2D(0, 2));
//
//    nodeplayerList1 = new ArrayList<Player>();
//
//    nodeplayerList1.add(nodeplayer1);
//    nodeplayerList1.add(nodeplayer2);
//    nodeplayerList1.add(nodeplayer3);
//    nodeplayerList1.add(nodeplayer4);
//
//    this.nodemodel1 = new FishModel(nodefullboard1, nodeplayerList1);
//
//    nodemodel1.placePenguin(new Pos2D(0, 0));
//    nodemodel1.placePenguin(new Pos2D(0, 1));
//    nodemodel1.placePenguin(new Pos2D(0, 2));
//    nodemodel1.placePenguin(new Pos2D(1, 0));
//    nodemodel1.placePenguin(new Pos2D(1, 1));
//    nodemodel1.placePenguin(new Pos2D(1, 2));
//    nodemodel1.placePenguin(new Pos2D(2, 0));
//    nodemodel1.placePenguin(new Pos2D(2, 1));
//
//    nodemodel1.startGame();
//
//
//    //-------------------Player.Strategy Test------------------------
//
//    strategyfullboard1 = new Board(2, 3, 4);
//    strategyfullboard2 = new Board(3, 3, 4);
//    strategyfullboard3 = new Board(2,2,3);
//    strategyfullboard4 = new Board(5,5,2);
//
//    strategyourTiles[0][0] = new Hole();
//    strategyourTiles[0][2] = new Hole();
//    strategyourTiles[0][1] = new Tile(2);
//    strategyourTiles[1][0] = new Hole();
//    strategyourTiles[1][2] = new Tile(4);
//    strategyourTiles[1][1] = new Tile(3);
//    strategyourTiles[2][0] = new Tile(4);
//    strategyourTiles[2][1] = new Tile(2);
//    strategyourTiles[2][2] = new Hole();
//
//
//    strategyplayer1 = new Player(13, Colour.BROWN);
//    strategyplayer2 = new Player(16, Colour.WHITE);
//    strategyplayer3 = new Player(19, Colour.WHITE);
//    strategyplayer4 = new Player(20,Colour.RED);
//    strategyplayer5 = new Player(21, Colour.RED);
//    strategyplayer6 = new Player(27, Colour.BLACK);
//    strategyplayer7 = new Player(30, Colour.RED);
//    strategyplayer8 = new Player(32, Colour.BLACK);
//    strategyplayer9 = new Player(33, Colour.WHITE);
//    strategyplayer10 = new Player(40, Colour.BROWN);
//
//    strategyaction1 = new Move(new Penguin(Colour.WHITE, new Pos2D(0,2)), new Pos2D(1,1));
//    strategyaction2 = new Move(new Penguin(Colour.WHITE, new Pos2D(0,1)), new Pos2D(1,1));
//    strategyaction3 = new Move(new Penguin(Colour.WHITE, new Pos2D(2, 2)), new Pos2D(1,1));
//    strategyaction4 = new Move(new Penguin(Colour.BLACK, new Pos2D(2,0)), new Pos2D(2,1));
//    strategyaction5 = new Move(new Penguin(Colour.BLACK, new Pos2D(2,0)), new Pos2D(1,0));
//    strategyaction6 = new Move(new Penguin(Colour.BLACK, new Pos2D(2,0)), new Pos2D(2,2));
//
//
//
//    strategyplayerList1 = new ArrayList<Player>();
//    strategyplayerList2 = new ArrayList<Player>();
//    strategyplayerList3 = new ArrayList<Player>();
//    strategyplayerList4 = new ArrayList<Player>();
//    strategyplayerList5 = new ArrayList<Player>();
//
//    strategyactionList1 = new ArrayList<Action>();
//    strategyactionList2 = new ArrayList<Action>();
//    strategyactionList3 = new ArrayList<Action>();
//    strategyactionList4 = new ArrayList<Action>();
//
//
//    strategyplayerList1.add(strategyplayer1);
//    strategyplayerList1.add(strategyplayer2);
//    strategyplayerList2.add(strategyplayer3);
//    strategyplayerList2.add(strategyplayer4);
//    strategyplayerList3.add(strategyplayer5);
//    strategyplayerList3.add(strategyplayer6);
//    strategyplayerList4.add(strategyplayer7);
//    strategyplayerList4.add(strategyplayer8);
//    strategyplayerList5.add(strategyplayer9);
//    strategyplayerList5.add(strategyplayer10);
//
//    strategyactionList1.add(strategyaction1);
//    strategyactionList1.add(strategyaction2);
//    strategyactionList2.add(strategyaction1);
//    strategyactionList2.add(strategyaction3);
//    strategyactionList3.add(strategyaction4);
//    strategyactionList3.add(strategyaction5);
//    strategyactionList4.add(strategyaction4);
//    strategyactionList4.add(strategyaction6);
//
//
//    this.strategymodel1 = new FishModel(strategyfullboard1, strategyplayerList1);
//    this.strategymodel2 = new FishModel(strategyfullboard2, strategyplayerList2);
//    this.strategymodel3 = new FishModel(strategyfullboard3, strategyplayerList4);
//    this.strategymodel4 = new FishModel(strategyfullboard2, strategyplayerList3);
//    this.strategymodel5 = new FishModel(strategyholeBoard, strategyplayerList5);
//
//
//    strategymodel1.placePenguin(new Pos2D(0, 1));
//    strategymodel1.placePenguin(new Pos2D(0, 0));
//    strategymodel1.placePenguin(new Pos2D(1, 1));
//    strategymodel1.placePenguin(new Pos2D(2, 1));
//
//    strategymodel1.startGame();
//
//
//    this.strategystartingNode = new GameNode(strategymodel1, null);
//
//    strategymodel3.placePenguin(new Pos2D(0,0));
//    strategymodel3.placePenguin(new Pos2D(0,1));
//    strategymodel3.placePenguin(new Pos2D(1,0));
//    strategymodel3.placePenguin(new Pos2D(1,1));
//
//    strategymodel4.placePenguin(new Pos2D(2,0));
//    strategymodel4.placePenguin(new Pos2D(0,1));
//    strategymodel4.startGame();
//
//    strategymodel5.placePenguin(new Pos2D(0,2));
//    strategymodel5.placePenguin(new Pos2D(2,1));
//    strategymodel5.startGame();
//
//
//
//    //--------------------------xBoard Test-------------------------
//    xboardtestOut = new ByteArrayOutputStream();
//    System.setOut(new PrintStream(xboardtestOut));
//
//    JsonArray xboardrow1 = new JsonArray();
//    xboardrow1.add(1);
//    xboardrow1.add(0);
//    xboardrow1.add(1);
//    JsonArray xboardrow2 = new JsonArray();
//    xboardrow2.add(2);
//    xboardrow2.add(1);
//    xboardrow2.add(3);
//    JsonArray xboardrow3 = new JsonArray();
//    xboardrow3.add(1);
//    xboardrow3.add(1);
//    xboardrow3.add(1);
//    xboardgivenBoard.add(xboardrow1);
//    xboardgivenBoard.add(xboardrow2);
//    xboardgivenBoard.add(xboardrow3);
//    xboardourTiles[0][0] = new Tile(1);
//    xboardourTiles[0][2] = new Hole();
//    xboardourTiles[0][4] = new Tile(1);
//    xboardourTiles[0][1] = new Tile(2);
//    xboardourTiles[0][3] = new Tile(1);
//    xboardourTiles[0][5] = new Tile(3);
//    xboardourTiles[1][0] = new Tile(1);
//    xboardourTiles[1][2] = new Tile(1);
//    xboardourTiles[1][4] = new Tile(1);
//
//    //-------------------------xState Test-----------------------
//
//    xstatetestOut = new ByteArrayOutputStream();
//    System.setOut(new PrintStream(xstatetestOut));
//
//    JsonArray xstaterow1 = new JsonArray();
//    xstaterow1.add(1);
//    xstaterow1.add(0);
//    xstaterow1.add(1);
//    JsonArray xstaterow2 = new JsonArray();
//    xstaterow2.add(2);
//    xstaterow2.add(1);
//    xstaterow2.add(3);
//    JsonArray xstaterow3 = new JsonArray();
//    xstaterow3.add(1);
//    xstaterow3.add(1);
//    xstaterow3.add(1);
//    xstategivenBoard.add(xstaterow1);
//    xstategivenBoard.add(xstaterow2);
//    xstategivenBoard.add(xstaterow3);
//    xstateourTiles[0][0] = new Tile(1);
//    xstateourTiles[0][2] = new Hole();
//    xstateourTiles[0][4] = new Tile(1);
//    xstateourTiles[0][1] = new Tile(2);
//    xstateourTiles[0][3] = new Tile(1);
//    xstateourTiles[0][5] = new Tile(3);
//    xstateourTiles[1][0] = new Tile(1);
//    xstateourTiles[1][2] = new Tile(1);
//    xstateourTiles[1][4] = new Tile(1);
//
//    xstatepenguin1 = new Penguin(Colour.RED, new Pos2D(0, 0));
//    xstatepenguin2 = new Penguin(Colour.RED, new Pos2D(3, 0));
//    xstatepenguin3 = new Penguin(Colour.WHITE, new Pos2D(4, 1));
//    xstatepenguin4 = new Penguin(Colour.WHITE, new Pos2D(2, 1));
//
//    xstatepenguinList1.add(xstatepenguin1);
//    xstatepenguinList1.add(xstatepenguin2);
//    xstatepenguinList2.add(xstatepenguin3);
//    xstatepenguinList2.add(xstatepenguin4);
//
//    xstateplayer1 = new Player(13, Colour.RED, 1, xstatepenguinList1);
//    xstateplayer2 = new Player(16, Colour.WHITE, 0, xstatepenguinList2);
//
//    xstateplayerList.add(xstateplayer1);
//    xstateplayerList.add(xstateplayer2);
//
//    xstatemodel1 = new FishModel(xstateourBoard, xstateplayerList);
//    xstatemodel1.startGame();
//
//    //--------------------------xTree Test-----------------------------
//    xtreetestOut = new ByteArrayOutputStream();
//    System.setOut(new PrintStream(xtreetestOut));
//
//    xtreeourTiles[0][0] = new Tile(1);
//    xtreeourTiles[0][2] = new Tile(1);
//    xtreeourTiles[0][4] = new Tile(1);
//    xtreeourTiles[0][1] = new Tile(2);
//    xtreeourTiles[0][3] = new Tile(1);
//    xtreeourTiles[0][5] = new Tile(3);
//    xtreeourTiles[1][0] = new Tile(1);
//    xtreeourTiles[1][2] = new Tile(1);
//    xtreeourTiles[1][4] = new Tile(1);
//
//    xtreepenguin1 = new Penguin(Colour.RED, new Pos2D(2, 0));
//    xtreepenguin2 = new Penguin(Colour.RED, new Pos2D(0, 2));
//    xtreepenguin3 = new Penguin(Colour.WHITE, new Pos2D(1, 0));
//    xtreepenguin4 = new Penguin(Colour.WHITE, new Pos2D(5, 0));
//
//    xtreepenguinList1.add(xtreepenguin1);
//    xtreepenguinList1.add(xtreepenguin2);
//    xtreepenguinList2.add(xtreepenguin3);
//    xtreepenguinList2.add(xtreepenguin4);
//
//    xtreeplayer1 = new Player(13, Colour.RED, 1, xtreepenguinList1);
//    xtreeplayer2 = new Player(16, Colour.WHITE, 0, xtreepenguinList2);
//
//    xtreeplayerList1.add(xtreeplayer1);
//    xtreeplayerList1.add(xtreeplayer2);
//
//    xtreemodel1 = new FishModel(xtreeourBoard, xtreeplayerList1);
//    xtreemodel1.startGame();
//
//    xtreepenguin5 = new Penguin(Colour.RED, new Pos2D(0, 0));
//    xtreepenguin6 = new Penguin(Colour.RED, new Pos2D(5, 0));
//    xtreepenguin7 = new Penguin(Colour.WHITE, new Pos2D(3, 0));
//    xtreepenguin8 = new Penguin(Colour.WHITE, new Pos2D(2, 1));
//
//    xtreepenguinList3.add(xtreepenguin5);
//    xtreepenguinList3.add(xtreepenguin6);
//    xtreepenguinList4.add(xtreepenguin7);
//    xtreepenguinList4.add(xtreepenguin8);
//
//    xtreeplayer3 = new Player(13, Colour.RED, 1, xtreepenguinList3);
//    xtreeplayer4 = new Player(16, Colour.WHITE, 0, xtreepenguinList4);
//
//    xtreeplayerList2.add(xtreeplayer3);
//    xtreeplayerList2.add(xtreeplayer4);
//
//    xtreemodel2 = new FishModel(xtreeourBoard, xtreeplayerList2);
//    xtreemodel2.startGame();
//
//    //--------------------------------Player.InternalPlayer Test----------------------
//    internaliplayer1 = new InternalPlayer("Alice", 1, new Strategy(), 1, true);
//    internaliplayer2 = new InternalPlayer("Bob", 2, new Strategy(), 1, false);
//    internaliplayer3 = new InternalPlayer("Nick", 4, new Strategy(), 2, true);
//
//    internalfullboard1 = new Board(2, 3, 4);
//    internalfullboard2 = new Board(3, 3, 4);
//    internalfullboard3 = new Board(2,2,3);
//    internalfullboard4 = new Board(5,5,2);
//
//    internalourTiles[0][0] = new Hole();
//    internalourTiles[0][2] = new Hole();
//    internalourTiles[0][1] = new Tile(2);
//    internalourTiles[1][0] = new Hole();
//    internalourTiles[1][2] = new Tile(4);
//    internalourTiles[1][1] = new Tile(3);
//    internalourTiles[2][0] = new Tile(4);
//    internalourTiles[2][1] = new Tile(2);
//    internalourTiles[2][2] = new Hole();
//
//
//    internalplayer1 = new Player(13, Colour.BROWN);
//    internalplayer2 = new Player(16, Colour.WHITE);
//    internalplayer3 = new Player(19, Colour.WHITE);
//    internalplayer4 = new Player(20,Colour.RED);
//    internalplayer5 = new Player(21, Colour.RED);
//    internalplayer6 = new Player(27, Colour.BLACK);
//    internalplayer7 = new Player(30, Colour.RED);
//    internalplayer8 = new Player(32, Colour.BLACK);
//    internalplayer9 = new Player(33, Colour.WHITE);
//    internalplayer10 = new Player(40, Colour.BROWN);
//
//    internalaction1 = new Move(new Penguin(Colour.WHITE, new Pos2D(0,2)), new Pos2D(1,1));
//    internalaction2 = new Move(new Penguin(Colour.WHITE, new Pos2D(0,1)), new Pos2D(1,1));
//    internalaction3 = new Move(new Penguin(Colour.WHITE, new Pos2D(2, 2)), new Pos2D(1,1));
//    internalaction4 = new Move(new Penguin(Colour.BLACK, new Pos2D(2,0)), new Pos2D(2,1));
//    internalaction5 = new Move(new Penguin(Colour.BLACK, new Pos2D(2,0)), new Pos2D(1,0));
//    internalaction6 = new Move(new Penguin(Colour.BLACK, new Pos2D(2,0)), new Pos2D(2,2));
//
//
//
//    internalplayerList1 = new ArrayList<Player>();
//    internalplayerList2 = new ArrayList<Player>();
//    internalplayerList3 = new ArrayList<Player>();
//    internalplayerList4 = new ArrayList<Player>();
//    internalplayerList5 = new ArrayList<Player>();
//
//    internalactionList1 = new ArrayList<Action>();
//    internalactionList2 = new ArrayList<Action>();
//    internalactionList3 = new ArrayList<Action>();
//    internalactionList4 = new ArrayList<Action>();
//
//
//    internalplayerList1.add(internalplayer1);
//    internalplayerList1.add(internalplayer2);
//    internalplayerList2.add(internalplayer3);
//    internalplayerList2.add(internalplayer4);
//    internalplayerList3.add(internalplayer5);
//    internalplayerList3.add(internalplayer6);
//    internalplayerList4.add(internalplayer7);
//    internalplayerList4.add(internalplayer8);
//    internalplayerList5.add(internalplayer9);
//    internalplayerList5.add(internalplayer10);
//
//    internalactionList1.add(internalaction1);
//    internalactionList1.add(internalaction2);
//    internalactionList2.add(internalaction1);
//    internalactionList2.add(internalaction3);
//    internalactionList3.add(internalaction4);
//    internalactionList3.add(internalaction5);
//    internalactionList4.add(internalaction4);
//    internalactionList4.add(internalaction6);
//
//
//
//    this.internalmodel1 = new FishModel(internalfullboard1, internalplayerList1);
//    this.internalmodel2 = new FishModel(internalfullboard2, internalplayerList2);
//    this.internalmodel3 = new FishModel(internalfullboard3, internalplayerList4);
//    this.internalmodel4 = new FishModel(internalfullboard2, internalplayerList3);
//    this.internalmodel5 = new FishModel(internalholeBoard, internalplayerList5);
//
//
//    internalmodel1.placePenguin(new Pos2D(0, 1));
//    internalmodel1.placePenguin(new Pos2D(0, 0));
//    internalmodel1.placePenguin(new Pos2D(1, 1));
//    internalmodel1.placePenguin(new Pos2D(2, 1));
//
//    internalmodel1.startGame();
//
//
//    this.internalstartingNode = new GameNode(internalmodel1, null);
//
//    internalmodel3.placePenguin(new Pos2D(0,0));
//    internalmodel3.placePenguin(new Pos2D(0,1));
//    internalmodel3.placePenguin(new Pos2D(1,0));
//    internalmodel3.placePenguin(new Pos2D(1,1));
//
//    internalmodel4.placePenguin(new Pos2D(2,0));
//    internalmodel4.placePenguin(new Pos2D(0,1));
//    internalmodel4.startGame();
//
//    internalmodel5.placePenguin(new Pos2D(0,2));
//    internalmodel5.placePenguin(new Pos2D(2,1));
//    internalmodel5.startGame();
//
//    //-------------------------------Admin.Referee Test------------------------
//    refereeiplayer1 = new InternalPlayer("Alice", 1, new Strategy(), 1, true);
//    refereeiplayer2 = new InternalPlayer("Bob", 2, new Strategy(), 1, false);
//    refereeiplayer3 = new InternalPlayer("Sam", 3, new Strategy(), 2, true);
//    refereeiplayer4 = new InternalPlayer("Tina", 4, new Strategy(), 1, false);
//    refereeiplayer5 = new InternalPlayer("Julia", 5, new Strategy(), 2, true);
//    refereeiplayer6 = new InternalPlayer("Mike", 6, new Strategy(), 1, true);
//
//    refereeiplayerList1 = new ArrayList<IPlayer>();
//    refereeiplayerList2 = new ArrayList<IPlayer>();
//    refereeiplayerList3 = new ArrayList<IPlayer>();
//
//    refereeiplayerList1.add(refereeiplayer1);
//    refereeiplayerList1.add(refereeiplayer2);
//    refereeiplayerList2.add(refereeiplayer3);
//    refereeiplayerList2.add(refereeiplayer4);
//    refereeiplayerList3.add(refereeiplayer5);
//    refereeiplayerList3.add(refereeiplayer6);
//
//
//    refereereferee1 = new Referee(refereeiplayerList1, 3,3);
//    refereereferee2 = new Referee(refereeiplayerList2, 2,4);
//    refereereferee3 = new Referee(refereeiplayerList3,2,5);
//
//
//
//  }
//
//
//  //-------------------------Model.Board Test----------------------
//
//  // checks all the tile in the board has the same fish number
//  @Test
//  public void testGenerateFullBoard1() {
//    Pieces[][] board = board1.get2DTiles();
//    for (int i = 0; i < board.length; i++) {
//      for (int j = 0; j < board[0].length; j++) {
//        Pieces currentPiece = board[i][j];
//        assertEquals(currentPiece.getFishNum(), 3);
//      }
//    }
//  }
//
//  // checks all the tile in the board has the same fish number and is not a hole
//  @Test
//  public void testGenerateFullBoard2() {
//    Pieces[][] board = board2.get2DTiles();
//    for (int i = 0; i < board.length; i++) {
//      for (int j = 0; j < board[0].length; j++) {
//        Pieces currentPiece = board[i][j];
//        assertEquals(currentPiece.getFishNum(), 5);
//      }
//    }
//  }
//
//  // checks invalid hole positions and if it added to the list
//  @Test
//  public void checkHolePosition1() {
//    List<Pos2D> holesList = new ArrayList<Pos2D>();
//    Pos2D hole1 = new Pos2D(11, 2);
//    Pos2D hole2 = new Pos2D(8, 3);
//    Pos2D hole3 = new Pos2D(2, 9);
//    holesList.add(hole1);
//    holesList.add(hole2);
//    holesList.add(hole3);
//    board3.checkHolePosition(10, 5, holesList);
//    List<Pos2D> ans = new ArrayList<Pos2D>();
//    ans.add(hole2);
//    assertEquals(holesList, ans);
//  }
//
//  // test checkValidMinNum1FishTile method
//  @Test(expected = IllegalArgumentException.class)
//  public void testCheckValidMinNum1FishTile() {
//    new Board(4, 4, holesList, 2, 1, 15);
//  }
//
//  // test checkValidMinNum1FishTile method, some tiles position not in bounds, so it was removed
//  @Test(expected = IllegalArgumentException.class)
//  public void testCheckValidMinNum1FishTile2() {
//    new Board(2, 2, holesList, 2, 1, 4);
//  }
//
//  // checks removeTile method
//  @Test
//  public void testRemove() {
//    Pos2D pos = new Pos2D(1, 2);
//    board2.removeTile(pos);
//    assertEquals(board2.get2DTiles()[2][1].getFishNum(), 0);
//  }
//
//  // checks if the board generated has the correct number of holes
//  @Test
//  public void testHoleBoard1() {
//    int count = 0;
//    int OneFishCount = 0;
//    Board holeBoard1 = new Board(10, 10, holesList, 2, 1, 1);
//    for (int i = 0; i < 10; i++) {
//      for (int j = 0; j < 10; j++) {
//        if (holeBoard1.get2DTiles()[i][j].getFishNum() == 0) {
//          count++;
//        } else if (holeBoard1.get2DTiles()[i][j].getFishNum() == 1) {
//          OneFishCount++;
//        }
//      }
//    }
//    assertTrue(count == holesList.size());
//    assertTrue(OneFishCount >= 1);
//  }
//
//  // checks if the board generated has holes at the right places
//  @Test
//  public void testHoleBoard2() {
//    Board holeBoard1 = new Board(10, 10, holesList, 2, 1, 1);
//    for (int i = 0; i < holesList.size(); i++) {
//      int x = holesList.get(i).getX();
//      int y = holesList.get(i).getY();
//      assertEquals(holeBoard1.get2DTiles()[y][x].getFishNum(), 0);
//    }
//  }
//
//  // checks if the board generated has the correct number of fish in each tile
//  @Test
//  public void testHoleBoard3() {
//    Board holeBoard1 = new Board(4, 4, holesList, 2, 1, 1);
//    List<Integer> fishNums = new ArrayList<Integer>();
//    List<Integer> ans = new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 2, 1, 2, 2, 2, 2, 1, 1, 1));
//    for (int i = 0; i < holeBoard1.get2DTiles().length; i++) {
//      for (int j = 0; j < holeBoard1.get2DTiles()[0].length; j++) {
//        if (holeBoard1.get2DTiles()[i][j].getFishNum() != 0) {
//          fishNums.add(holeBoard1.get2DTiles()[i][j].getFishNum());
//        }
//      }
//    }
//    assertEquals(fishNums.size(), 13);
//    assertEquals(fishNums, ans);
//  }
//
//  // checks the possible moves, no penguins
//  @Test
//  public void testPossibleTilesBeforeHole() {
//    Board holeBoard1 = new Board(4, 4, holesList2, 2, 2, 1);
//    List<Pos2D> tiles = holeBoard1.possibleTileToMoveTo(new Pos2D(1, 1), new ArrayList<>());
//    assertEquals(tiles.size(), 9);
//    List<Pos2D> ans = new ArrayList<Pos2D>(Arrays.asList(new Pos2D(0, 1), new Pos2D(1, 0),
//            new Pos2D(0, 2), new Pos2D(1, 2), new Pos2D(2, 1), new Pos2D(3, 0),
//            new Pos2D(3, 2), new Pos2D(1, 3), new Pos2D(2, 2)));
//    assertTrue(tiles.containsAll(ans));
//  }
//
//  // checks the possible moves, no penguins
//  @Test
//  public void testPossibleTilesBeforeHole2() {
//    Board holeBoard1 = new Board(4, 4, holesList2, 2, 2, 1);
//    List<Pos2D> tiles = holeBoard1.possibleTileToMoveTo(new Pos2D(3, 3), new ArrayList<>());
//    assertEquals(tiles.size(), 4);
//    List<Pos2D> ans = new ArrayList<Pos2D>(Arrays.asList(new Pos2D(3, 2), new Pos2D(2, 3),
//            new Pos2D(1, 2), new Pos2D(0, 2)));
//    assertTrue(tiles.containsAll(ans));
//  }
//
//  // checks the possible moves, no penguins
//  @Test
//  public void testPossibleTilesBeforeHole3() {
//    Board holeBoard1 = new Board(4, 4, holesList2, 2, 2, 1);
//    List<Pos2D> tiles = holeBoard1.possibleTileToMoveTo(new Pos2D(1, 3), new ArrayList<>());
//    assertEquals(tiles.size(), 6);
//    List<Pos2D> ans = new ArrayList<Pos2D>(Arrays.asList(new Pos2D(0, 3), new Pos2D(2, 3),
//            new Pos2D(3, 2), new Pos2D(1, 2), new Pos2D(1, 1), new Pos2D(1, 0)));
//    assertTrue(tiles.containsAll(ans));
//  }
//
//  // checks the possible moves, with penguins
//  @Test
//  public void testPossibleTilesBeforePenguin() {
//    Board holeBoard1 = new Board(4, 4, holesList2, 2, 2, 1);
//    List<Pos2D> pos = new ArrayList<>();
//    pos.add(new Pos2D(1, 2));
//    List<Pos2D> tiles = holeBoard1.possibleTileToMoveTo(new Pos2D(1, 3), pos);
//    assertEquals(tiles.size(), 3);
//    List<Pos2D> ans = new ArrayList<Pos2D>(Arrays.asList(new Pos2D(0, 3), new Pos2D(2, 3),
//            new Pos2D(3, 2)));
//    assertTrue(tiles.containsAll(ans));
//  }
//
//  // checks checkValidPos
//  @Test(expected = IllegalArgumentException.class)
//  public void testCheckValidPos() {
//    board1.checkValidPos(1, 1);
//  }
//
//  // checks checkValidPos
//  @Test(expected = IllegalArgumentException.class)
//  public void testCheckValidPos1() {
//    Board board = new Board(2, 1, 1);
//    board.checkValidPos(0, 2);
//  }
//
//  // checks checkValidPos
//  @Test(expected = IllegalArgumentException.class)
//  public void testCheckValidPos2() {
//    Board board = new Board(1, 2, 1);
//    board.checkValidPos(2, 0);
//  }
//
//  // checks checkValidMove (invalid position not in straight lines)
//  @Test(expected = IllegalArgumentException.class)
//  public void testCheckValidMove1() {
//    List<Pos2D> pos = new ArrayList<Pos2D>();
//    pos.add(new Pos2D(1, 1));
//    board4.checkValidMove(new Pos2D(3, 4), new Pos2D(4, 1), pos);
//  }
//
//  // checks checkValidMove (invalid position due to penguin position)
//  @Test(expected = IllegalArgumentException.class)
//  public void testCheckValidMove2() {
//    List<Pos2D> pos = new ArrayList<Pos2D>();
//    pos.add(new Pos2D(3, 3));
//    board4.checkValidMove(new Pos2D(3, 4), new Pos2D(3, 3), pos);
//  }
//
//  //-------------------------Model.FishModel Test-------------------------
//
//  // test checkValidNumOfPlayer method if there are less than 2 players
//  @Test(expected = IllegalArgumentException.class)
//  public void checkValidNumOfPlayerLessThan2() {
//    new FishModel(modelfullboard1, modelplayerListLessThan2);
//  }
//
//  // test checkValidNumOfPlayer method if there are more than 4 players
//  @Test(expected = IllegalArgumentException.class)
//  public void checkValidNumOfPlayerMoreThan4() {
//    new FishModel(modelfullboard1, modelplayerListMoreThan4);
//  }
//
//  // test checkValidOrder method
//  @Test(expected = IllegalArgumentException.class)
//  public void checkValidOrder() {
//    new FishModel(modelfullboard1, modelplayerList2);
//  }
//
//  // test placePenguin method
//  @Test
//  public void checkPlacePenguin1() {
//    modelmodel2.placePenguin(new Pos2D(1, 1));
//    modelmodel2.placePenguin(new Pos2D(3, 7));
//    Penguin penguin = new Penguin(Colour.BROWN, new Pos2D(1, 1));
//    Penguin penguin2 = new Penguin(Colour.WHITE, new Pos2D(3, 7));
//    List<Penguin> penguinList = new ArrayList<Penguin>();
//    penguinList.add(penguin);
//    penguinList.add(penguin2);
//    assertTrue(modelmodel2.getPenguins().containsAll(penguinList));
//  }
//
//  // test placePenguin method
//  @Test
//  public void checkPlacePenguin2() {
//    modelmodel2.placePenguin(new Pos2D(1, 1));
//    assertEquals(modelplayer6.getPenguins().size(), 1);
//    assertEquals(modelplayer6.getScore(), 0);
//    assertEquals(modelfullboard2.get2DTiles()[1][1].getFishNum(), 4);
//    Assert.assertEquals(modelmodel2.getGameStatus(), GameStatus.PENDING);
//    assertEquals(modelmodel2.getCurrentTurn(), modelplayer7);
//    modelmodel2.placePenguin(new Pos2D(2, 2));
//    modelmodel2.placePenguin(new Pos2D(3, 1));
//    modelmodel2.placePenguin(new Pos2D(2, 1));
//    modelmodel2.placePenguin(new Pos2D(5, 5));
//    modelmodel2.placePenguin(new Pos2D(1, 4));
//    modelmodel2.placePenguin(new Pos2D(9, 9));
//    modelmodel2.placePenguin(new Pos2D(6, 1));
//    assertTrue(modelmodel2.getPenguinPositions().containsAll(new ArrayList<Pos2D>(Arrays.asList(new Pos2D(1, 1), new Pos2D(2, 2),
//            new Pos2D(3, 1), new Pos2D(2, 1),new Pos2D(5, 5), new Pos2D(1, 4), new Pos2D(9, 9),
//            new Pos2D(6, 1)))));
//    assertTrue(new ArrayList<Pos2D>(Arrays.asList(new Pos2D(1, 1), new Pos2D(2, 2),
//            new Pos2D(3, 1), new Pos2D(2, 1),new Pos2D(5, 5), new Pos2D(1, 4), new Pos2D(9, 9),
//            new Pos2D(6, 1))).containsAll(modelmodel2.getPenguinPositions()));
//  }
//
//  // test placePenguin method when game has starting playing
//  @Test(expected = IllegalArgumentException.class)
//  public void checkPlacePenguin3() {
//    modelmodel1.startGame();
//    modelmodel1.placePenguin(new Pos2D(9, 9));
//  }
//
//  // test checkValidPenguinToMove method
//  @Test(expected = IllegalArgumentException.class)
//  public void testCheckValidPenguinToMove1() {
//    modelmodel1.movePenguin(modelpenguinBlack, new Pos2D(1, 2));
//  }
//
//  // test checkValidPenguinToMove method
//  @Test(expected = IllegalArgumentException.class)
//  public void testCheckValidPenguinToMove2() {
//    modelmodel1.movePenguin(modelpenguinRed, new Pos2D(1, 2));
//  }
//
//  // test check invalid penguin move (invalid given position)
//  @Test(expected = IllegalArgumentException.class)
//  public void testInvalidPenguinMove1() {
//    modelmodel1.movePenguin(modelpenguinBrown, new Pos2D(10, 10));
//  }
//
//  // test check invalid penguin move (move not in possible move list)
//  @Test(expected = IllegalArgumentException.class)
//  public void testInvalidPenguinMove2() {
//    modelmodel1.movePenguin(modelpenguinBrown, new Pos2D(0, 0));
//  }
//
//  // test check game status (Playing -> Over)
//  @Test
//  public void testCheckGameStatus() {
//    modelmodel1.startGame();
//    assertEquals(modelmodel1.getGameStatus(), GameStatus.PLAYING);
//    modelfullboard1.removeTile(new Pos2D(0, 4));
//    modelfullboard1.removeTile(new Pos2D(1, 4));
//    modelfullboard1.removeTile(new Pos2D(2, 4));
//    modelfullboard1.removeTile(new Pos2D(2, 3));
//    modelfullboard1.removeTile(new Pos2D(2, 2));
//    modelfullboard1.removeTile(new Pos2D(2, 0));
//    modelfullboard1.removeTile(new Pos2D(3, 0));
//    modelfullboard1.removeTile(new Pos2D(3, 1));
//    modelmodel1.movePenguin(modelpenguinBrown, new Pos2D(2, 1));
//    assertEquals(modelmodel1.getGameStatus(), GameStatus.OVER);
//  }
//
//
//  // test check game status (Pending -> Over)
//  @Test
//  public void testCheckGameStatus2() {
//    assertEquals(modelmodel2.getGameStatus(), GameStatus.PENDING);
//    modelmodel2.placePenguin(new Pos2D(1, 1));
//    modelmodel2.placePenguin(new Pos2D(0, 1));
//    modelmodel2.placePenguin(new Pos2D(1, 2));
//    modelmodel2.placePenguin(new Pos2D(0, 2));
//    modelmodel2.placePenguin(new Pos2D(1, 0));
//    modelmodel2.placePenguin(new Pos2D(0, 0));
//    modelmodel2.placePenguin(new Pos2D(0, 3));
//    modelmodel2.placePenguin(new Pos2D(1, 3));
//    modelmodel2.startGame();
//    assertEquals(modelmodel2.getGameStatus(), GameStatus.PLAYING);
//  }
//
//  // test to move when game is over
//  @Test(expected = IllegalArgumentException.class)
//  public void testGameOver() {
//    modelfullboard1.removeTile(new Pos2D(0, 4));
//    modelfullboard1.removeTile(new Pos2D(1, 4));
//    modelfullboard1.removeTile(new Pos2D(2, 4));
//    modelfullboard1.removeTile(new Pos2D(2, 3));
//    modelfullboard1.removeTile(new Pos2D(2, 2));
//    modelfullboard1.removeTile(new Pos2D(2, 0));
//    modelfullboard1.removeTile(new Pos2D(3, 0));
//    modelfullboard1.removeTile(new Pos2D(3, 1));
//    modelmodel1.movePenguin(modelpenguinBrown, new Pos2D(2, 1));
//    modelmodel1.movePenguin(modelpenguinWhite, new Pos2D(0,5));
//  }
//
//  // test to check if penguin is not on the board
//  @Test(expected = IllegalArgumentException.class)
//  public void testNoPenguinFound() {
//    Penguin p = new Penguin(Colour.BROWN, new Pos2D(1, 2));
//    modelmodel1.movePenguin(p, new Pos2D(1, 4));
//  }
//
//  //test valid moves
//  @Test
//  public void testMovePenguin() {
//    modelmodel1.startGame();
//    modelmodel1.movePenguin(modelpenguinBrown, new Pos2D(2,1));
//    assertEquals(modelplayer1.getPenguins().get(0).getPosition(), new Pos2D(2,1));
//    assertEquals(modelplayer1.getScore(), 4);
//    assertEquals(modelmodel1.getBoard().get2DTiles()[1][1].getFishNum(), 0);
//    assertEquals(modelmodel1.getCurrentTurn(), modelplayer2);
//  }
//
//  // test all getter methods
//  @Test
//  public void testGetterMethods() {
//    List<Penguin> penguinList = new ArrayList<>();
//    penguinList.add(modelpenguinBrown);
//    penguinList.add(modelpenguinWhite);
//    penguinList.add(modelpenguinBlack);
//    penguinList.add(modelpenguinRed);
//    penguinList.add(new Penguin(Colour.BROWN, new Pos2D(1, 0)));
//    penguinList.add(new Penguin(Colour.WHITE, new Pos2D(0, 0)));
//    penguinList.add(new Penguin(Colour.BLACK, new Pos2D(0, 3)));
//    penguinList.add(new Penguin(Colour.RED, new Pos2D(1, 3)));
//    assertEquals(modelmodel1.getPlayers(), modelplayerList1);
//    assertEquals(modelmodel1.getPenguins().containsAll(penguinList), true);
//    assertEquals(penguinList.containsAll(modelmodel1.getPenguins()), true);
//  }
//
//  @Test
//  public void testGetterMethods2() {
//    assertEquals(modelmodel1.getCurrentTurn(), modelplayer1);
//    assertEquals(modelmodel1.getGameStatus(), GameStatus.PENDING);
//    assertEquals(modelmodel1.getScoreOfPlayer(modelplayer1.getColor()), 0);
//    assertTrue(modelmodel1.getPenguinPositions().containsAll(
//            new ArrayList<>(Arrays.asList(new Pos2D(1, 1), new Pos2D(0, 1),
//                    new Pos2D(1, 2), new Pos2D(0, 2), new Pos2D(1, 0),
//                    new Pos2D(0, 0), new Pos2D(0, 3), new Pos2D(1, 3)))));
//    assertTrue(new ArrayList<Pos2D>(Arrays.asList(new Pos2D(1, 1), new Pos2D(0, 1),
//            new Pos2D(1, 2), new Pos2D(0, 2), new Pos2D(1, 0),
//            new Pos2D(0, 0), new Pos2D(0, 3), new Pos2D(1, 3))).containsAll(modelmodel1.getPenguinPositions()));
//  }
//
//  @Test
//  public void testGetBoard() {
//    Pieces[][] given = modelmodel1.getBoard().get2DTiles();
//    Pieces[][] ans = modelfullboard1.get2DTiles();
//    for (int i = 0; i < given.length ; i++) {
//      for (int j = 0; j < given[0].length ; j++) {
//        assertEquals(given[i][j].getFishNum(), ans[i][j].getFishNum());
//      }
//    }
//  }
//
//  // test remove player method
//  @Test
//  public void testRemovePlayer() {
//    modelmodel1.removePlayer(modelplayer1);
//    List<Player> playerListAns = new ArrayList<>();
//    playerListAns.add(modelplayer2);
//    playerListAns.add(modelplayer3);
//    playerListAns.add(modelplayer4);
//    assertEquals(modelmodel1.getPlayers(), playerListAns);
//    List<Penguin> penguinListAns = new ArrayList<>();
//    penguinListAns.add(modelpenguinWhite);
//    penguinListAns.add(modelpenguinBlack);
//    penguinListAns.add(modelpenguinRed);
//    penguinListAns.add(new Penguin(Colour.WHITE, new Pos2D(0, 0)));
//    penguinListAns.add(new Penguin(Colour.BLACK, new Pos2D(0, 3)));
//    penguinListAns.add(new Penguin(Colour.RED, new Pos2D(1, 3)));
//    assertEquals(modelmodel1.getPenguins().containsAll(penguinListAns), true);
//    assertEquals(penguinListAns.containsAll(modelmodel1.getPenguins()), true);
//  }
//
//  // test checkCurrentPlayerCanMove method
//  @Test(expected = IllegalArgumentException.class)
//  public void testCheckCurrentPlayerCanMove() {
//    modelmodel1.skipPlayer();
//    modelmodel1.movePenguin(new Penguin(Colour.WHITE, new Pos2D(0,0)), new Pos2D(0, 1));
//  }
//
//  // test skipPlayer method
//  @Test
//  public void testSkipPlayer() {
//    assertEquals(modelmodel1.getCurrentTurn(), modelplayer1);
//    modelmodel1.skipPlayer();
//    assertEquals(modelmodel1.getCurrentTurn(), modelplayer2);
//  }
//
//  //---------------------Model.GameNode Test-------------------------
//
//  @Test(expected = IllegalArgumentException.class)
//  public void testGetParentNull() {
//    GameNode startingNode = new GameNode(nodemodel1, null);
//    startingNode.getParent();
//  }
//
//  @Test
//  public void testGetParentNonNull() {
//    GameNode startingNode = new GameNode(nodemodel1, null);
//    for (GameNode node : startingNode.generatePossibleGameState()) {
//      assertEquals(node.getParent(), startingNode);
//    }
//  }
//
//  // test generate possibleGameState method with a skip
//  @Test
//  public void testGeneratePossibleGameState1() {
//    IFishModel copy1 = new FishModel(nodemodel1);
//    copy1.skipPlayer();
//    copy1.skipPlayer();
//    GameNode startingNode = new GameNode(copy1, null);
//    IFishModel copy2 = copy1.copyFishModel();
//    copy2.skipPlayer();
//    List<GameNode> ans = new ArrayList<>(Arrays.asList(new GameNode(copy2, startingNode)));
//    assertEquals(startingNode.generatePossibleGameState(), ans);
//  }
//
//  // test generate possibleGameState method without a skip because game over
//  @Test
//  public void testGeneratePossibleGameState2() {
//    GameNode startingNode = new GameNode(nodemodel1, null);
//    GameNode node = new GameNode(startingNode.takeAction(new Move(new Penguin(Colour.BROWN, new Pos2D(1, 1)), new Pos2D(2, 2))), startingNode);
//    List<GameNode> ans = new ArrayList<>();
//    assertEquals(node.generatePossibleGameState(), ans);
//  }
//
//  // test takeAction method, legal move
//  @Test
//  public void testTakeAction1() {
//    GameNode startingNode = new GameNode(nodemodel1, null);
//    FishModel copy1 = new FishModel(nodemodel1);
//    copy1.movePenguin(new Penguin(Colour.BROWN, new Pos2D(1,1)), new Pos2D(2,2));
//    assertEquals(startingNode.takeAction(new Move(new Penguin(Colour.BROWN, new Pos2D(1,1)), new Pos2D(2,2))), copy1);
//  }
//
//  // test takeAction method, illegal move
//  @Test(expected = IllegalArgumentException.class)
//  public void testTakeAction2() {
//    GameNode startingNode = new GameNode(nodemodel1, null);
//    startingNode.takeAction(new Move(new Penguin(Colour.BROWN, new Pos2D(1,1)), new Pos2D(0,2)));
//  }
//
//
//  // test takeAction method, illegal move (to the same spot)
//  @Test(expected = IllegalArgumentException.class)
//  public void testTakeAction3() {
//    GameNode startingNode = new GameNode(nodemodel1, null);
//    startingNode.takeAction(new Move(new Penguin(Colour.BROWN, new Pos2D(0,1)), new Pos2D(0,1)));
//  }
//
//  // test applies function method
//  @Test
//  public void testAppliesFunction() {
//    GameNode startingNode = new GameNode(nodemodel1, null);
//    ArrayList<Integer> ans = new ArrayList<>(Arrays.asList(12));
//    Function<IFishModel, Integer> getYoungestPlayerScore = a -> a.getPlayers().get(0).getScore();
//    assertEquals(startingNode.appliesFunction(getYoungestPlayerScore), ans);
//  }
//
//  //-------------------------Model.Penguin Test---------------
//  //test for determineColor method
//  @Test
//  public void testColorOfPenguin() {
//    assertEquals(penguinRed.determineColor(), Color.RED);
//    assertEquals(penguinWhite.determineColor(), Color.WHITE);
//    assertEquals(penguinBlack.determineColor(), Color.BLACK);
//    assertEquals(penguinBrown.determineColor(), new Color(130, 79, 35));
//
//  }
//
//  // test getter methods
//  @Test
//  public void testPenguinGetterMethods() {
//    assertEquals(penguinRed.getColor(), Colour.RED);
//    assertEquals(penguinBlack.getPosition(), new Pos2D(9, 5));
//  }
//
//  // test updatePos method
//  @Test
//  public void testUpdatePos() {
//    penguinBlack.updatePos(new Pos2D(5, 5));
//    assertEquals(penguinBlack.getPosition(), new Pos2D(5, 5));
//  }
//
//  //----------------------------Piece Test------------------------
//  // test the method checkFishNum
//  @Test(expected = IllegalArgumentException.class)
//  public void checkNegFishNum() {
//    new Tile(-1);
//  }
//
//  // test the method checkFishNum
//  @Test(expected = IllegalArgumentException.class)
//  public void checkMoreThan5FishNum() {
//    new Tile(10);
//  }
//
//  // test the method getFishNum
//  @Test
//  public void checkGetFishNum() {
//    assertEquals(5, piecetile1.getFishNum());
//  }
//
//  // test the method checkIfPosIsHole
//  @Test(expected = IllegalArgumentException.class)
//  public void testCheckIfPosIsHole() {
//    piecehole1.checkIfPosIsHole();
//  }
//
//  //------------------------------Model.Player Test------------------------
//  // test getter methods
//  @Test
//  public void testPlayerGetterMethods() {
//    assertEquals(playerplayer1.getColor(), Colour.BROWN);
//    assertEquals(playerplayer1.getScore(), 0);
//    assertEquals(playerplayer1.getAge(), 13);
//    assertEquals(playerplayer2.getPenguins(), new ArrayList<>(Arrays.asList(playerpenguin1, playerpenguin2)));
//  }
//
//  // test addScore method
//  @Test
//  public void testAddScore() {
//    assertEquals(playerplayer1.getScore(), 0);
//    playerplayer1.addScore(4);
//    assertEquals(playerplayer1.getScore(), 4);
//  }
//
//  // test addPenguin / placedAllPenguin method
//  @Test
//  public void testPlacedAllPenguin() {
//    assertEquals(playerplayer1.getPenguins().size(), 0);
//    playerplayer1.addPenguin(new Pos2D(1, 1));
//    assertEquals(playerplayer1.getPenguins().size(), 1);
//    playerplayer1.addPenguin(new Pos2D(2, 2));
//    assertEquals(playerplayer1.getPenguins().size(), 2);
//  }
//
//
//  // test updatePenguin method
//  @Test
//  public void testUpdatePenguin() {
//    playerplayer1.addPenguin(new Pos2D(1, 1));
//    playerplayer1.updatePenguin(new Penguin(Colour.BROWN, new Pos2D(1, 1)), new Pos2D(0, 1));
//    assertEquals(playerplayer1.getPenguins().get(0).getPosition(), new Pos2D(0, 1));
//  }
//
//  // test updatePenguin method (no such penguin)
//  @Test(expected = IllegalArgumentException.class)
//  public void testUpdatePenguin1() {
//    playerplayer1.updatePenguin(new Penguin(Colour.BROWN, new Pos2D(1, 1)), new Pos2D(0, 1));
//  }
//
//  // test checkGameOver method
//  @Test
//  public void testCheckGameOver() {
//    Board board = new Board(1, 1, 4);
//    playerplayer1.addPenguin(new Pos2D(0, 0));
//    assertTrue(playerplayer1.hasNoMoveAvailable(board, new ArrayList<>()));
//  }
//
//  // test hasNoMoveAvailable method
//  @Test
//  public void testHasNoMoveAvailable() {
//    assertFalse(playerplayer2.hasNoMoveAvailable(playerfullBoard, new ArrayList<>(Arrays.asList(new Pos2D(1, 1), new Pos2D(0, 1)))));
//    playerfullBoard.removeTile(new Pos2D(0, 0));
//    playerfullBoard.removeTile(new Pos2D(1, 0));
//    assertTrue(playerplayer2.hasNoMoveAvailable(playerfullBoard, new ArrayList<>(Arrays.asList(new Pos2D(1, 1), new Pos2D(0, 1)))));
//  }
//
//  //--------------------------Model.Pos2D Test---------------------
//  // invalid x position
//  @Test(expected = IllegalArgumentException.class)
//  public void checkPos1() {
//    new Pos2D(-1, 3);
//  }
//
//  // invalid y position
//  @Test(expected = IllegalArgumentException.class)
//  public void checkPos2() {
//    new Pos2D(0, -10);
//  }
//
//  @Test
//  public void checkGetterMethods() {
//    assertEquals(pospos1.getX(), 1);
//    assertEquals(pospos1.getY(), 2);
//  }
//
//  //----------------------------Player.Strategy Test--------------
//
//
//  // test for zigZagPenguinPlacement method when the board is not big enough to place all the penguins
//  @Test(expected = IllegalArgumentException.class)
//  public void testZigZagNotBigEnough(){
//    new Strategy().getPenguinPlacement(strategymodel3);
//  }
//
//  // test for zigZagPenguinPlacement method
//  @Test
//  public void testZigZag1(){
//    assertEquals(new Strategy().getPenguinPlacement(strategymodel1), new Pos2D(2,0));
//  }
//
//
//  // test for zigZagPenguinPlacement method without any penguin on the board
//  @Test
//  public void testZigZagEmpty(){
//    assertEquals(new Strategy().getPenguinPlacement(strategymodel2), new Pos2D(0,0));
//  }
//
//
//  @Test
//  public void testZigZag2(){
//    strategymodel2.placePenguin(new Pos2D(new Strategy().getPenguinPlacement(strategymodel2)));
//    assertEquals(new Strategy().getPenguinPlacement(strategymodel2), new Pos2D(2,0));
//    strategymodel2.placePenguin(new Pos2D(new Strategy().getPenguinPlacement(strategymodel2)));
//    assertEquals(new Strategy().getPenguinPlacement(strategymodel2), new Pos2D(1,0));
//    strategymodel2.placePenguin(new Pos2D(new Strategy().getPenguinPlacement(strategymodel2)));
//    assertEquals(new Strategy().getPenguinPlacement(strategymodel2), new Pos2D(0,1));
//
//  }
//
//  // test for method minMaxAction with full board and same fish numbers
//  @Test
//  public void minMaxAction1() {
//    assertEquals(new Strategy().getPlayerAction(strategymodel1, 2),
//            new Move(new Penguin(Colour.BROWN, new Pos2D(0,1)), new Pos2D(2,0)));
//  }
//
//  // test for method minMaxAction with full board and same fish numbers
//  @Test
//  public void minMaxAction2(){
//    assertEquals(new Strategy().getPlayerAction(strategymodel4,2),
//            new Move(new Penguin(Colour.RED, new Pos2D(2,0)),new Pos2D(1,0)));
//  }
//
//
//  // test for method minMaxAction with hole board and different fish numbers
//  @Test
//  public void minMaxAction3() {
//    assertEquals(new Strategy().getPlayerAction(strategymodel5, 2),
//            new Move(new Penguin(Colour.WHITE, new Pos2D(0, 2)), new Pos2D(1, 1)));
//
//  }
//
//  // test for tieBreaker method when row numbers of from position are different
//  @Test
//  public void tieBreaker1(){
//    Action ans = new Move(new Penguin(Colour.WHITE, new Pos2D(0,1)), new Pos2D(1,1));
//    assertEquals(new Strategy().tieBreaker(strategyactionList1), ans);
//  }
//
//  // test for tieBreaker method when row numbers of from position are the same, but column numbers
//  // are different
//  @Test
//  public void tieBreaker2(){
//    Action ans = new Move(new Penguin(Colour.WHITE, new Pos2D(0,2)), new Pos2D(1,1));
//    assertEquals(new Strategy().tieBreaker(strategyactionList2), ans);
//
//  }
//
//  // test for tieBreaker method when from positions are the same,
//  // but row numbers for to position are different
//  @Test
//  public void tieBreaker3(){
//    Action ans = new Move(new Penguin(Colour.BLACK, new Pos2D(2,0)), new Pos2D(1,0));
//    assertEquals(new Strategy().tieBreaker(strategyactionList3), ans);
//  }
//
//  // test for tieBreaker method when from positions are the same, and row numbers for to positions
//  // are the same, but column numbers for to positions are different
//  @Test
//  public void tieBreaker4(){
//    Action ans = new Move(new Penguin(Colour.BLACK, new Pos2D(2,0)), new Pos2D(2,1));
//    assertEquals(new Strategy().tieBreaker(strategyactionList4), ans);
//
//  }
//
//
//  // -----------------------------xBoard Test---------------------
//
//
//  // test method to make an empty board
//  @Test
//  public void xboardtestMakeEmptyBoard() {
//    Pieces[][] emptyBoard = xboard.makeEmptyBoard(2, 3);
//    for (int i = 0; i < 2; i++) {
//      for (int j = 0; j < 3; j++) {
//        assertEquals(emptyBoard[i][j].getFishNum(), 0);
//      }
//    }
//  }
//
//  // test conversion of given data representation of board to our representation of board
//  @Test
//  public void xboardtestConversionBoard() {
//    for (int i = 0; i < xboardourBoard.get2DTiles().length; i++) {
//      for (int j = 0; j < xboardourBoard.get2DTiles()[0].length; j++) {
//        assertEquals(xboardourBoard.get2DTiles()[i][j].getFishNum(), xboard.getOurBoard(xboardgivenBoard).get2DTiles()[i][j].getFishNum());
//      }
//    }
//  }
//
//  // test get longest row method
//  @Test
//  public void xboardtestLongestRowMethod() {
//    JsonArray row = new JsonArray();
//    row.add(1);
//    row.add(1);
//    row.add(1);
//    row.add(1);
//    row.add(1);
//    xboardgivenBoard.add(row);
//    assertEquals(xboard.getLongestRow(4, xboardgivenBoard), 3);
//  }
//
//  // test if it's less than 25 tiles total
//  @Test(expected = IllegalArgumentException.class)
//  public void xboardtestValidBoard() {
//    JsonArray row = new JsonArray();
//    row.add(1);
//    row.add(1);
//    row.add(1);
//    row.add(1);
//    row.add(1);
//    row.add(1);
//    row.add(1);
//    xboardgivenBoard.add(row);
//    xboard.checkValidBoard(xboardgivenBoard);
//  }
//
//  // test conversion of given data representation of position to our representation of position
//  @Test
//  public void xboardtestConversionPosition() {
//    JsonArray position = new JsonArray();
//    position.add(1);
//    position.add(1);
//    assertEquals(xboard.getOurPos2D(position), new Pos2D(3, 0));
//  }
//
//  // test conversion of given data representation of position to our representation of position
//  @Test
//  public void xboardtestConversionPosition1() {
//    JsonArray position = new JsonArray();
//    position.add(0);
//    position.add(1);
//    assertEquals(xboard.getOurPos2D(position), new Pos2D(2, 0));
//  }
//
//  // test conversion of given data representation of position to our representation of position
//  @Test
//  public void xboardtestConversionPosition2() {
//    JsonArray position = new JsonArray();
//    position.add(2);
//    position.add(0);
//    assertEquals(xboard.getOurPos2D(position), new Pos2D(0, 1));
//  }
//
//  //---------------------------xState Test----------------------
//
//  private void provideInput(String data) {
//    xstatetestIn = new ByteArrayInputStream(data.getBytes());
//    System.setIn(xstatetestIn);
//  }
//
//  private String getOutput() {
//    return xstatetestOut.toString();
//  }
//
//  @After
//  public void restoreSystemInputOutput() {
//    System.setIn(xstatesystemIn);
//    System.setOut(xstatesystemOut);
//  }
//
//
//  // test method to make an empty board
//  @Test
//  public void testMakeEmptyBoard() {
//    Pieces[][] emptyBoard = xstate.makeEmptyBoard(2,3);
//    for (int i = 0; i < 2; i++) {
//      for (int j = 0; j < 3; j++) {
//        assertEquals(emptyBoard[i][j].getFishNum(), 0);
//      }
//    }
//  }
//
//  // test conversion of given data representation of board to our representation of board
//  @Test
//  public void testConversionBoard() {
//    for (int i = 0; i < xstateourBoard.get2DTiles().length; i++) {
//      for (int j = 0; j < xstateourBoard.get2DTiles()[0].length; j++) {
//        assertEquals(xstateourBoard.get2DTiles()[i][j].getFishNum(), xstate.getOurBoard(xstategivenBoard).get2DTiles()[i][j].getFishNum());
//      }
//    }
//  }
//
//  // test get longest row method
//  @Test
//  public void testLongestRowMethod() {
//    JsonArray row = new JsonArray();
//    row.add(1);
//    row.add(1);
//    row.add(1);
//    row.add(1);
//    row.add(1);
//    xstategivenBoard.add(row);
//    assertEquals(xstate.getLongestRow(4,xstategivenBoard), 3);
//  }
//
//  // test if it's less than 25 tiles total
//  @Test(expected = IllegalArgumentException.class)
//  public void testValidBoard() {
//    JsonArray row = new JsonArray();
//    row.add(1);
//    row.add(1);
//    row.add(1);
//    row.add(1);
//    row.add(1);
//    row.add(1);
//    row.add(1);
//    xstategivenBoard.add(row);
//    xstate.checkValidBoard(xstategivenBoard);
//  }
//
//  // test conversion of given data representation of position to our representation of position
//  @Test
//  public void testConversionPosition() {
//    JsonArray position = new JsonArray();
//    position.add(1);
//    position.add(1);
//    assertEquals(xstate.getOurPos2D(position), new Pos2D(3, 0));
//  }
//
//  // test conversion of given data representation of position to our representation of position
//  @Test
//  public void testConversionPosition1() {
//    JsonArray position = new JsonArray();
//    position.add(0);
//    position.add(1);
//    assertEquals(xstate.getOurPos2D(position), new Pos2D(2, 0));
//  }
//
//  // test conversion of given data representation of position to our representation of position
//  @Test
//  public void testConversionPosition2() {
//    JsonArray position = new JsonArray();
//    position.add(2);
//    position.add(0);
//    assertEquals(xstate.getOurPos2D(position), new Pos2D(0, 1));
//  }
//
//  // test formatting output board
//  @Test
//  public void testFormatting() {
//    JsonArray input = new JsonArray();
//    JsonArray row1 = new JsonArray();
//    input.add(row1);
//    JsonArray output = new JsonArray();
//    JsonArray row2 = new JsonArray();
//    output.add(row2);
//    JsonArray row3 = new JsonArray();
//    output.add(row3);
//    assertEquals(xstate.reformatOutputBoard(output,input).size(),1);
//  }
//
//  // test converting from our version of players to output version
//  @Test
//  public void testGeneratePlayerOutput() {
//    JsonArray ans = new JsonArray();
//    JsonObject player1 = new JsonObject();
//    player1.add("color", new JsonPrimitive("red"));
//    player1.add("score", new JsonPrimitive(1));
//    JsonArray player1Penguins = new JsonArray();
//    JsonArray player1Penguins1 = new JsonArray();
//    player1Penguins1.add(0);
//    player1Penguins1.add(0);
//    player1Penguins.add(player1Penguins1);
//    JsonArray player1Penguins2 = new JsonArray();
//    player1Penguins2.add(1);
//    player1Penguins2.add(1);
//    player1Penguins.add(player1Penguins2);
//    player1.add("places", player1Penguins);
//    ans.add(player1);
//
//    JsonObject player2 = new JsonObject();
//    player2.add("color", new JsonPrimitive("white"));
//    player2.add("score", new JsonPrimitive(0));
//    JsonArray player2Penguins = new JsonArray();
//    JsonArray player2Penguins1 = new JsonArray();
//    player2Penguins1.add(2);
//    player2Penguins1.add(2);
//    player2Penguins.add(player2Penguins1);
//    JsonArray player2Penguins2 = new JsonArray();
//    player2Penguins2.add(2);
//    player2Penguins2.add(1);
//    player2Penguins.add(player2Penguins2);
//    player2.add("places", player2Penguins);
//    ans.add(player2);
//
//    assertEquals(xstate.generatePlayerOutput(xstatemodel1), ans);
//  }
//
//  // test converting from our version of board to output version
//  @Test
//  public void testGenerateBoardOutput() {
//    JsonArray ans = new JsonArray();
//    JsonArray row1 = new JsonArray();
//    row1.add(1);
//    row1.add(0);
//    row1.add(1);
//    ans.add(row1);
//    JsonArray row2 = new JsonArray();
//    row2.add(2);
//    row2.add(1);
//    row2.add(3);
//    ans.add(row2);
//    JsonArray row3 = new JsonArray();
//    row3.add(1);
//    row3.add(1);
//    row3.add(1);
//    ans.add(row3);
//    JsonArray row4 = new JsonArray();
//    row4.add(0);
//    row4.add(0);
//    row4.add(0);
//    ans.add(row4);
//
//    assertEquals(xstate.generateBoardOutput(xstatemodel1), ans);
//  }
//
//  // test silly player strategy
//  @Test
//  public void testSillyPlayerStrategy() {
//    IFishModel copy = new FishModel(xstatemodel1);
//    copy.movePenguin(xstatepenguin1, new Pos2D(1,0));
//    assertTrue(xstate.sillyPlayerStrategy(xstatemodel1));
//    assertEquals(xstatemodel1, copy);
//  }
//
//  // test getOurPenguins method
//  @Test
//  public void testGetOurPenguins(){
//    List<Penguin> ans = new ArrayList<Penguin>();
//    ans.add(xstatepenguin1);
//    ans.add(new Penguin(Colour.RED, new Pos2D(2,0)));
//    JsonArray penguins = new JsonArray();
//    JsonArray penguin1Pos = new JsonArray();
//    JsonArray penguin2Pos = new JsonArray();
//    penguin1Pos.add(0);
//    penguin1Pos.add(0);
//    penguins.add(penguin1Pos);
//    penguin2Pos.add(0);
//    penguin2Pos.add(1);
//    penguins.add(penguin2Pos);
//    assertEquals(xstate.getOurPenguins(penguins, Colour.RED), ans);
//
//  }
//
//  // test getOurPlayers method
//  @Test
//  public void testGetOurPlayers(){
//    List<Player> ans = new ArrayList<>();
//    List<Penguin> penguinsans = new ArrayList<>();
//    JsonObject player1 = new JsonObject();
//    player1.add("color", new JsonPrimitive("red"));
//    player1.add("score", new JsonPrimitive(1));
//    JsonArray penguins = new JsonArray();
//    JsonArray penguin1Pos = new JsonArray();
//    JsonArray penguin2Pos = new JsonArray();
//    penguin1Pos.add(0);
//    penguin1Pos.add(0);
//    penguins.add(penguin1Pos);
//    penguin2Pos.add(0);
//    penguin2Pos.add(1);
//    penguins.add(penguin2Pos);
//    player1.add("places", penguins);
//    penguinsans.add(new Penguin(Colour.RED, new Pos2D(0,0)));
//    penguinsans.add(new Penguin(Colour.RED, new Pos2D(2,0)));
//    ans.add(new Player(1, Colour.RED, 1, penguinsans));
//
//    JsonObject player2 = new JsonObject();
//    List<Penguin> penguinsans2 = new ArrayList<>();
//    player2.add("color", new JsonPrimitive("white"));
//    player2.add("score", new JsonPrimitive(0));
//    JsonArray player2Penguins = new JsonArray();
//    JsonArray player2Penguins1 = new JsonArray();
//    player2Penguins1.add(1);
//    player2Penguins1.add(0);
//    player2Penguins.add(player2Penguins1);
//    JsonArray player2Penguins2 = new JsonArray();
//    player2Penguins2.add(3);
//    player2Penguins2.add(0);
//    player2Penguins.add(player2Penguins2);
//    player2.add("places", player2Penguins);
//    penguinsans2.add(new Penguin(Colour.WHITE, new Pos2D(1,0)));
//    penguinsans2.add(new Penguin(Colour.WHITE, new Pos2D(1,1)));
//    ans.add(new Player(2, Colour.WHITE, 0, penguinsans2));
//
//    JsonArray players = new JsonArray();
//    players.add(player1);
//    players.add(player2);
//
//    assertEquals(xstate.getOurPlayers(players), ans);
//
//  }
//
//
//  //---------------------------xTree Test----------------------------
//  private void xtreeprovideInput(String data) {
//    xtreetestIn = new ByteArrayInputStream(data.getBytes());
//    System.setIn(xtreetestIn);
//  }
//
//  private String xtreegetOutput() {
//    return xtreetestOut.toString();
//  }
//
//  @After
//  public void xtreerestoreSystemInputOutput() {
//    System.setIn(xtreesystemIn);
//    System.setOut(xtreesystemOut);
//  }
//
//  // test read, parse and print
//  @Test
//  public void xtreetestCase1() {
//    String testString = "{ \"state\": { \"players\" : [{\"color\": \"red\", \"score\": 4, \"places\": [[2,1],[0,1],[1,1],[1,2]]},\n" +
//            "  {\"color\": \"white\", \"score\": 5, \"places\": [[0,2],[3,1],[4,0],[2,2]]}],\n" +
//            "  \"board\" : [[1, 2, 3], [0, 1, 2], [5, 2, 2], [4, 3, 5], [2, 1, 2]]},\n" +
//            "\"from\":  [1,2],\n" +
//            "\"to\":  [3,2]}";
//    xtreeprovideInput(testString);
//
//    xtree.main(new String[0]);
//
//    assertEquals(xtreegetOutput(), "[[2,2],[4,2]]");
//  }
//
//  // test method to generate action output
//  @Test
//  public void xtreetestGenerateActionOutput() {
//    Action action = new Move(new Penguin(Colour.RED, new Pos2D(1,1)), new Pos2D(2,2));
//    JsonArray outputAction = new JsonArray();
//    JsonArray fromAction = new JsonArray();
//    fromAction.add(3);
//    fromAction.add(0);
//    outputAction.add(fromAction);
//    JsonArray toAction = new JsonArray();
//    toAction.add(4);
//    toAction.add(1);
//    outputAction.add(toAction);
//    assertEquals(xtree.generateActionOutput(action), outputAction);
//  }
//
//  // test method to the neighboring position to move to (moving from Model.Pos2D(2,0) -> Model.Pos2D(3,0))
//  // multiple directions to choose from, should go clockwise
//  @Test
//  public void xtreetestPossibleNeighboringAction1() {
//    assertEquals(xtreemodel1.getCurrentTurn(), xtreeplayer1);
//    xtreemodel1.movePenguin(xtreepenguin1, new Pos2D(3,0));
//    assertEquals(xtree.possibleNeighboringAction(xtreemodel1, new Pos2D(3,0)), new Move(xtreepenguin4, new Pos2D(4,0)));
//  }
//
//  // test method to the neighboring position to move to
//  // tie breaker test (multiple penguins going to the same tile)
//  @Test
//  public void xtreetestPossibleNeighboringAction2() {
//    assertEquals(xtreemodel2.getCurrentTurn(), xtreeplayer3);
//    xtreemodel2.movePenguin(xtreepenguin5, new Pos2D(1,0));
//    Action ans = xtree.possibleNeighboringAction(xtreemodel2, new Pos2D(1,0));
//    assertEquals(ans, new Move(xtreepenguin7, new Pos2D(2,0)));
//  }
//
//  //--------------------------------Player.InternalPlayer Test-------------------------------
//  // test for receiveInformationAboutEnd method
//  @Test
//  public void testReceiveInformationAboutEndTrue(){
//    assertEquals(internaliplayer1.receiveInformationAboutEnd(), true);
//  }
//
//  @Test
//  public void testReceiveInformationAboutEndFalse(){
//    assertEquals(internaliplayer2.receiveInformationAboutEnd(), false);
//  }
//
//  // test for getPlayerName method
//  @Test
//  public void testGetPlayerName1(){
//    assertEquals(internaliplayer1.getPlayerName(), "Alice");
//  }
//
//  @Test
//  public void testGetPlayerName2(){
//    assertEquals(internaliplayer2.getPlayerName(), "Bob");
//  }
//
//  // test for getPlayerAge method
//  @Test
//  public void testGetPlayerAge1(){
//    assertEquals(internaliplayer1.getPlayerAge(),1);
//  }
//
//  @Test
//  public void testGetPlayerAge2(){
//    assertEquals(internaliplayer2.getPlayerAge(),2);
//  }
//
//  // test for startGame and endGame method
//  @Test
//  public void testStartAndEndGame(){
//    assertEquals(internaliplayer1.getGameStatus(), GameStatus.PENDING);
//    internaliplayer1.startGame();
//    assertEquals(internaliplayer1.getGameStatus(), GameStatus.PLAYING);
//    assertEquals(internaliplayer2.getGameStatus(), GameStatus.PENDING);
//    internaliplayer2.endGame();
//    assertEquals(internaliplayer2.getGameStatus(), GameStatus.OVER);
//  }
//
//
//  // test for giveEndInformation method
//  @Test
//  public void testGiveEndInformation(){
//    HashMap<String, Integer> winnerList = new HashMap<>();
//    winnerList.put("Andy", 10);
//    winnerList.put("Sam", 10);
//    winnerList.put("Nick",10);
//    internaliplayer1.giveEndInformation(winnerList);
//    assertEquals(internaliplayer1.getWinnerInfo(), winnerList);
//    internaliplayer3.giveEndInformation(winnerList);
//    assertEquals(internaliplayer3.getWinnerInfo(), winnerList);
//  }
//
//
//  // test for getPositionToPlace method when the board is not big enough to place all the penguins
//  @Test(expected = IllegalArgumentException.class)
//  public void testGetPositionToPlaceNotBigEnough(){
//    internaliplayer1.getPositionToPlace(internalmodel3);
//  }
//
//  // test for GetPositionToPlace method
//  @Test
//  public void testGetPositionToPlace1(){
//    assertEquals(internaliplayer1.getPositionToPlace(internalmodel1), new Pos2D(2,0));
//  }
//
//
//  // test for GetPositionToPlace method without any penguin on the board
//  @Test
//  public void testGetPositionToPlaceEmpty(){
//    assertEquals(internaliplayer2.getPositionToPlace(internalmodel2), new Pos2D(0,0));
//  }
//
//
//  @Test
//  public void testGetPositionToPlace2(){
//    internalmodel2.placePenguin(new Pos2D(internaliplayer1.getPositionToPlace(internalmodel2)));
//    assertEquals(internaliplayer1.getPositionToPlace(internalmodel2), new Pos2D(2,0));
//    internalmodel2.placePenguin(new Pos2D(internaliplayer1.getPositionToPlace(internalmodel2)));
//    assertEquals(internaliplayer1.getPositionToPlace(internalmodel2), new Pos2D(1,0));
//    internalmodel2.placePenguin(new Pos2D(internaliplayer1.getPositionToPlace(internalmodel2)));
//    assertEquals(internaliplayer1.getPositionToPlace(internalmodel2), new Pos2D(0,1));
//  }
//
//  // test for method getMove with full board and same fish numbers, player depth 1
//  @Test
//  public void getMove1() {
//    assertEquals(internaliplayer2.getMove(internalmodel1),
//            new Move(new Penguin(Colour.BROWN, new Pos2D(0,1)), new Pos2D(2,0)));
//  }
//
//  // test for method getMove with full board and same fish numbers, player depth 2
//  @Test
//  public void getMove2(){
//    assertEquals(internaliplayer3.getMove(internalmodel4),
//            new Move(new Penguin(Colour.RED, new Pos2D(2,0)),new Pos2D(1,0)));
//  }
//
//
//  // test for method getMove with hole board and different fish numbers, player depth 2
//  @Test
//  public void getMove3() {
//    assertEquals(internaliplayer3.getMove(internalmodel5),
//            new Move(new Penguin(Colour.WHITE, new Pos2D(0, 2)), new Pos2D(1, 1)));
//
//  }
//
//  //-----------------------------------Admin.Referee Test--------------------------
//
//  // check whether the referee ended the game
//  @Test
//  public void testGameStatus(){
//    List<IPlayer> players = new ArrayList<IPlayer>(refereereferee1.getPlayersInThisGame().values());
//    assertEquals(players.get(0).getGameStatus(), GameStatus.OVER);
//    assertEquals(players.get(1).getGameStatus(), GameStatus.OVER);
//    assertEquals(refereereferee1.getGameState().getGameStatus(), GameStatus.OVER);
//  }
//
//
//  // checks whether the referee did the correct actions when there is only one move by checking the penguin positions
//  @Test
//  public void testCorrectRefereeAction1(){
//    // check whether all the penguins are on the correct positions after being moved by the referee
//    List<Pos2D> penguinPos = new ArrayList<Pos2D>(Arrays.asList(new Pos2D(0,0),new Pos2D(2,0),
//            new Pos2D(1,0), new Pos2D(0,1), new Pos2D(2,1), new Pos2D(1,1), new Pos2D(2,2),
//            new Pos2D(1,2)));
//    assertTrue(refereereferee1.getGameState().getPenguinPositions().containsAll(penguinPos));
//    assertTrue(penguinPos.containsAll(refereereferee1.getGameState().getPenguinPositions()));
//
//    // check whether the fish number in (2,0) is zero (move)
//    assertEquals(refereereferee1.getGameState().getBoard().get2DTiles()[2][0].getFishNum(), 0);
//
//  }
//
//  // check whether the referee did the correct actions when there is no more available moves after placing
//  // all the penguins, by checking the penguin positions
//  @Test
//  public void testCorrectRefereeAction2(){
//    // check whether all the penguins are placed on the correct positions by the referee
//    List<Pos2D> penguinPos = new ArrayList<Pos2D>(Arrays.asList(new Pos2D(0,0),new Pos2D(2,0),
//            new Pos2D(1,0), new Pos2D(3,0), new Pos2D(0,1), new Pos2D(2,1),
//            new Pos2D(1,1), new Pos2D(3,1)));
//    assertTrue(refereereferee2.getGameState().getPenguinPositions().containsAll(penguinPos));
//    assertTrue(penguinPos.containsAll(refereereferee2.getGameState().getPenguinPositions()));
//  }
//
//  // check whether the referee did the correct actions when there are two movements by checking
//  // the penguin positions
//  @Test
//  public void testCorrectRefereeAction3(){
//    // check whether all the penguins are on the correct positions after being moved by the referee
//    List<Pos2D> penguinPos = new ArrayList<Pos2D>(Arrays.asList(new Pos2D(0,0),new Pos2D(2,0),
//            new Pos2D(4,0), new Pos2D(0,1),new Pos2D(2,1), new Pos2D(4,1),
//            new Pos2D(1,1),new Pos2D(3,1)));
//    assertTrue(refereereferee3.getGameState().getPenguinPositions().containsAll(penguinPos));
//    assertTrue(penguinPos.containsAll(refereereferee3.getGameState().getPenguinPositions()));
//
//    //check whether the fish numbers in (0,1) and (0,3) are zero (move)
//    assertEquals(refereereferee3.getGameState().getBoard().get2DTiles()[0][1].getFishNum(),0);
//    assertEquals(refereereferee3.getGameState().getBoard().get2DTiles()[0][3].getFishNum(),0);
//
//
//  }
//
//  // test getWinner method when there is only one possible move
//  @Test
//  public void testGetWinner1(){
//    List<IPlayer> winner = new ArrayList<>();
//    winner.add(refereeiplayer1);
//    assertEquals(refereereferee1.getWinner(),winner);
//  }
//
//  // test getWinner method when there is no possible move after placing all penguins
//  @Test
//  public void testGetWinner2(){
//    List<IPlayer> winners = new ArrayList<>();
//    winners.add(refereeiplayer3);
//    winners.add(refereeiplayer4);
//    assertEquals(refereereferee2.getWinner(),winners);
//  }
//
//  // test getWinner method when there are two movements
//  @Test
//  public void testGetWinner3(){
//    List<IPlayer> winners = new ArrayList<>();
//    winners.add(refereeiplayer5);
//    winners.add(refereeiplayer6);
//    assertEquals(refereereferee3.getWinner(),winners);
//
//  }
//
//  // test whether the referee assigned different colors to players
//  @Test
//  public void testAssignColor(){
//    List<Colour> playerColors = new ArrayList<Colour>(refereereferee1.getPlayersInThisGame().keySet());
//    assertFalse(playerColors.get(0).equals(playerColors.get(1)));
//  }
//
//  // test whether the referee has gotten the correct scores when there is only one movement
//  @Test
//  public void testWinnerScore1(){
//    assertEquals(refereereferee1.getWinningScore(),15);
//  }
//
//  // test whether the referee has gotten the correct scores when there is no available move after
//  // placing all penguins (2 players have same score)
//  @Test
//  public void testWinnerScore2(){
//    assertEquals(refereereferee2.getWinningScore(), 12);
//  }
//
//  // test whether the referee has gotten teh correct scores when there are two movements, one
//  // for each player (2 players have same score)
//  @Test
//  public void testWinnerScore3(){
//    assertEquals(refereereferee3.getWinningScore(), 15);
//  }
//
//  // check whether player who wants to receive has received the information when game ends, and
////  // whether player who does not want to receive has not received it
////  @Test
////  public void testEndReceiveInfo1(){
////    List<Player.IPlayer> players = new ArrayList<Player.IPlayer>(refereereferee1.getPlayersInThisGame().values());
////    HashMap<String, Integer> winnerInfo = new HashMap<>();
////    winnerInfo.put("Alice",15);
////    assertEquals(players.get(1).getWinnerInfo(), winnerInfo);
////    assertEquals(players.get(0).getWinnerInfo(), new HashMap<>());
////  }
////
////
////  // check whether player who wants to receive has received the information when game ends, and
////  // whether player who does not want to receive has not received it
////  @Test
////  public void testEndReceiveInfo2(){
////    List<Player.IPlayer> players = new ArrayList<Player.IPlayer>(refereereferee2.getPlayersInThisGame().values());
////    HashMap<String, Integer> winnerInfo = new HashMap<>();
////    winnerInfo.put("Sam", 12);
////    winnerInfo.put("Tina", 12);
////    assertEquals(players.get(1).getWinnerInfo(), winnerInfo);
////    assertEquals(players.get(0).getWinnerInfo(), new HashMap<>());
////  }
//
//  // check whether player who wants to receive has received the information when game ends, and
//  // whether player who does not want to receive has not received it
//  @Test
//  public void testEndReceiveInfo3(){
//    List<IPlayer> players = new ArrayList<IPlayer>(refereereferee3.getPlayersInThisGame().values());
//    HashMap<String, Integer> winnerInfo = new HashMap<>();
//    winnerInfo.put("Julia",15);
//    winnerInfo.put("Mike",15);
//    assertEquals(players.get(0).getWinnerInfo(), winnerInfo);
//    assertEquals(players.get(1).getWinnerInfo(), winnerInfo);
//  }
//
//
//  // test for getGameResult method
//  @Test
//  public void testGetGameResult1(){
//    HashMap<IPlayer, Integer> result = new HashMap<>();
//    result.put(refereeiplayer1, 15);
//    result.put(refereeiplayer2, 12);
//    assertEquals(refereereferee1.getGameResult(), result);
//  }
//
//  @Test
//  public void testGetGameResult2(){
//    HashMap<IPlayer, Integer> result = new HashMap<>();
//    result.put(refereeiplayer3, 12);
//    result.put(refereeiplayer4, 12);
//    assertEquals(refereereferee2.getGameResult(), result);
//  }
//
//  @Test
//  public void testGetGameResult3(){
//    HashMap<IPlayer, Integer> result = new HashMap<>();
//    result.put(refereeiplayer5, 15);
//    result.put(refereeiplayer6, 15);
//    assertEquals(refereereferee3.getGameResult(), result);
//  }
//
//
//}