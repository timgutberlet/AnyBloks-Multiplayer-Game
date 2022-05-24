package engine.handler;

import engine.component.Field;
import engine.controller.AbstractGameController;
import game.view.poly.PolyPane;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

/**
 * This class enables elements to be draggable and handles inputs.
 *
 * @author tgutberl
 */
public class InputHandler {

  /**
   * Sets for Pressed Keys and Pressed Keys while the Frame repainted.
   */
  private final Set<KeyCode> keysPressed;
  /**
   * Sets for Pressed Keys and Pressed Keys while the Frame repainted.
   */
  private final Set<KeyCode> keysPressedSaved;
  /**
   * Sets for Pressed Keys and Pressed Keys while the Frame repainted.
   */
  private final Set<KeyCode> keysReleased;
  /**
   * Sets for Pressed Keys and Pressed Keys while the Frame repainted.
   */
  private final Set<KeyCode> keysReleasedSaved;
  /**
   * Sets for Pressed Fields and Pressed Fields while the Frame repainted.
   */
  private final Set<Field> fieldClickedSaved;
  /**
   * Sets for Pressed Fields and Pressed Fields while the Frame repainted.
   */
  private final Set<Field> fieldPressedSaved;
  /**
   * Sets for Pressed Fields and Pressed Fields while the Frame repainted.
   */
  private final Set<Field> fieldHoveredSaved;
  /**
   * Sets for Pressed Fields and Pressed Fields while the Frame repainted.
   */
  private final Set<Field> fieldReleasedSaved;
  /**
   * Sets for Pressed Fields and Pressed Fields while the Frame repainted.
   */
  private final Set<PolyPane> polyClickedSaved;
  /**
   * Sets for Pressed Fields and Pressed Fields while the Frame repainted.
   */
  private final Set<Field> fieldClicked;
  /**
   * Sets for Pressed Fields and Pressed Fields while the Frame repainted.
   */
  private final Set<Field> fieldPressed;
  /**
   * Sets for Pressed Fields and Pressed Fields while the Frame repainted.
   */
  private final Set<Field> fieldHovered;
  /**
   * Sets for Pressed Fields and Pressed Fields while the Frame repainted.
   */
  private final Set<Field> fieldReleased;
  /**
   * Sets for Pressed Fields and Pressed Fields while the Frame repainted.
   */
  private final Set<PolyPane> polyClicked;
  /**
   * Mousepos for dragging.
   */
  private double mouseAnchorX;
  /**
   * Mousepos for dragging.
   */
  private double mouseAnchorY;
  /**
   * blockInput for input handling.
   */
  private boolean blockInput;

  /**
   * Consturcor for setting Hashsets and gamecontrollers.
   *
   * @param gameController Gamecontroller used in appl.
   */
  public InputHandler(AbstractGameController gameController) {
    fieldClickedSaved = new HashSet<>();
    fieldPressedSaved = new HashSet<>();
    fieldHoveredSaved = new HashSet<>();
    fieldReleasedSaved = new HashSet<>();
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
   * @author tgutberl
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
   * Method to regiser Keys.
   *
   * @param scene Scene that is given
   */
  public void registerKeys(Scene scene) {
    scene.setOnKeyPressed(event -> {
      if (blockInput) {
        keysPressedSaved.add(event.getCode());
      } else {
        keysPressed.add(event.getCode());
      }
    });
    scene.setOnKeyReleased(event -> {
      if (blockInput) {
        keysPressedSaved.add(event.getCode());
      } else {
        keysPressed.add(event.getCode());
      }
    });
  }

  /**
   * Method to check if key is pressed.
   *
   * @param keyCode Keycode element, determining which key of the keyboard was pushed
   * @return boolean
   */
  public boolean isKeyPressed(KeyCode keyCode) {
    return this.keysPressed.contains(keyCode);
  }

  /**
   * Method to check if key is released.
   *
   * @param keyCode Keycode element, determining which key of the keyboard was released
   * @return returns if keyecode was released or not
   */
  public boolean isKeyReleased(KeyCode keyCode) {
    return this.keysReleased.contains(keyCode);
  }


  /**
   * Method to be called before a Frame.
   */
  public void start() {
    blockInput = true;
  }

  /**
   * Method to be called after a frame.
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

  /**
   * Method to clear all keys.
   */
  private void clearKeys() {
    fieldClickedSaved.clear();
    fieldHoveredSaved.clear();
    fieldReleasedSaved.clear();
    fieldPressedSaved.clear();
  }

  /**
   * Add all needed Eventhandlers to the Abstract field Element, that represents a grid on the Board
   * on either Square or Trigon.
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
   * Add event handler to object poly pane.
   *
   * @param polyPane Polypane object that should be register for inputs
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
   * The Following  Methods return, if a field Object has been register in the Mouse Event handler.
   *
   * @param field Element on the Board
   * @return Boolean
   */
  public boolean isFieldClicked(Field field) {
    return fieldClicked.contains(field);
  }

  /**
   * Returns if a field of an Array has been clicked or not.
   *
   * @param fieldArray Field array with field elements to be checked
   * @return returns if field in array was clicked or not
   */
  public boolean isFieldArrayClicked(List<Field> fieldArray) {
    for (Field field : fieldArray) {
      if (fieldClicked.contains(field)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Method to check for an Field array if one of it was pressed.
   *
   * @param fieldArray Field array with field elements to be checked
   * @return returns if field in array was pressed or not
   */
  public boolean isFieldArrayPressed(List<Field> fieldArray) {
    for (Field field : fieldArray) {
      if (fieldPressed.contains(field)) {
        return true;
      }
    }
    return false;
  }

  /**
   * The Following  Methods return, if a field Object has been register in the Mouse Event handler.
   *
   * @param field Element on the Board
   * @return Boolean
   */
  public boolean isFieldHovered(Field field) {
    return fieldHovered.contains(field);
  }

  /**
   * The Following  Methods return, if a field Object has been register in the Mouse Event handler.
   *
   * @param field Element on the Board
   * @return Boolean
   */
  public boolean isFieldPressed(Field field) {
    return fieldPressed.contains(field);
  }

  /**
   * The following  Method returns, if a poly pane object has been register in the mouse event
   * handler.
   *
   * @param polyPane0 Polypane object that should be checked if clicked or not
   * @return Boolean if polypane was clicked or not
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
