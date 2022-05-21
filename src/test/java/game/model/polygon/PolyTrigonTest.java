package game.model.polygon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.model.Color;
import game.model.field.FieldTrigon;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * @author tiotto
 * @date 15.05.2022
 */
class PolyTrigonTest {

  PolyTrigon pt1;

  PolyTrigonTest() {
    FieldTrigon f001 = new FieldTrigon(0, 0, 1);
    FieldTrigon f100 = new FieldTrigon(1, 0, 0);
    FieldTrigon f101 = new FieldTrigon(1, 0, 1);
    FieldTrigon f110 = new FieldTrigon(1, 1, 0);
    FieldTrigon f200 = new FieldTrigon(2, 0, 0);
    FieldTrigon f201 = new FieldTrigon(2, 0, 1);
    ArrayList<FieldTrigon> shape = new ArrayList<>();
    shape.add(f001);
    shape.add(f100);
    shape.add(f101);
    shape.add(f200);
    shape.add(f201);
    shape.add(f110);
    pt1 = new PolyTrigon(shape, Color.BLUE);
  }

  @Test
  void testShapeEquals() {
    assertTrue(PolyTrigon.shapeEquals(pt1.getShape(), pt1.getShape()));
  }

  @Test
  void testEquals() {
    assertEquals(pt1, pt1); //shape matches, even with rotation or mirror
    assertTrue(pt1.equalsReal(pt1)); //shape matches perfectly
  }

  @Test
  void testClone() {
    assertTrue(pt1.equalsReal(pt1.clone()));
  }

  @Test
  void testRotation() {
    PolyTrigon ps2 = pt1.clone();
    for (int i = 0; i < 6; i++) {
      ps2.rotateLeft();
    }
    assertTrue(pt1.equalsReal(ps2)); //checks if after 6 rotations the same PolyTrigon comes out
    ps2.rotateRight();
    ps2.rotateLeft();
    assertTrue(pt1.equalsReal(
        ps2)); //checks if after one left and one right rotation the same PolyTrigon comes out
  }

  @Test
  void testMirror() {
    PolyTrigon ps2 = pt1.clone();
    ps2.mirror();
    ps2.mirror();
    assertTrue(pt1.equalsReal(ps2));
  }

  @Test
  void testContainsField() {
    assertTrue(pt1.containsField(2, 0, 1));
    assertTrue(pt1.containsField(new int[]{2, 0, 1}));
    assertFalse(pt1.containsField(2, 1, 0));
    assertFalse(pt1.containsField(new int[]{2, 1, 0}));
  }

  @Test
  void testShapeList() {
    assertEquals(PolyTrigon.shapeListTrigon.size(), 22);
  }
}