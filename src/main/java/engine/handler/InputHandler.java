package engine.handler;

import engine.controller.AbstractGameController;
import game.view.poly.SquarePolyPane;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 * This class enables elements to be draggable
 *
 * @author tgutberl
 */
public class InputHandler {

  private double mouseAnchorX;
  private double mouseAnchorY;

  private final Set<Rectangle> rectanglesClicked;
  private final Set<Rectangle> boardSquarePressed;
  private final Set<Rectangle> boardSquareHovered;

  private AbstractGameController gameController;


  public InputHandler(AbstractGameController gameController){
    this.gameController = gameController;
    rectanglesClicked = new HashSet<>();
    boardSquarePressed = new HashSet<>();
    boardSquareHovered = new HashSet<>();
  }

  /**
   * Method to make elements draggable. Takes X,Y Coordinate when clicked and calculates where the
   * Element should be put
   *
   * @param node JavaFx Element that should be made Draggable
   */
  public void makeDraggable(Node node) {
    node.setOnMousePressed(mouseEvent -> {
      mouseAnchorX = mouseEvent.getSceneX() - node.getTranslateX();
      mouseAnchorY = mouseEvent.getSceneY() - node.getTranslateY();
    });

    node.setOnMouseDragged(mouseEvent -> {
      node.setTranslateX(mouseEvent.getSceneX() - mouseAnchorX);
      node.setTranslateY(mouseEvent.getSceneY() - mouseAnchorY);
    });
  }

  private void clearKeys() {
    rectanglesClicked.clear();
    boardSquarePressed.clear();
    boardSquareHovered.clear();
  }


  public void registerBoardPressed(Rectangle rectangle) {
    rectangle.setOnMousePressed((e -> {
      rectanglesClicked.add(rectangle);
      System.out.println("BoardPressed " + GridPane.getRowIndex(rectangle) + " " + GridPane.getColumnIndex(rectangle));
    }));
  }
  public void registerBoardHovered(Rectangle rectangle){
      rectangle.setOnMouseEntered(e -> {
      boardSquareHovered.add(rectangle);
      System.out.println("Board Hovered " + GridPane.getRowIndex(rectangle) + " "+ " ");
    });
  }
  public void registerBoardLeft(Rectangle rectangle){
    rectangle.setOnMouseExited(e -> {
      if(boardSquareHovered.contains(rectangle)){
        boardSquareHovered.remove(rectangle);
      }
    });
  }
  public void registerPolyPressed(SquarePolyPane polyPane) {
    polyPane.setOnMousePressed((e -> {
      System.out.println("Poly Pressed: " + GridPane.getRowIndex(polyPane) + " "+ GridPane.getColumnIndex(polyPane));
    }));
  }

  public boolean isRectangleClicked(Rectangle rectangle) {
    return rectanglesClicked.contains(rectangle);
  }


}
