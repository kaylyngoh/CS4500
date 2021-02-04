import org.junit.Test;

import java.awt.*;

import Model.Colour;
import Model.Penguin;
import Model.Pos2D;

import static org.junit.Assert.assertEquals;


public class PenguinTest {

  Penguin penguinRed = new Penguin(Colour.RED, new Pos2D(2,3));
  Penguin penguinWhite = new Penguin(Colour.WHITE, new Pos2D(7,2));
  Penguin penguinBrown = new Penguin(Colour.BROWN, new Pos2D(6,4));
  Penguin penguinBlack = new Penguin(Colour.BLACK, new Pos2D(9,5));



  //test for determineColor method
  @Test
  public void testColorOfPenguin() {
    assertEquals(penguinRed.determineColor(), Color.RED);
    assertEquals(penguinWhite.determineColor(), Color.WHITE);
    assertEquals(penguinBlack.determineColor(), Color.BLACK);
    assertEquals(penguinBrown.determineColor(), new Color(130, 79, 35));

  }

  // test getter methods
  @Test
  public void testGetterMethods() {
    assertEquals(penguinRed.getColor(), Colour.RED);
    assertEquals(penguinBlack.getPosition(), new Pos2D(9, 5));
  }

  // test updatePos method
  @Test
  public void testUpdatePos() {
    penguinBlack.updatePos(new Pos2D(5,5));
    assertEquals(penguinBlack.getPosition(), new Pos2D(5, 5));
  }

}

