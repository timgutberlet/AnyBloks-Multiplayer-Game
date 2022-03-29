package game.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * This class is the abstract version of a single peace on the board
 *
 * @author tiotto
 * @date 21.03.2022
 */
public abstract class Field {

  /**
   * contains the position of the square
   */
  public int[] pos;

  /**
   * current color of the square
   */
  public Color color;

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public int[] getPos() {
    return pos;
  }

  /**
   * method, that gives back the javafx color of the field
   *
   * @return javafx color, that the field has
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

  /**
   * if the field is already occupied
   *
   * @return boolean, if the field is already occupied
   */
  public Boolean isOccupied() {
    return (!color.equals(Color.WHITE));
  }

  /**
   * abstract clone method, that needs to be implemented in the subclasses
   *
   * @return gives back a real copy of the field
   */
  @Override
  public abstract game.model.Field clone();

  /**
   * method, that converts the field into a string for a Terminaloutput
   *
   * @return String of the Field
   */
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
  }

  /**
   * method, that determine if to fields are the same, depending on their position
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
    return Arrays.equals(pos, that.pos);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(color);
    result = 31 * result + Arrays.hashCode(pos);
    return result;
  }

}


