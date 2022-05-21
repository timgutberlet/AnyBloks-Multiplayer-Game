package game.model.field;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.model.Color;
import org.junit.jupiter.api.Test;

/**
 * @author tiotto
 * @date 15.05.2022
 */
class FieldTrigonTest {

  FieldTrigon ft1 = new FieldTrigon(1, 2, 0);

  @Test
  void testClone() {
    assertEquals(ft1, ft1.clone());
  }

  @Test
  void testIsOccupied() {
    assertFalse(ft1.isOccupied());
    ft1.setColor(Color.BLUE);
    assertTrue(ft1.isOccupied());
  }

  @Test
  void testPosition() {
    assertTrue(ft1.isPos(1, 2, 0));
    assertArrayEquals(ft1.getPos(), new int[]{1, 2, 0});
  }
}