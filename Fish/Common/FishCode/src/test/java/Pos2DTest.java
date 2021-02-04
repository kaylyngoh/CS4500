import org.junit.Test;

import Model.Pos2D;

import static org.junit.Assert.assertEquals;


public class Pos2DTest {

  Pos2D pos1 = new Pos2D(1,2);

  /*// invalid x position
  @Test(expected = IllegalArgumentException.class)
  public void checkPos1() {
    new Pos2D(-1, 3);
  }

  // invalid y position
  @Test(expected = IllegalArgumentException.class)
  public void checkPos2() {
    new Pos2D(0, -10);
  }*/

  @Test
  public void checkGetterMethods() {
    assertEquals(pos1.getX(), 1);
    assertEquals(pos1.getY(), 2);
  }
}
