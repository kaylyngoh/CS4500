import org.junit.Test;

import Model.Hole;
import Model.Tile;

import static org.junit.Assert.assertEquals;

public class PieceTest {
  Tile tile1 = new Tile(5);
  Hole hole1 = new Hole();

  // test the method checkFishNum
  @Test(expected = IllegalArgumentException.class)
  public void checkNegFishNum() {
    new Tile(-1);
  }

  // test the method checkFishNum
  @Test(expected = IllegalArgumentException.class)
  public void checkMoreThan5FishNum() {
    new Tile(10);
  }

  // test the method getFishNum
  @Test
  public void checkGetFishNum() {
    assertEquals(5, tile1.getFishNum());
  }

  // test the method checkIfPosIsHole
  @Test(expected = IllegalArgumentException.class)
  public void testCheckIfPosIsHole(){
    hole1.checkIfPosIsHole();
  }

}