package engine.handler;

import engine.component.Field;
import engine.controller.AbstractGameController;
import game.view.DragablePolyPane;
import game.view.poly.PolyPane;
import java.security.Key;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * This class enables elements to be draggable
 *
 * @author tgutberl
 */
public class InputHandler {

  private final Set<KeyCode> keysPressed;
  private final Set<KeyCode> keysPressedSaved;
  private final Set<KeyCode> keysReleased;
  private final Set<KeyCode> keysReleasedSaved;
  private final Set<Field> fieldClickedSaved;
  private final Set<Field> fieldPressedSaved;
  private final Set<Field> fieldHoveredSaved;
  private final Set<Field> fieldReleasedSaved;
  private final Set<PolyPane> polyClickedSaved;
  private final Set<Field> fieldClicked;
  private final Set<Field> fieldPressed;
  private final Set<Field> fieldHovered;
  private final Set<Field> fieldReleased;
  private final Set<PolyPane> polyClicked;
  private double mouseAnchorX;
  private double mouseAnchorY;
  private final AbstractGameController gameController;
  private boolean blockInput;
  private final Set<DragablePolyPane> fieldDragged;



  public InputHandler(AbstractGameController gameController) {
    this.gameController = gameController;
    fieldClickedSaved = new HashSet<>();
    fieldPressedSaved = new HashSet<>();
    fieldHoveredSaved = new HashSet<>();
    fieldReleasedSaved = new HashSet<>();
    fieldDragged = new HashSet<>();
    fieldClicked = new HashSet<>();
    fieldPressed = new HashSet<>();
    fieldHovered = new HashSet<>();
    fieldReleased = new HashSet<>();
    polyClickedSaved = new HashSet<>();
    polyClicked = new HashSet<>();
    keysPressed = new HashSet<>();
    keysPressedSaved = new HashSet<>();
    keysReleased = new HashSet<>();
    keysReleasedSaved = new HashSet<>();

    registerKeys(gameController.getStage().getScene());
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

  public void registerKeys(Scene scene){
    scene.setOnKeyPressed(event ->{
        if (blockInput) {
          keysPressedSaved.add(event.getCode());
        } else {
          keysPressed.add(event.getCode());
        }
    });
    scene.setOnKeyReleased(event -> {
      if(blockInput){
        keysPressedSaved.add(event.getCode());
      }else {
        keysPressed.add(event.getCode());
      }
    });
  }

  public boolean isKeyPressed(KeyCode keyCode){
    return this.keysPressed.contains(keyCode);
  }
  public boolean isKeyReleased(KeyCode keyCode){
    return this.keysReleased.contains(keyCode);
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
    //Clear Keys
    keysPressed.clear();
    keysReleased.clear();
    //Clear StackInputs
    polyClicked.clear();
    //Assign Cached Values
    fieldClicked.addAll(fieldClickedSaved);
    fieldPressed.addAll(fieldPressedSaved);
    fieldHovered.addAll(fieldHoveredSaved);
    fieldReleased.addAll(fieldReleasedSaved);
    polyClicked.addAll(polyClickedSaved);
    keysPressed.addAll(keysPressedSaved);
    keysReleased.addAll(keysReleasedSaved);
    //Clear cached values
    fieldClickedSaved.clear();
    fieldPressedSaved.clear();
    fieldHoveredSaved.clear();
    fieldReleasedSaved.clear();
    polyClickedSaved.clear();
    keysReleasedSaved.clear();
    keysPressedSaved.clear();
    blockInput = false;
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
      if (blockInput) {
        fieldClickedSaved.add(field);
      } else {
        fieldClicked.add(field);
      }
    });

    field.setOnMousePressed((event -> {
      if (blockInput) {
        fieldPressedSaved.add(field);
      } else {
        fieldPressed.add(field);
      }
    }));

    field.setOnMouseEntered(event -> {
      if (blockInput) {
        fieldHoveredSaved.add(field);
      } else {
        fieldHovered.add(field);
      }
    });

    field.setOnMouseExited(event -> {
      if (blockInput) {
        fieldHoveredSaved.remove(field);
      } else {
        fieldHovered.remove(field);
      }
    });
  }

  /**
   * Add event handler to object poly pane
   *
   * @param polyPane
   */
  public void registerPoly(PolyPane polyPane) {
    for (Field field : polyPane.getFields()) {
      field.setOnMousePressed(event -> {
        if (blockInput) {
          polyClickedSaved.add(polyPane);
        } else {
          polyClicked.add(polyPane);
        }
      });
    }
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

  /**
   * The following  Method returns, if a poly pane object has been register in the mouse event
   * handler
   *
   * @param polyPane0
   * @return
   */
  public boolean isPolyClicked(PolyPane polyPane0) {
    for (PolyPane polyPane1 : polyClicked) {
      if (polyPane1.getPoly().equals(polyPane0.getPoly())) {
        return true;
      }
    }
    return false;
  }


}
