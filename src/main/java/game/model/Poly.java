package game.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * this class represents a specific polygon of the game
 *
 * @author tiotto
 * @author tgutberl
 */

public class Poly {

  /**
   * represents the shape of the polygon as an array of boolean whether the position is filled with
   * the polygon or not
   */
  public boolean[][] shape;

  /**
   * number of single squares contained
   */
  public int size;

  /**
   * height of the polygon (and the representing shape)
   */
  public int height;

  /**
   * width of the polygon (and the representing shape)
   */
  public int width;

  /**
   * color of the polygon
   */
  public Color color;

  /**
   * rotation of the polygon compared to the initial position
   */
  public int rotation; // rotation * 90 degrees

  /**
   * states if the polygon was mirrored compared to the initial position
   */
  public boolean isMirrored;

  public static ArrayList<boolean[][]> shapeList = new ArrayList<>();

  /**
   * Initializing all pieces of the basic Game
   *
   * @author tgutberl
   * @author tiotto
   */
  static {
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
    ;
  }

  /**
   * Initializes the default values of a polygon
   *
   * @param shape shape of the polygon
   * @param color color of the polygon
   */
  public Poly(boolean[][] shape, Color color) {
    this.shape = shape;
    this.height = shape[0].length;
    this.width = shape.length;
    this.color = color;
    rotation = 0;
    isMirrored = false;
    this.size = 0;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        if (shape[i][j]) {
          size++;
        }
      }
    }
  }

  public Poly(boolean[][] shape, Color color, int rotation, boolean isMirrored) {
    this.shape = shape;
    this.height = shape[0].length;
    this.width = shape.length;
    this.color = color;
    this.rotation = rotation;
    this.isMirrored = isMirrored;
    this.size = 0;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        if (shape[i][j]) {
          size++;
        }
      }
    }
  }

  /**
   * rotates the polygon to the right
   */
  public void rotateRight() {
    boolean[][] res = new boolean[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        res[i][j] = shape[width - 1 - j][i];
      }
    }
    shape = res;
    this.height = shape[0].length;
    this.width = shape.length;
    rotation = ++rotation % 4;
  }

  /**
   * rotates the polygon to the left
   */
  public void rotateLeft() {
    boolean[][] res = new boolean[height][width];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        res[height - 1 - j][i] = shape[i][j];
      }
    }
    shape = res;
    this.height = shape[0].length;
    this.width = shape.length;
    rotation = --rotation % 4;
  }

  /**
   * mirrors the polygon
   */
  public void mirror() {
    boolean help;
    for (int i = 0; i < width / 2; i++) {
      for (int j = 0; j < height; j++) {
        help = shape[i][j];
        shape[i][j] = shape[width - 1 - i][j];
        shape[width - 1 - i][j] = help;
      }
    }
    isMirrored = !isMirrored;
  }

  public boolean[][] getShape() {
    return shape;
  }

  public Color getColor() {
    return color;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }


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
  }

  @Override
  public Poly clone() {
    boolean[][] newShape = new boolean[this.width][this.height];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        newShape[i][j] = shape[i][j];
      }
    }
    return new Poly(newShape, color, rotation, isMirrored);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Poly poly = (Poly) o;
    Poly poly1 = poly.clone();
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

  @Override
  public int hashCode() {
    int result = Objects.hash(size, height, width, color, rotation, isMirrored);
    result = 31 * result + Arrays.hashCode(shape);
    return result;
  }

  public static boolean shapeEquals(boolean[][] s1, boolean[][] s2) {
    boolean res = true;
    if (s1.length != s2.length || s1[0].length != s2[0].length) {
      return false;
    }
    for (int i = 0; i < s1.length; i++) {
      for (int j = 0; j < s1[0].length; j++) {
        res = res && (s1[i][j] == s2[i][j]);
      }
    }
    return res;
  }
}
