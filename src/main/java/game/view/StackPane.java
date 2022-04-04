package game.view;

import game.model.Player;
import game.model.Poly;
import java.util.List;
import javafx.scene.layout.GridPane;

/**
 * @author lbaudenb
 */
public abstract class StackPane extends GridPane {

  private final int MAX_NB_POLYS = 8;

  private final Player player;

  public StackPane(Player player) {
    this.player = player;
  }

  public abstract void setUpStack(List<Poly> polys);
}
