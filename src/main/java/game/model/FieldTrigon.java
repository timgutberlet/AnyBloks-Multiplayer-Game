package game.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author tiotto
 * @date 21.03.2022
 */
public class FieldTrigon extends Field implements Cloneable {

  /**
   * contains the position of the square
   */
  public int[] pos;

  /**
   * current color of the square
   */
  public Color color;

  public FieldTrigon(int x, int y, int isRight) {
    super();
    this.pos = new int[]{x, y, isRight};
    this.color = Color.WHITE;
  }

  public FieldTrigon(int x, int y, int isRight, Color c) {
    super();
    this.pos = new int[]{x, y, isRight};
    this.color = c;
  }

  public boolean isPos(int x, int y, int isRight){
    return x == pos[0] && y == pos[1] && isRight == pos[2];
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public int[] getPos() {
    return pos;
  }

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

  public Boolean isOccupied() {
    return (!color.equals(Color.WHITE));
  }


  @Override
  public game.model.FieldTrigon clone() {
    return new game.model.FieldTrigon(this.pos[0], this.pos[1], this.pos[2], this.color);
  }

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FieldTrigon that = (FieldTrigon) o;
    return Arrays.equals(pos, that.pos);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(color);
    result = 31 * result + Arrays.hashCode(pos);
    return result;
  }
}
