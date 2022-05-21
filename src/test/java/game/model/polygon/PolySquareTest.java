package game.model.polygon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.model.Color;
import game.model.field.FieldSquare;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * @author tiotto
 * @date 15.05.2022
 */
class PolySquareTest {

  PolySquare ps1;

  PolySquareTest() {
    FieldSquare f00 = new FieldSquare(0, 0);
    FieldSquare f01 = new FieldSquare(0, 1);
    FieldSquare f11 = new FieldSquare(1, 1);
    FieldSquare f12 = new FieldSquare(1, 2);
    FieldSquare f13 = new FieldSquare(1, 3);
    ArrayList<FieldSquare> shape = new ArrayList<>();
    shape.add(f00);
    shape.add(f01);
    shape.add(f11);
    shape.add(f12);
    shape.add(f13);
    ps1 = new PolySquare(shape, Color.BLUE);
  }

  @Test
  void testShapeEquals() {
    assertTrue(PolySquare.shapeEquals(ps1.getShape(), ps1.getShape()));
  }

  @Test
  void testEquals() {
    assertEquals(ps1, ps1); //shape matches, even with rotation or mirror
    assertTrue(ps1.equalsReal(ps1)); //shape matches perfectly
  }

  @Test
  void testClone() {
    assertTrue(ps1.equalsReal(ps1.clone()));
  }

  @Test
  void testRotation() {
    PolySquare ps2 = ps1.clone();
    for (int i = 0; i < 4; i++) {
      ps2.rotateLeft();
    }
    assertTrue(ps1.equalsReal(ps2)); //checks if after 4 rotations the same PolySquare comes out
    ps2.rotateRight();
    ps2.rotateLeft();
    assertTrue(ps1.equalsReal(
        ps2)); //checks if after one left and one right rotation the same PolySquare comes out
  }

  @Test
  void testMirror() {
    PolySquare ps2 = ps1.clone();
    ps2.mirror();
    ps2.mirror();
    assertTrue(ps1.equalsReal(ps2));
  }

  @Test
  void testContainsField() {
    assertTrue(ps1.containsField(1, 2));
    assertTrue(ps1.containsField(new int[]{1, 2}));
    assertFalse(ps1.containsField(2, 2));
    assertFalse(ps1.containsField(new int[]{2, 2}));
  }

  @Test
  void testShapeList() {
    assertEquals(PolySquare.shapeListClassic.size(), 21);
    assertEquals(PolySquare.shapeListDuo.size(), 21);
    assertEquals(PolySquare.shapeListJunior.size(), 24);
  }
}