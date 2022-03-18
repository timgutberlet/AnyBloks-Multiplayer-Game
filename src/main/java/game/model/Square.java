package game.model;

/**
 * This class represents one square of a game board.
 */

public class Square implements Cloneable {

  /**
   * column of the square on the board
   */
  public int col;

  /**
   * row of the square on the board
   */
  public int row;

  /**
   * current color of the square
   */
  public Color color;

  /**
   * initializing the default values
   *
   * @param col column of the square on the board
   * @param row row of the square on the board
   * @author tiotto
   */
  public Square(int col, int row) {
    this.col = col;
    this.row = row;
    this.color = Color.WHITE;
  }

  public Square(int col, int row, Color c) {
    this.col = col;
    this.row = row;
    this.color = c;
  }

  public Color getColor() {
    return color;
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

  public void setColor(Color color) {
    this.color = color;
  }

  @Override
  public Square clone() {
    return new Square(this.col, this.row, this.color);
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

}
