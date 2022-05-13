package engine.handler;

import engine.component.Field;
import engine.controller.AbstractGameController;
import game.view.poly.SquarePolyPane;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * This class enables elements to be draggable
 *
 * @author tgutberl
 */
public class InputHandler {

  private final Set<Field> fieldClickedSaved;
  private final Set<Field> fieldPressedSaved;
  private final Set<Field> fieldHoveredSaved;
  private final Set<Field> fieldReleasedSaved;
  private final Set<Field> fieldClicked;
  private final Set<Field> fieldPressed;
  private final Set<Field> fieldHovered;
  private final Set<Field> fieldReleased;
  private double mouseAnchorX;
  private double mouseAnchorY;
  private AbstractGameController gameController;
  private boolean blockInput;


  public InputHandler(AbstractGameController gameController) {
    this.gameController = gameController;
    fieldClickedSaved = new HashSet<>();
    fieldPressedSaved = new HashSet<>();
    fieldHoveredSaved = new HashSet<>();
    fieldReleasedSaved = new HashSet<>();
    fieldClicked = new HashSet<>();
    fieldPressed = new HashSet<>();
    fieldHovered = new HashSet<>();
    fieldReleased = new HashSet<>();
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

  /**
   * Method to be called before a Frame
   */
  public void start() {
    blockInput = true;
  }

  /**
   * Method to be called after a frame
   */
  public void end() {
    //Clear BoardInputs
    fieldClicked.clear();
    fieldPressed.clear();
    fieldHovered.clear();
    fieldReleased.clear();
    //Assign Cached Values
    fieldClicked.addAll(fieldClickedSaved);
    fieldPressed.addAll(fieldPressedSaved);
    fieldHovered.addAll(fieldHoveredSaved);
    fieldReleased.addAll(fieldReleasedSaved);
    //Clear cached values
    fieldClickedSaved.clear();
    fieldPressedSaved.clear();
    fieldHoveredSaved.clear();
    fieldReleasedSaved.clear();

    blockInput=false;
  }


  private void clearKeys() {
    fieldClickedSaved.clear();
    fieldHoveredSaved.clear();
    fieldReleasedSaved.clear();
    fieldPressedSaved.clear();
  }

  /**
   * Add all needed Eventhandlers to the Abstract field Element, that represents a grid on the Board
   * on either Square or Trigon
   *
   * @param field Element on the Board
   */
  public void registerField(Field field) {
    field.setOnMouseClicked(event -> {
      if(blockInput){
        fieldClickedSaved.add(field);
      }else{
        fieldClicked.add(field);
      }
    });

    field.setOnMousePressed((event -> {
      if(blockInput){
        fieldPressedSaved.add(field);
      }else{
        fieldPressed.add(field);
      }
    }));

    field.setOnMouseEntered(event -> {
      if(blockInput){
        fieldHoveredSaved.add(field);
      }else{
        fieldHovered.add(field);
      }
    });

    field.setOnMouseExited(event -> {
      if(blockInput){
        if (fieldHoveredSaved.contains(field)) {
          fieldHoveredSaved.remove(field);
        }
      }else{
        fieldHovered.remove(field);
      }
    });
  }

  /**
   * The Following  Methods return, if a field Object has been register in the Mouse Event handler
   *
   * @param field Element on the Board
   * @return Boolean
   */
  public boolean isFieldClicked(Field field) {
    return fieldClicked.contains(field);
  }

  /**
   * The Following  Methods return, if a field Object has been register in the Mouse Event handler
   *
   * @param field Element on the Board
   * @return Boolean
   */
  public boolean isFieldHovered(Field field) {
    return fieldHovered.contains(field);
  }

  /**
   * The Following  Methods return, if a field Object has been register in the Mouse Event handler
   *
   * @param field Element on the Board
   * @return Boolean
   */
  public boolean isFieldPressed(Field field) {
    return fieldPressed.contains(field);
  }

  public void registerPoly(SquarePolyPane polyPane) {
    polyPane.setOnMousePressed((e -> {
      System.out.println(
          "Poly Pressed: " + GridPane.getRowIndex(polyPane) + " " + GridPane.getColumnIndex(
              polyPane));
    }));
  }


}
