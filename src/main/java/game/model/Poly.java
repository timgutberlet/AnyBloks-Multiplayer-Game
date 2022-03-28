package game.model;

/**
 * @author tiotto
 * @date 23.03.2022
 */
public abstract class Poly implements Cloneable{
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

  public Poly(Color color){
    this.color = color;
    rotation = 0;
    isMirrored = false;
  }

  public Poly(Color color, int rotation, boolean isMirrored){
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

  public Color getColor(){
    return color;
  }

}
