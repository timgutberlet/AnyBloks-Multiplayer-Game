package game.model.field;

import static org.junit.jupiter.api.Assertions.*;

import game.model.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author tiotto
 * @date 15.05.2022
 */
class FieldSquareTest {
  FieldSquare fs1 = new FieldSquare(1,2);
  @Test
  void testClone() {
    assertEquals(fs1, fs1.clone());
  }

  @Test
  void testPosition(){
    assertArrayEquals(fs1.getPos(), new int[] {1,2});
  }

  @Test
  void testIsOccupied(){
    assertFalse(fs1.isOccupied());
    fs1.setColor(Color.BLUE);
    assertTrue(fs1.isOccupied());
  }
}