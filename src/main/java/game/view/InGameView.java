package game.view;

import engine.Initializable;
import engine.controller.AbstractGameController;
import game.model.Board;
import game.model.GameMode;
import javafx.scene.Group;

/**
 * @author lbaudenb
 */
public class InGameView implements Initializable {

  private Group root2D;
  private AbstractGameController gameController;
  private BoardPane boardPane;

  @Override
  public void init(AbstractGameController gameController, Group root2D) {
    this.root2D = root2D;
    this.gameController = gameController;
    this.boardPane = new BoardPane(new Board(GameMode.CLASSIC));
    this.root2D.getChildren().add(this.boardPane);
  }

  public BoardPane getBoardPane() {
    return this.boardPane;
  }
}
