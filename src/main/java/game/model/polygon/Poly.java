package game.model.polygon;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import game.model.Color;

/**
 * @author tiotto
 * @date 23.03.2022
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @Type(value = PolySquare.class, name = "PolySquare"),
    @Type(value = PolyTrigon.class, name = "PolyTrigon"),
})
public abstract class Poly implements Cloneable {


  /**
   * number of single squares contained
   */
  public int size;
  /**
   * color of the polygon
   */
  public Color color;
  /**
   * rotation of the polygon compared to the initial position
   */
  public int rotation; // rotation * 60 degrees
  /**
   * states if the polygon was mirrored compared to the initial position
   */
  public boolean isMirrored;

  /**
   * tells whether trigon or square.
   */
  public String polyType = "";

  /**
   * empty constructor for jackson
   */
  public Poly() {

  }

  /**
   * Initializes the default values of a polygon.
   *
   * @param color color of the polygon
   */
  public Poly(Color color) {
    this.color = color;
    rotation = 0;
    isMirrored = false;
  }

  /**
   * initializes all values of a poly, so it can be used to clone a poly.
   *
   * @param color      color of the poly
   * @param rotation   rotation of the poly
   * @param isMirrored if the poly is mirrored
   */
  public Poly(Color color, int rotation, boolean isMirrored) {
    this.color = color;
    this.rotation = rotation;
    this.isMirrored = isMirrored;
  }

  /**
   * rotates the polygon to the left.
   */
  public abstract void rotateLeft();

  /**
   * rotates the polygon to the right.
   */
  public abstract void rotateRight();

  /**
   * mirrors the polygon.
   */
  public abstract void mirror();

  /**
   * gets deep clone of the poly.
   *
   * @return deep clone of the poly
   */
  public abstract Poly clone();

  /**
   * evaluates if o is the same poly but maybe in another rotation or mirroring.
   *
   * @param o other object
   * @return if they are the same
   */
  public abstract boolean equals(Object o);

  public Color getColor() {
    return color;
  }

  public int getSize() {
    return size;
  }

  /**
   * evaluates if o is exactly the same poly.
   *
   * @param o other object
   * @return if they are exactly the same
   */
  public abstract boolean equalsReal(Object o);

  /**
   * method that returns true if shape contains field with coordinates {i,j}
   *
   * @param pos
   * @author lbaudenb
   */
  public abstract boolean containsField(int[] pos);

  /**
   * checks if the equals test works for every rotation and mirror
   *
   * @return result of the test, true if everything works, false if not
   */
  public boolean polyTest() {
    Poly test = this.clone();
    for (boolean mirror : new boolean[]{false, true}) {
      do {
        test.rotateRight();
        if (!this.equals(test)) {
          return false;
        }
      } while (test.rotation != 0);
    }
    return true;
  }

  /**
   * returns the height of the poly.
   *
   * @return height of the poly
   */
  public abstract int getHeight();

  /**
   * returns the width of the poly.
   *
   * @return width of the poly
   */
  public abstract int getWidth();

  /**
   * returns the type of the poly.
   *
   * @return
   */
  public abstract String getPolyType();

  /**
   * converts the board into code, which creates the board.
   *
   * @return string containing the creating code
   */
  public abstract String toCode();

}
