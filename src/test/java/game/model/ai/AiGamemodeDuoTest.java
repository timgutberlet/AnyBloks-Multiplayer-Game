package game.model.ai;

import static org.junit.jupiter.api.Assertions.*;

import game.model.Game;
import game.model.GameSession;
import game.model.Turn;
import game.model.gamemodes.GameModeDuo;
import game.model.player.Ai;
import game.model.player.Player;
import game.model.player.PlayerType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * tests the game mode duo with all ai types.
 * @author tiotto
 * @date 17.05.2022
 */
class AiGamemodeDuoTest {
  static boolean allValidTurnAIRandom = true;
  static boolean allValidTurnAIEasy = true;
  static boolean allValidTurnAIMiddle = true;
  static boolean allValidTurnAIHard = true;

  @BeforeAll
  static void preparation(){
    GameSession gameSession1 = new GameSession();

    gameSession1.addPlayer(new Player("BOT1", PlayerType.AI_RANDOM));
    gameSession1.addPlayer(new Player("BOT2", PlayerType.AI_EASY));

    Game game1 = gameSession1.startGame(new GameModeDuo());

    game1.startGame();
    while (game1.getGameState().isStateRunning()) {
      Turn t1 = Ai.calculateNextMove(game1.getGameState(), game1.getCurrentPlayer()); //Turn AI_RANDOM
      allValidTurnAIRandom = (game1.getGameState().playTurn(t1) || t1 == null) && allValidTurnAIRandom;

      Turn t2 = Ai.calculateNextMove(game1.getGameState(), game1.getCurrentPlayer()); //Turn AI_EASY
      allValidTurnAIEasy = (game1.getGameState().playTurn(t2) || t2 == null) && allValidTurnAIEasy;
    }
    gameSession1.stopSession();

    //second gameSession

    GameSession gameSession2 = new GameSession();

    gameSession2.addPlayer(new Player("BOT3", PlayerType.AI_MIDDLE));
    gameSession2.addPlayer(new Player("BOT4", PlayerType.AI_HARD));

    Game game2 = gameSession2.startGame(new GameModeDuo());

    game2.startGame();
    while (game2.getGameState().isStateRunning()) {
      Turn t1 = Ai.calculateNextMove(game2.getGameState(), game2.getCurrentPlayer()); //Turn AI_MIDDLE
      allValidTurnAIMiddle = (game2.getGameState().playTurn(t1) || t1 == null) && allValidTurnAIMiddle;

      Turn t2 = Ai.calculateNextMove(game2.getGameState(), game2.getCurrentPlayer()); //Turn AI_HARD
      allValidTurnAIHard = (game2.getGameState().playTurn(t2) || t2 == null) && allValidTurnAIHard;
    }
    gameSession2.stopSession();
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
