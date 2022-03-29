package game.view;

import engine.Initializable;
import engine.controller.AbstractGameController;
import game.model.BoardSquare;
import game.model.gamemodes.GMClassic;
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
    this.boardPane = new BoardPane(new BoardSquare(new GMClassic()));
    this.root2D.getChildren().add(this.boardPane);
  }

  public BoardPane getBoardPane() {
    return this.boardPane;
  }
}
