package game.model;

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
  public abstract game.model.Field clone();

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

}


