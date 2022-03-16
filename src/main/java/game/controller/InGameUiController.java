package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import game.view.InGameView;
import javafx.scene.Group;

public class InGameUiController extends AbstractUiController {

  private InGameView inGameView;

  public InGameUiController(AbstractGameController gameController) {
    super(gameController);
  }

  @Override
  public void init(AbstractGameController gameController, Group root) {
    inGameView = new InGameView();
    inGameView.init(gameController, root);
  }

  @Override
  public void update(AbstractGameController gameController, double deltaTime) {

    /*for (Rectangle rectangle : inGameView.getBoardPane().getSquares()) {
      if (gameController.getInputHandler().isRectangleClicked(rectangle)) {
        System.out.println(
            "Rectangle " + GridPane.getRowIndex(rectangle) + " " + GridPane.getColumnIndex(
                rectangle) + " is clicked");
      }*/
  }

}

