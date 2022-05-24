package game.model.polygon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import game.model.Color;
import game.model.field.FieldSquare;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * this class represents a specific polygon of the game.
 *
 * @author tiotto
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PolySquare extends Poly {

  public static ArrayList<ArrayList<FieldSquare>> shapeListClassic = new ArrayList<>();
  public static ArrayList<ArrayList<FieldSquare>> shapeListDuo;
  public static ArrayList<ArrayList<FieldSquare>> shapeListJunior = new ArrayList<>();

  // Initializing all pieces of classic, duo and junior game.
  static {
    FieldSquare f00 = new FieldSquare(0, 0);
    FieldSquare f01 = new FieldSquare(0, 1);
    FieldSquare f02 = new FieldSquare(0, 2);
    FieldSquare f03 = new FieldSquare(0, 3);
    FieldSquare f04 = new FieldSquare(0, 4);
    FieldSquare f10 = new FieldSquare(1, 0);
    FieldSquare f11 = new FieldSquare(1, 1);
    FieldSquare f12 = new FieldSquare(1, 2);
    FieldSquare f13 = new FieldSquare(1, 3);
    FieldSquare f20 = new FieldSquare(2, 0);
    FieldSquare f21 = new FieldSquare(2, 1);
    FieldSquare f22 = new FieldSquare(2, 2);
    FieldSquare f31 = new FieldSquare(3, 1);

    ArrayList<FieldSquare>[] pieceListClassic = new ArrayList[]{
        new ArrayList(Arrays.asList(f00.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f02.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f11.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f10.clone(), f11.clone(), f21.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f10.clone(), f11.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f02.clone(), f11.clone())),
        new ArrayList(Arrays.asList(f10.clone(), f11.clone(), f12.clone(), f02.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f02.clone(), f03.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f10.clone(), f11.clone(),
            f21.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f10.clone(), f20.clone(),
            f21.clone())),
        new ArrayList(Arrays.asList(f10.clone(), f11.clone(), f12.clone(), f02.clone(),
            f22.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f10.clone(), f11.clone(), f21.clone(),
            f31.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f11.clone(), f21.clone(),
            f22.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f11.clone(), f12.clone(),
            f21.clone())),
        new ArrayList(Arrays.asList(f10.clone(), f11.clone(), f12.clone(), f13.clone(),
            f03.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f10.clone(), f11.clone(), f21.clone(),
            f22.clone())),
        new ArrayList(Arrays.asList(f10.clone(), f01.clone(), f11.clone(), f12.clone(),
            f21.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f02.clone(), f03.clone(),
            f04.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f02.clone(), f12.clone(),
            f22.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f02.clone(), f03.clone(),
            f11.clone()))
    };

    for (ArrayList<FieldSquare> piece : pieceListClassic) {
      shapeListClassic.add(piece);
    }

    shapeListDuo = shapeListClassic;

    ArrayList<FieldSquare>[] pieceListJunior = new ArrayList[]{
        new ArrayList(Arrays.asList(f00.clone())),
        new ArrayList(Arrays.asList(f00.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f10.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f10.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f10.clone(), f20.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f10.clone(), f20.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f02.clone(), f03.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f02.clone(), f03.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f11.clone(), f02.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f11.clone(), f02.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f02.clone(), f10.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f02.clone(), f10.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f10.clone(), f11.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f10.clone(), f11.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f11.clone(), f12.clone())),
        new ArrayList(Arrays.asList(f00.clone(), f01.clone(), f11.clone(), f12.clone())),
        new ArrayList(
            Arrays.asList(f00.clone(), f01.clone(), f02.clone(), f03.clone(), f10.clone())),
        new ArrayList(
            Arrays.asList(f00.clone(), f01.clone(), f02.clone(), f03.clone(), f10.clone())),
        new ArrayList(
            Arrays.asList(f00.clone(), f01.clone(), f11.clone(), f10.clone(), f02.clone())),
        new ArrayList(
            Arrays.asList(f00.clone(), f01.clone(), f11.clone(), f10.clone(), f02.clone())),
        new ArrayList(
            Arrays.asList(f00.clone(), f01.clone(), f11.clone(), f12.clone(), f13.clone())),
        new ArrayList(
            Arrays.asList(f00.clone(), f01.clone(), f11.clone(), f12.clone(), f13.clone()))
    };

    for (ArrayList<FieldSquare> piece : pieceListJunior) {
      shapeListJunior.add(piece);
    }
  }

  /**
   * represents the shape of the polygon as an array of boolean whether the position is filled with
   * the polygon or not.
   */
  public ArrayList<FieldSquare> shape;
  private String polyType = "Square";

  /**
   * empty constructor for jackson.
   */

  public PolySquare() {

  }

  /**
   * Initializes the default values of a polygon.
   *
   * @param shape shape of the polygon
   * @param color color of the polygon
   */
  public PolySquare(ArrayList<FieldSquare> shape, Color color) {
    super(color);
    this.shape = shape;
    this.size = shape.size();
    for (FieldSquare fs : shape) {
      fs.setColor(color);
    }
    this.polyType = "Square";
  }

  /**
   * initializes all values of a poly, so it can be used to clone a poly.
   *
   * @param shape      shape of the poly
   * @param color      color of the poly
   * @param rotation   rotation of the poly
   * @param isMirrored if the poly is mirrored
   */
  public PolySquare(ArrayList<FieldSquare> shape, Color color, int rotation, boolean isMirrored) {
    super(color, rotation, isMirrored);
    this.shape = shape;
    this.size = shape.size();
  }

  /**
   * compares two shapes and evaluates if they are equal independent of rotation and mirroring.
   *
   * @param s1 shape 1
   * @param s2 shape 2
   * @return if they are the same
   */
  public static boolean shapeEquals(ArrayList<FieldSquare> s1, ArrayList<FieldSquare> s2) {
    if (s1.size() != s2.size()) {
      return false;
    }
    for (FieldSquare fs1 : s1) {
      boolean res = false;
      for (FieldSquare fs2 : s2) {
        if (fs1.equals(fs2)) {
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
   * returns the height of the poly.
   *
   * @return height of the poly
   */
  @Override
  public int getHeight() {
    int res = 0;
    for (FieldSquare fs : shape) {
      if (fs.getPos()[1] > res) {
        res = fs.getPos()[1];
      }
    }
    return res + 1;
  }

  /**
   * returns the width of the poly.
   *
   * @return width of the poly
   */
  @Override
  public int getWidth() {
    int res = 0;
    for (FieldSquare fs : shape) {
      if (fs.getPos()[0] > res) {
        res = fs.getPos()[0];
      }
    }
    return res + 1;
  }

  /**
   * rotates the polygon to the left.
   */
  @Override
  public void rotateLeft() {
    ArrayList<FieldSquare> newShape = new ArrayList<>();
    for (FieldSquare fs : shape) {
      newShape.add(new FieldSquare(getHeight() - 1 - fs.getPos()[1], fs.getPos()[0], color));
    }
    shape = newShape;
    rotation = --rotation % 4;
  }

  /**
   * rotates the polygon to the right.
   */
  @Override
  public void rotateRight() {
    for (int i = 1; i < 4; i++) {
      this.rotateLeft();
    }
  }

  /**
   * mirrors the polygon.
   */
  @Override
  public void mirror() {
    ArrayList<FieldSquare> newShape = new ArrayList<>();
    for (FieldSquare fs : shape) {
      newShape.add(new FieldSquare(getWidth() - 1 - fs.getPos()[0], fs.getPos()[1], color));
    }
    shape = newShape;
    isMirrored = !isMirrored;
  }

  /**
   * gets the shape of the poly.
   *
   * @return shape of the poly
   */
  public ArrayList<FieldSquare> getShape() {
    return shape;
  }

  /**
   * gets deep clone of the poly.
   *
   * @return deep clone of the poly
   */
  @Override
  public PolySquare clone() {
    ArrayList<FieldSquare> newShape = new ArrayList<>();
    for (FieldSquare fs : this.shape) {
      newShape.add(fs.clone());
    }
    return new PolySquare(newShape, color, rotation, isMirrored);
  }

  /**
   * evaluates if o is the same poly but maybe in another rotation or mirroring.
   *
   * @param o other object
   * @return if they are the same
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PolySquare poly = (PolySquare) o;
    PolySquare poly1 = poly.clone();
    boolean res = false;

    for (Boolean mirrored : new boolean[]{false, true}) {
      for (int i = 0; i < 4; i++) {
        if (i == 0 && mirrored) {
          poly1.mirror();
        }
        res = res || shapeEquals(poly1.getShape(), shape);
        poly1.rotateRight();
      }
    }
    return res && color == poly.color;
  }

  /**
   * evaluates if o is exactly the same poly.
   *
   * @param o other object
   * @return if they are exactly the same
   */
  @Override
  public boolean equalsReal(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PolySquare poly = (PolySquare) o;
    boolean res = true;

    if (poly.shape.size() != this.shape.size()) {
      return false;
    }

    outer:
    for (FieldSquare fs1 : poly.getShape()) {
      for (FieldSquare fs2 : this.getShape()) {
        if (fs1.equals(fs2)) {
          continue outer;
        }
      }
      return false;
    }
    return res && color == poly.color;
  }

  /**
   * method that returns true if shape contains field with coordinates {i,j}.
   *
   * @param pos position
   * @author lbaudenb
   */
  @Override
  public boolean containsField(int[] pos) {
    for (FieldSquare fs : shape) {
      if (fs.pos[0] == pos[0] && fs.pos[1] == pos[1]) {
        return true;
      }
    }
    return false;
  }

  /**
   * checks if the poly contains the given field.
   *
   * @param x coordinate
   * @param y coordinate
   * @return boolean if the poly contains the field
   */
  public boolean containsField(int x, int y) {
    for (FieldSquare fs : shape) {
      if (fs.pos[0] == x && fs.pos[1] == y) {
        return true;
      }
    }
    return false;
  }

  /*
   * converts to board to a string.
   *
   * @return the string representation of the board
   *//*
  @Override
  public String toString() {
    StringBuffer res = new StringBuffer();
    String element = "";
    switch (getColor()) {
      case RED:
        element = "\uD83D\uDFE5";
        break;
      case BLUE:
        element = "\uD83D\uDFE6";
        break;
      case GREEN:
        element = "\uD83D\uDFE9";
        break;
      case YELLOW:
        element = "\uD83D\uDFE8";
        break;
    }
    for (int i = 0; i < getWidth(); i++) {
      for (int j = 0; j < getHeight(); j++) {
        if (containsField(i, j)) {
          res.append(element);
        } else {
          res.append("\uD83D\uDFEB");
        }
      }
      res.append("\n");
    }
    return res.toString();
  }*/

  /**
   * returns the type of the poly.
   *
   * @return type as String
   */
  public String getPolyType() {
    return this.polyType;
  }

  public void setPolyType(String polyType) {
    this.polyType = polyType;
  }

  /**
   * converts the board into code, which creates the board.
   *
   * @return string containing the creating code
   */
  public String toCode() {
    StringBuffer res = new StringBuffer("ArrayList<FieldSquare> shape = new ArrayList<>();\n");
    for (FieldSquare fs : shape) {
      res.append("shape.add(" + fs.toCode() + ");\n");
    }
    res.append("PolySquare p = new PolySquare(shape, Color.Blue);\n");
    return res.toString();
  }
}
