package game.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * this class represents a specific polygon of the game
 *
 * @author tiotto
 * @author tgutberl
 */

public class PolySquare extends Poly {

  public static ArrayList<ArrayList<FieldSquare>> shapeList = new ArrayList<>();

  /**
   * Initializing all pieces of the basic Game
   *
   * @author tgutberl
   * @author tiotto
   */
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

    ArrayList<FieldSquare>[] pieceList = new ArrayList[]{
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

    for (ArrayList<FieldSquare> piece : pieceList) {
      shapeList.add(piece);
    }
    /*
    boolean[][][] pieceList = {new boolean[][]{{true}}, new boolean[][]{{true, true}},
        new boolean[][]{{false, true}, {true, true}}, new boolean[][]{{true, true, true}},
        new boolean[][]{{true, true, true, true}},
        new boolean[][]{{false, false, true}, {true, true, true}},
        new boolean[][]{{true, true, false}, {false, true, true}},
        new boolean[][]{{true, true}, {true, true}},
        new boolean[][]{{true, true, true}, {false, true, false}},
        new boolean[][]{{false, true, true}, {true, true, false}, {false, true, false}},
        new boolean[][]{{true}, {true}, {true}, {true}, {true}},
        new boolean[][]{{true, false}, {true, false}, {true, false}, {true, true}},
        new boolean[][]{{false, false}, {true, true}, {true, false}, {true, false}},
        new boolean[][]{{true, true}, {true, true}, {true, false}},
        new boolean[][]{{true, true, true}, {false, true, false}, {false, true, false}},
        new boolean[][]{{true, false, true}, {true, true, true}},
        new boolean[][]{{false, false, true}, {false, false, true}, {true, true, true}},
        new boolean[][]{{false, false, true}, {false, true, true}, {true, true, false}},
        new boolean[][]{{false, true, false}, {true, true, true}, {false, true, false}},
        new boolean[][]{{false, true}, {true, true}, {false, true}, {false, true}},
        new boolean[][]{{true, true, false}, {false, true, false}, {false, true, true}}};
    for (boolean[][] piece : pieceList) {
      shapeList.add(piece);
    }
     */
  }

  /**
   * represents the shape of the polygon as an array of boolean whether the position is filled with
   * the polygon or not
   */
  public ArrayList<FieldSquare> shape;

  /**
   * Initializes the default values of a polygon
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
  }

  public PolySquare(ArrayList<FieldSquare> shape, Color color, int rotation, boolean isMirrored) {
    super(color, rotation, isMirrored);
    this.shape = shape;
    this.size = shape.size();
  }

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

  private int getHeight() {
    int res = 0;
    for (FieldSquare fs : shape) {
      if (fs.getPos()[1] > res) {
        res = fs.getPos()[1];
      }
    }
    return res;
  }

  private int getWidth() {
    int res = 0;
    for (FieldSquare fs : shape) {
      if (fs.getPos()[0] > res) {
        res = fs.getPos()[0];
      }
    }
    return res;
  }

  /**
   * rotates the polygon to the left
   */
  public void rotateLeft() {
    ArrayList<FieldSquare> newShape = new ArrayList<>();
    for (FieldSquare fs : shape) {
      newShape.add(new FieldSquare(getHeight() - 1 - fs.getPos()[1], fs.getPos()[0], color));
    }
    shape = newShape;
    rotation = --rotation % 4;
  }

  /**
   * rotates the polygon to the right
   */
  public void rotateRight() {
    for (int i = 1; i < 4; i++) {
      this.rotateLeft();
    }
  }

  /**
   * mirrors the polygon
   */
  public void mirror() {
    ArrayList<FieldSquare> newShape = new ArrayList<>();
    for (FieldSquare fs : shape) {
      newShape.add(new FieldSquare(getWidth() - 1 - fs.getPos()[0], fs.getPos()[1], color));
    }
    shape = newShape;
    isMirrored = !isMirrored;
  }

  public ArrayList<FieldSquare> getShape() {
    return shape;
  }

  /* public String toString() {
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
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        if (shape[i][j]) {
          res.append(element);
        } else {
          res.append("\uD83D\uDFEB");
        }
      }
      res.append("\n");
    }
    return res.toString();
  } */

  @Override
  public PolySquare clone() {
    ArrayList<FieldSquare> newShape = new ArrayList<>();
    for (FieldSquare fs : this.shape) {
      newShape.add(fs.clone());
    }
    return new PolySquare(newShape, color, rotation, isMirrored);
  }

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
}
