package game.controller;

import engine.controller.AbstractGameController;
import game.model.Game;
import game.model.GameSession;
import game.model.GameState;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.util.ArrayList;

/**
 * @author tgutberl
 */
public class TutorialUiController extends InGameUiController{

  /**
   * Arraylist containing the Instructions
   */
  private final ArrayList<String> hints;
  /**
   * Number of turn that got played
   */
  private int turn;

  public TutorialUiController(AbstractGameController gameController,
      GameSession gameSession) {
    super(gameController, gameSession.getGame(), gameSession);
    hints = new ArrayList<>();
  }
}
