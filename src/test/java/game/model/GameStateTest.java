package game.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.model.gamemodes.GMClassic;
import game.model.player.Ai;
import game.model.player.Player;
import game.model.player.PlayerType;
import org.junit.jupiter.api.Test;

/**
 * @author tiotto
 * @date 15.05.2022
 */
class GameStateTest {

  Game game;

  GameStateTest() {
    GameSession gameSession = new GameSession();
    gameSession.addPlayer(new Player("BOT1", PlayerType.AI_EASY));
    gameSession.addPlayer(new Player("BOT2", PlayerType.AI_RANDOM));
    gameSession.addPlayer(new Player("BOT3", PlayerType.AI_MIDDLE));
    gameSession.addPlayer(new Player("BOT4", PlayerType.AI_EASY));

    game = gameSession.startGame(new GMClassic());
  }

  @Test
  void testCheckEnd() {
    game.startGame();
    assertFalse(game.getGameState().checkEnd());

    int i = 0;
    while (game.getGameState().isStateRunning() && i < 40) {
      game.getGameState()
          .playTurn(Ai.calculateNextMove(game.getGameState(), game.getCurrentPlayer()));
      i++;
    }
    assertFalse(game.getGameState().checkEnd());
    while (game.getGameState().isStateRunning()) {
      game.getGameState()
          .playTurn(Ai.calculateNextMove(game.getGameState(), game.getCurrentPlayer()));
    }
    assertTrue(game.getGameState().checkEnd());
  }
}