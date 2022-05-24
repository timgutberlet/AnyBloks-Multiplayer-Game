package game.model.ai;

import static org.junit.jupiter.api.Assertions.*;

import game.model.Game;
import game.model.GameSession;
import game.model.Turn;;
import game.model.gamemodes.GameModeTrigon;
import game.model.player.Ai;
import game.model.player.Player;
import game.model.player.PlayerType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * tests the game mode classic trigon all ai types.
 * @author tiotto
 * @date 17.05.2022
 */

class AiGamemodeTrigonTest {
  static boolean allValidTurnAIRandom = true;
  static boolean allValidTurnAIEasy = true;
  static boolean allValidTurnAIMiddle = true;
  static boolean allValidTurnAIHard = true;

  @BeforeAll
  static void preparation(){
    GameSession gameSession = new GameSession();

    gameSession.addPlayer(new Player("BOT1", PlayerType.AI_RANDOM));
    gameSession.addPlayer(new Player("BOT2", PlayerType.AI_EASY));
    gameSession.addPlayer(new Player("BOT3", PlayerType.AI_MIDDLE));
    gameSession.addPlayer(new Player("BOT4", PlayerType.AI_HARD));

    Game game = gameSession.startGame(new GameModeTrigon());

    game.startGame();
    while (game.getGameState().isStateRunning()) {
      Turn t1 = Ai.calculateNextMove(game.getGameState(), game.getCurrentPlayer()); //Turn AI_RANDOM
      allValidTurnAIRandom = (game.getGameState().playTurn(t1) || t1 == null) && allValidTurnAIRandom;

      Turn t2 = Ai.calculateNextMove(game.getGameState(), game.getCurrentPlayer()); //Turn AI_EASY
      allValidTurnAIEasy = (game.getGameState().playTurn(t2) || t2 == null) && allValidTurnAIEasy;

      Turn t3 = Ai.calculateNextMove(game.getGameState(), game.getCurrentPlayer()); //Turn AI_MIDDLE
      allValidTurnAIMiddle = (game.getGameState().playTurn(t3) || t3 == null) && allValidTurnAIMiddle;

      Turn t4 = Ai.calculateNextMove(game.getGameState(), game.getCurrentPlayer()); //Turn AI_HARD
      allValidTurnAIHard = (game.getGameState().playTurn(t4) || t4 == null) && allValidTurnAIHard;
    }
    gameSession.stopSession();
  }

  @Test
  void testAIRandom(){
    assertTrue(allValidTurnAIRandom);
  }

  @Test
  void testAIEasy(){
    assertTrue(allValidTurnAIEasy);
  }

  @Test
  void testAIMiddle(){
    assertTrue(allValidTurnAIMiddle);
  }

  @Test
  void testAIHard(){
    assertTrue(allValidTurnAIHard);
  }
}