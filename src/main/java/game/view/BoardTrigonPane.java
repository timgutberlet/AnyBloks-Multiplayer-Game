package game.view;

import game.model.board.Board;
import game.model.board.BoardTrigon;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * @author lbaudenb
 */
public class BoardTrigonPane extends Pane implements BoardPane {

  private final List<Polygon> triangles;
  private BoardTrigon boardTrigon;

  private double size = 40;
  private double xOfSet = Math.sin(Math.toRadians(30)) * size;
  private double yOfSet = Math.sin(Math.toRadians(60)) * size;

  public BoardTrigonPane(BoardTrigon boardTrigon) {
    this.boardTrigon = boardTrigon;
    triangles = new ArrayList<>();
    setBoard();
  }

  private void setTriangleRight(int i, int j, Color color) {
    Polygon triangleRight = new Polygon();
    triangleRight.getPoints().addAll(new Double[]{
        size + (j + i) * size - i * xOfSet, yOfSet + i * yOfSet, //right vertex
        xOfSet + (j + i) * size - i * xOfSet, 0.0 + i * yOfSet, // top vertex
        0.0 + (j + i) * size - i * xOfSet, yOfSet + i * yOfSet}); // left vertex
    triangleRight.setFill(color);
    triangleRight.setStroke(Color.BLACK);
    this.getChildren().add(triangleRight);
  }

  private void setTriangleLeft(int i, int j, Color color) {
    Polygon triangleLeft = new Polygon();
    triangleLeft.getPoints().addAll(new Double[]{
        size + (j - 1 + i) * size - i * xOfSet, yOfSet + i * yOfSet,  // top vertex
        size + xOfSet + (j - 1 + i) * size - i * xOfSet, 0.0 + i * yOfSet, // right vertex
        xOfSet + (j - 1 + i) * size - i * xOfSet, 0.0 + i * yOfSet});  // left vertex
    triangleLeft.setFill(color);
    triangleLeft.setStroke(Color.BLACK);
    this.getChildren().add(triangleLeft);
  }

  private void setTriangle(int i, int j) {
    Color color;
    if (i + j == 8) {
      color = boardTrigon.getJavaColor(i, j, 1);
      setTriangleRight(i, j, color);
    }
    if (i + j == 26) {
      color = boardTrigon.getJavaColor(i, j, 0);
      setTriangleLeft(i, j, color);
    }
    if (i + j > 8 && i + j < 26) {
      color = boardTrigon.getJavaColor(i, j, 1);
      setTriangleRight(i, j, color);
      color = boardTrigon.getJavaColor(i, j, 0);
      setTriangleLeft(i, j, color);
    }
  }

  private void setBoard() {
    for (int i = 0; i < 18; i++) {
      for (int j = 0; j < 18; j++) {
        setTriangle(i, j);
      }
    }
  }

  @Override
  public void repaint(Board board) {
    boardTrigon = (BoardTrigon) board;
    setBoard();
  }
}
