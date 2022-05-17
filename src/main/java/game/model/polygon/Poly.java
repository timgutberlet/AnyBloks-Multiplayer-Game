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
   * empty constructor for jackson
   */
  public Poly() {

  }

  public Poly(Color color) {
    this.color = color;
    rotation = 0;
    isMirrored = false;
  }

  public Poly(Color color, int rotation, boolean isMirrored) {
    this.color = color;
    this.rotation = rotation;
    this.isMirrored = isMirrored;
  }

  public abstract void rotateLeft();

  public abstract void rotateRight();

  public abstract void mirror();

  //public abstract ArrayList<Field> getShape();

  public abstract Poly clone();

  public abstract boolean equals(Object o);

  public Color getColor() {
    return color;
  }

  public int getSize() {
    return size;
  }

  public abstract boolean equalsReal(Object o);

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

  public abstract int getHeight();

  public abstract int getWidth();

  public abstract String toCode();

}
