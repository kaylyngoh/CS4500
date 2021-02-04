import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import Model.Board;
import Model.Colour;
import Model.Penguin;
import Model.Player;
import Model.Pos2D;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

  Board fullBoard = new Board(2,2, 1);

  Penguin penguin1 = new Penguin(Colour.RED, new Pos2D(1, 1));
  Penguin penguin2 = new Penguin(Colour.RED, new Pos2D(0, 1));

  Player player1 = new Player(13, Colour.BROWN);
  Player player2 = new Player(13, Colour.RED, 5, new ArrayList<>(Arrays.asList(penguin1, penguin2)));

  // test getter methods
  @Test
  public void testGetterMethods() {
    assertEquals(player1.getColor(), Colour.BROWN);
    assertEquals(player1.getScore(), 0);
    assertEquals(player1.getAge(), 13);
    assertEquals(player2.getPenguins(), new ArrayList<>(Arrays.asList(penguin1, penguin2)));
  }

  // test addScore method
  @Test
  public void testAddScore() {
    assertEquals(player1.getScore(), 0);
    player1.addScore(4);
    assertEquals(player1.getScore(), 4);
  }

  // test addPenguin / placedAllPenguin method
  @Test
  public void testPlacedAllPenguin() {
    assertEquals(player1.getPenguins().size(), 0);
    player1.addPenguin(new Pos2D(1, 1));
    assertEquals(player1.getPenguins().size(), 1);
    player1.addPenguin(new Pos2D(2, 2));
    assertEquals(player1.getPenguins().size(), 2);
  }


  // test updatePenguin method
  @Test
  public void testUpdatePenguin() {
    player1.addPenguin(new Pos2D(1, 1));
    player1.updatePenguin(new Penguin(Colour.BROWN, new Pos2D(1,1)), new Pos2D(0, 1));
    assertEquals(player1.getPenguins().get(0).getPosition(), new Pos2D(0, 1));
  }

  // test updatePenguin method (no such penguin)
  @Test(expected = IllegalArgumentException.class)
  public void testUpdatePenguin1() {
    player1.updatePenguin(new Penguin(Colour.BROWN, new Pos2D(1,1)), new Pos2D(0, 1));
  }

  // test checkGameOver method
  @Test
  public void testCheckGameOver() {
    Board board = new Board(1, 1, 4);
    player1.addPenguin(new Pos2D(0, 0));
    assertTrue(player1.hasNoMoveAvailable(board, new ArrayList<>()));
  }

  // test hasNoMoveAvailable method
  @Test
  public void testHasNoMoveAvailable() {
    assertFalse(player2.hasNoMoveAvailable(fullBoard, new ArrayList<>(Arrays.asList(new Pos2D(1,1), new Pos2D(0,1)))));
    fullBoard.removeTile(new Pos2D(0,0));
    fullBoard.removeTile(new Pos2D(1,0));
    assertTrue(player2.hasNoMoveAvailable(fullBoard, new ArrayList<>(Arrays.asList(new Pos2D(1,1), new Pos2D(0,1)))));
  }
}
