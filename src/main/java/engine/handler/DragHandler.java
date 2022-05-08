package engine.handler;

import javafx.scene.Node;

/**
 * This class enables elements to be draggable
 * @author timgutberlet
 */
public class DragHandler {

  private double mouseAnchorX;
  private double mouseAnchorY;

  /**
   *  Method to make elements draggable. Takes X,Y Coordinate when clicked and calculates
   *  where the Element should be put
   * @param node JavaFx Element that should be made Draggable
   */
  public void makeDraggable(Node node){
    node.setOnMousePressed(mouseEvent -> {
      mouseAnchorX = mouseEvent.getX();
      mouseAnchorY = mouseEvent.getY();
    });

    node.setOnMouseDragged(mouseEvent -> {
      node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
      node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
    });



  }


}
