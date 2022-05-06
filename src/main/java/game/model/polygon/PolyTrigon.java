package game.model.polygon;

import game.model.Color;
import game.model.field.FieldTrigon;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * this class represents a specific polygon of the game
 *
 * @author tiotto
 * @author tgutberl
 */

public class PolyTrigon extends Poly {

  public static ArrayList<ArrayList<FieldTrigon>> shapeListTrigon = new ArrayList<>();

  static {
    FieldTrigon f000 = new FieldTrigon(0, 0, 0);
    FieldTrigon f001 = new FieldTrigon(0, 0, 1);
    FieldTrigon f010 = new FieldTrigon(0, 1, 0);
    FieldTrigon f011 = new FieldTrigon(0, 1, 1);
    FieldTrigon f020 = new FieldTrigon(0, 2, 0);
    FieldTrigon f021 = new FieldTrigon(0, 2, 1);
    FieldTrigon f030 = new FieldTrigon(0, 3, 0);
    FieldTrigon f100 = new FieldTrigon(1, 0, 0);
    FieldTrigon f101 = new FieldTrigon(1, 0, 1);
    FieldTrigon f110 = new FieldTrigon(1, 1, 0);
    FieldTrigon f111 = new FieldTrigon(1, 1, 1);
    FieldTrigon f120 = new FieldTrigon(1, 2, 0);
    FieldTrigon f200 = new FieldTrigon(2, 0, 0);
    FieldTrigon f201 = new FieldTrigon(2, 0, 1);
    FieldTrigon f210 = new FieldTrigon(2, 1, 0);
    FieldTrigon f300 = new FieldTrigon(3, 0, 0);

    ArrayList<FieldTrigon>[] pieceListTrigon = new ArrayList[]{
        new ArrayList(Arrays.asList(f000.clone())),
        new ArrayList(Arrays.asList(f000.clone(), f001.clone())),
        new ArrayList(Arrays.asList(f000.clone(), f001.clone(), f100.clone())),
        new ArrayList(Arrays.asList(f000.clone(), f001.clone(), f100.clone(), f101.clone())),
        new ArrayList(Arrays.asList(f000.clone(), f001.clone(), f100.clone(), f010.clone())),
        new ArrayList(Arrays.asList(f001.clone(), f100.clone(), f101.clone(), f110.clone())),
        new ArrayList(
            Arrays.asList(f000.clone(), f001.clone(), f100.clone(), f101.clone(), f200.clone())),
        new ArrayList(
            Arrays.asList(f000.clone(), f001.clone(), f100.clone(), f101.clone(), f010.clone())),
        new ArrayList(
            Arrays.asList(f001.clone(), f100.clone(), f101.clone(), f010.clone(), f110.clone())),
        new ArrayList(
            Arrays.asList(f000.clone(), f001.clone(), f100.clone(), f101.clone(), f110.clone())),
        new ArrayList(
            Arrays.asList(f000.clone(), f001.clone(), f100.clone(), f101.clone(), f200.clone(),
                f201.clone())),
        new ArrayList(
            Arrays.asList(f000.clone(), f001.clone(), f100.clone(), f101.clone(), f200.clone(),
                f010.clone())),
        new ArrayList(
            Arrays.asList(f001.clone(), f100.clone(), f101.clone(), f010.clone(), f011.clone(),
                f110.clone())),
        new ArrayList(
            Arrays.asList(f001.clone(), f100.clone(), f101.clone(), f200.clone(), f201.clone(),
                f210.clone())),
        new ArrayList(
            Arrays.asList(f001.clone(), f100.clone(), f101.clone(), f200.clone(), f201.clone(),
                f110.clone())),
        new ArrayList(
            Arrays.asList(f100.clone(), f101.clone(), f200.clone(), f201.clone(), f110.clone(),
                f011.clone())),
        new ArrayList(
            Arrays.asList(f000.clone(), f001.clone(), f100.clone(), f101.clone(), f110.clone(),
                f011.clone())),
        new ArrayList(
            Arrays.asList(f001.clone(), f100.clone(), f101.clone(), f200.clone(), f110.clone(),
                f011.clone())),
        new ArrayList(
            Arrays.asList(f100.clone(), f101.clone(), f200.clone(), f110.clone(), f011.clone(),
                f111.clone())),
        new ArrayList(
            Arrays.asList(f010.clone(), f011.clone(), f110.clone(), f101.clone(), f200.clone(),
                f201.clone())),
        new ArrayList(
            Arrays.asList(f000.clone(), f001.clone(), f100.clone(), f101.clone(), f110.clone(),
                f111.clone())),
        new ArrayList(
            Arrays.asList(f010.clone(), f011.clone(), f110.clone(), f100.clone(), f101.clone(),
                f200.clone()))
    };

    for (ArrayList<FieldTrigon> piece : pieceListTrigon) {
      shapeListTrigon.add(piece);
    }
  }

  /**
   * represents the shape of the polygon as an array of boolean whether the position is filled with
   * the polygon or not
   */
  public ArrayList<FieldTrigon> shape;

  /**
   * Initializes the default values of a polygon
   *
   * @param shape shape of the polygon
   * @param color color of the polygon
   */
  public PolyTrigon(ArrayList<FieldTrigon> shape, Color color) {
    super(color);
    this.shape = shape;
    this.size = shape.size();
    for (FieldTrigon ft : shape) {
      ft.setColor(color);
    }
  }

  public PolyTrigon(ArrayList<FieldTrigon> shape, Color color, int rotation, boolean isMirrored) {
    super(color, rotation, isMirrored);
    this.shape = shape;
    this.size = shape.size();
  }

  public static boolean shapeEquals(ArrayList<FieldTrigon> s1, ArrayList<FieldTrigon> s2) {
    if (s1.size() != s2.size()) {
      return false;
    }
    for (FieldTrigon ft1 : s1) {
      boolean res = false;
      for (FieldTrigon ft2 : s2) {
        if (ft1.equals(ft2)) {
          res = true;
        }
      }
      if (!res) {
        return false;
      }
    }
    return true;
  }

  /**
   * rotates the polygon to the right
   */
  public void rotateLeft() {
    ArrayList<FieldTrigon> newShape = new ArrayList<>();

    for (FieldTrigon ft : this.shape) { //toDo Idee: Sortierung der Zeilen aendern, um immer unten links als Referenzstein zu haben
      if (ft.isPos(0, 0, 0)) {
        newShape.add(new FieldTrigon(3, 0, 1, this.color));
      } else if (ft.isPos(0, 0, 1)) {
        newShape.add(new FieldTrigon(3, 1, 0, this.color));
      } else if (ft.isPos(1, 0, 0)) {
        newShape.add(new FieldTrigon(3, 1, 1, this.color));
      } else if (ft.isPos(1, 0, 1)) {
        newShape.add(new FieldTrigon(3, 2, 0, this.color));
      } else if (ft.isPos(2, 0, 0)) {
        newShape.add(new FieldTrigon(3, 2, 1, this.color));
      } else if (ft.isPos(2, 0, 1)) {
        newShape.add(new FieldTrigon(3, 3, 0, this.color));
      } else if (ft.isPos(3, 0, 0)) {
        newShape.add(new FieldTrigon(3, 3, 1, this.color));
      } else if (ft.isPos(2, 1, 0)) {
        newShape.add(new FieldTrigon(2, 3, 1, this.color));
      } else if (ft.isPos(1, 1, 1)) {
        newShape.add(new FieldTrigon(2, 3, 0, this.color));
      } else if (ft.isPos(1, 2, 0)) {
        newShape.add(new FieldTrigon(1, 3, 1, this.color));
      } else if (ft.isPos(0, 2, 1)) {
        newShape.add(new FieldTrigon(1, 3, 0, this.color));
      } else if (ft.isPos(0, 3, 0)) {
        newShape.add(new FieldTrigon(0, 3, 1, this.color));
      } else if (ft.isPos(0, 2, 0)) {
        newShape.add(new FieldTrigon(1, 2, 1, this.color));
      } else if (ft.isPos(0, 1, 1)) {
        newShape.add(new FieldTrigon(2, 2, 0, this.color));
      } else if (ft.isPos(0, 1, 0)) {
        newShape.add(new FieldTrigon(2, 1, 1, this.color));
      } else if (ft.isPos(1, 1, 0)) {
        newShape.add(new FieldTrigon(2, 2, 1, this.color));

      } else if (ft.isPos(3, 0, 1)) {
        newShape.add(new FieldTrigon(3, 0, 0, this.color));
      } else if (ft.isPos(3, 1, 0)) {
        newShape.add(new FieldTrigon(2, 0, 1, this.color));
      } else if (ft.isPos(3, 1, 1)) {
        newShape.add(new FieldTrigon(2, 1, 0, this.color));
      } else if (ft.isPos(3, 2, 0)) {
        newShape.add(new FieldTrigon(1, 1, 1, this.color));
      } else if (ft.isPos(3, 2, 1)) {
        newShape.add(new FieldTrigon(1, 2, 0, this.color));
      } else if (ft.isPos(3, 3, 0)) {
        newShape.add(new FieldTrigon(0, 2, 1, this.color));
      } else if (ft.isPos(3, 3, 1)) {
        newShape.add(new FieldTrigon(0, 3, 0, this.color));
      } else if (ft.isPos(2, 3, 1)) {
        newShape.add(new FieldTrigon(0, 2, 0, this.color));
      } else if (ft.isPos(2, 3, 0)) {
        newShape.add(new FieldTrigon(0, 1, 1, this.color));
      } else if (ft.isPos(1, 3, 1)) {
        newShape.add(new FieldTrigon(0, 1, 0, this.color));
      } else if (ft.isPos(1, 3, 0)) {
        newShape.add(new FieldTrigon(0, 0, 1, this.color));
      } else if (ft.isPos(0, 3, 1)) {
        newShape.add(new FieldTrigon(0, 0, 0, this.color));
      } else if (ft.isPos(1, 2, 1)) {
        newShape.add(new FieldTrigon(1, 0, 0, this.color));
      } else if (ft.isPos(2, 2, 0)) {
        newShape.add(new FieldTrigon(1, 0, 1, this.color));
      } else if (ft.isPos(2, 1, 1)) {
        newShape.add(new FieldTrigon(2, 0, 0, this.color));
      } else if (ft.isPos(2, 2, 1)) {
        newShape.add(new FieldTrigon(1, 1, 0, this.color));
      }
    }
    this.shape = newShape;
    rotation = --rotation % 6;
  }

  /**
   * rotates the polygon to the left
   */
  public void rotateRight() {
    for (int i = 1; i < 6; i++) {
      this.rotateLeft();
    }
  }

  /**
   * mirrors the polygon
   */
  public void mirror() {
    ArrayList<FieldTrigon> newShape = new ArrayList<>();

    for (FieldTrigon ft : this.shape) {
      if (ft.isPos(0, 0, 0)) {
        newShape.add(new FieldTrigon(3, 0, 0, this.color));
      } else if (ft.isPos(0, 0, 1)) {
        newShape.add(new FieldTrigon(2, 0, 1, this.color));
      } else if (ft.isPos(1, 0, 0)) {
        newShape.add(new FieldTrigon(2, 0, 0, this.color));
      } else if (ft.isPos(1, 0, 1)) {
        newShape.add(new FieldTrigon(1, 0, 1, this.color));
      } else if (ft.isPos(2, 0, 0)) {
        newShape.add(new FieldTrigon(1, 0, 0, this.color));
      } else if (ft.isPos(2, 0, 1)) {
        newShape.add(new FieldTrigon(0, 0, 1, this.color));
      } else if (ft.isPos(3, 0, 0)) {
        newShape.add(new FieldTrigon(0, 0, 0, this.color));
      } else if (ft.isPos(2, 1, 0)) {
        newShape.add(new FieldTrigon(0, 1, 0, this.color));
      } else if (ft.isPos(1, 1, 1)) {
        newShape.add(new FieldTrigon(0, 1, 1, this.color));
      } else if (ft.isPos(1, 2, 0)) {
        newShape.add(new FieldTrigon(0, 2, 0, this.color));
      } else if (ft.isPos(0, 2, 1)) {
        newShape.add(new FieldTrigon(0, 2, 1, this.color));
      } else if (ft.isPos(0, 3, 0)) {
        newShape.add(new FieldTrigon(0, 3, 0, this.color));
      } else if (ft.isPos(0, 2, 0)) {
        newShape.add(new FieldTrigon(1, 2, 0, this.color));
      } else if (ft.isPos(0, 1, 1)) {
        newShape.add(new FieldTrigon(1, 1, 1, this.color));
      } else if (ft.isPos(0, 1, 0)) {
        newShape.add(new FieldTrigon(2, 1, 0, this.color));
      } else if (ft.isPos(1, 1, 0)) {
        newShape.add(new FieldTrigon(1, 1, 0, this.color));
      } else if (ft.isPos(3, 0, 1)) {
        newShape.add(new FieldTrigon(3, 0, 1, this.color));
      } else if (ft.isPos(3, 1, 0)) {
        newShape.add(new FieldTrigon(3, 1, 0, this.color));
      } else if (ft.isPos(3, 1, 1)) {
        newShape.add(new FieldTrigon(2, 1, 1, this.color));
      } else if (ft.isPos(3, 2, 0)) {
        newShape.add(new FieldTrigon(2, 2, 0, this.color));
      } else if (ft.isPos(3, 2, 1)) {
        newShape.add(new FieldTrigon(1, 2, 1, this.color));
      } else if (ft.isPos(3, 3, 0)) {
        newShape.add(new FieldTrigon(1, 3, 0, this.color));
      } else if (ft.isPos(3, 3, 1)) {
        newShape.add(new FieldTrigon(0, 3, 1, this.color));
      } else if (ft.isPos(2, 3, 1)) {
        newShape.add(new FieldTrigon(1, 3, 1, this.color));
      } else if (ft.isPos(2, 3, 0)) {
        newShape.add(new FieldTrigon(2, 3, 0, this.color));
      } else if (ft.isPos(1, 3, 1)) {
        newShape.add(new FieldTrigon(2, 3, 1, this.color));
      } else if (ft.isPos(1, 3, 0)) {
        newShape.add(new FieldTrigon(3, 3, 0, this.color));
      } else if (ft.isPos(0, 3, 1)) {
        newShape.add(new FieldTrigon(3, 3, 1, this.color));
      } else if (ft.isPos(1, 2, 1)) {
        newShape.add(new FieldTrigon(3, 2, 1, this.color));
      } else if (ft.isPos(2, 2, 0)) {
        newShape.add(new FieldTrigon(3, 2, 0, this.color));
      } else if (ft.isPos(2, 1, 1)) {
        newShape.add(new FieldTrigon(3, 1, 1, this.color));
      } else if (ft.isPos(2, 2, 1)) {
        newShape.add(new FieldTrigon(2, 2, 1, this.color));
      }
    }
    this.shape = newShape;
    isMirrored = !isMirrored;
  }

  public ArrayList<FieldTrigon> getShape() {
    return shape;
  }

  @Override
  public PolyTrigon clone() {
    ArrayList<FieldTrigon> newShape = new ArrayList<>();
    for (FieldTrigon ft : this.shape) {
      newShape.add(ft.clone());
    }
    return new PolyTrigon(newShape, color, rotation, isMirrored);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PolyTrigon poly = (PolyTrigon) o;
    PolyTrigon poly1 = poly.clone();
    boolean res = false;

    for (Boolean mirrored : new boolean[]{false, true}) {
      for (int i = 0; i < 6; i++) {
        if (i == 0 && mirrored) {
          poly1.mirror();
        }
        res = res || shapeEquals(poly1.getShape(), shape);
        poly1.rotateRight();
      }
    }
    return res && color == poly.color;
  }

  @Override
  public boolean equalsReal(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PolyTrigon poly = (PolyTrigon) o;
    boolean res = true;

    if (poly.shape.size() != this.shape.size()) {
      return false;
    }

    outer:
    for (FieldTrigon fs1 : poly.getShape()) {
      for (FieldTrigon fs2 : this.getShape()) {
        if (fs1.equals(fs2)) {
          continue outer;
        }
      }
      return false;
    }
    return res && color == poly.color;
  }
}
