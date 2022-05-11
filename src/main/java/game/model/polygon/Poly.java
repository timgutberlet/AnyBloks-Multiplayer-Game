package game.model.polygon;

import game.model.Color;

/**
 * @author tiotto
 * @date 23.03.2022
 */
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
   * method, that gives back the javafx color of the field
   *
   * @return javafx color, that the field has
   * @author lbaudenb
   */
  public javafx.scene.paint.Color getJavaColor() {
    switch (this.color) {
      case RED:
        return javafx.scene.paint.Color.RED;
      case BLUE:
        return javafx.scene.paint.Color.BLUE;
      case GREEN:
        return javafx.scene.paint.Color.GREEN;
      case YELLOW:
        return javafx.scene.paint.Color.YELLOW;
      default:
        return javafx.scene.paint.Color.WHITE;
    }
  }


}
