package game.controller;

import engine.controller.AbstractGameController;
import game.model.Game;
import game.model.GameSession;
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
      Game game, GameSession gameSession) {
    super(gameController, game, gameSession);
    hints = new ArrayList<>();
    Player player = new Player("You", PlayerType.HOST_PLAYER);
    Player oponnentAiPlayer = new Player("Opponent(Ai)", PlayerType.AI_EASY);
    gameSession.addPlayer(player);
    gameSession.addPlayer(oponnentAiPlayer);
    gameSession.startGame(new GameMode("Tutorial", 2));
  }
}
