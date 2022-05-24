package game.model.field;

import game.model.Color;
import java.util.Arrays;
import java.util.Objects;

/**
 * This class is the abstract version of a single peace on the board.
 *
 * @author tiotto
 */
public abstract class Field {

  /**
   * contains the position of the square.
   */
  public int[] pos;

  /**
   * current color of the square.
   */
  public Color color;

  /**
   * get current color of the square.
   */
  public Color getColor() {
    return color;
  }

  /**
   * set current color of the square.
   */
  public void setColor(Color color) {
    this.color = color;
  }

  /**
   * get the position of the square.
   */
  public int[] getPos() {
    return pos;
  }

  /**
   * if the field is already occupied.
   *
   * @return boolean, if the field is already occupied
   */
  public Boolean isOccupied() {
    return (!color.equals(Color.WHITE));
  }

  /**
   * abstract clone method, that needs to be implemented in the subclasses.
   *
   * @return gives back a real copy of the field
   */
  @Override
  public abstract Field clone();

  /*
   * method, that converts the field into a string for a Terminal output.
   *
   * @return String of the Field
  @Override
  public String toString() {
    switch (getColor()) {
      case WHITE:
        return "\uD83D\uDFEB";
      case RED:
        return "\uD83D\uDFE5";
      case BLUE:
        return "\uD83D\uDFE6";
      case GREEN:
        return "\uD83D\uDFE9";
      case YELLOW:
        return "\uD83D\uDFE8";
      default:
        return "Error for Square color";
    }
  }*/

  /**
   * method, that determine if to fields are the same, depending on their position.
   *
   * @param o field, that will be determined, if it is equal
   * @return boolean, if the two fields have the same position
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Field that = (Field) o;
    boolean res = true;
    for (int i = 0; i < pos.length; i++) {
      if (!(pos[i] == that.pos[i])) {
        return false;
      }
    }
    return true;
  }

  /**
   * returns the hash code of the field.
   *
   * @return the hash code of the field
   */
  @Override
  public int hashCode() {
    int result = Objects.hash(color);
    result = 31 * result + Arrays.hashCode(pos);
    return result;
  }

}


