package game.model;

import game.model.gamemodes.GameModeClassic;
import game.model.gamemodes.GameModeDuo;
import game.model.gamemodes.GameModeJunior;
import game.model.gamemodes.GameModeTrigon;
import game.model.player.Ai;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * class used to locally run optimise the AI.
 */
public class MainT {

  /**
   * a simple test class that shows how the domain works at the moment.
   *
   * @param args String[]
   * @author tgeilen
   */
  public static void main(String[] args) {

    int[] result = playClassicGame(true);
    Debug.printMessage("" + result);

    result = playDuoGame(true);
    Debug.printMessage("" + result);

    result = playJuniorGame(true);
    Debug.printMessage("" + result);

    result = playTrigonGame(true);
    Debug.printMessage("" + result);

    Debug.printMessage("Everything works");

    int[][] resClassic = new int[24][24];
    int[][] resTrigon = new int[24][24];

    for (int i = 0; i < 24; i++) {
      for (int j = 0; j < 24; j++) {
        resTrigon[i][j] = 0;
        resClassic[i][j] = 0;
      }
    }

    for (int i = 0; i < 24; i++) {
      for (int j = 0; j < 24; j++) {
        if (j < i) {
          continue;
        }
        Ai.setRoundSections(0, new int[]{i, j});
        Ai.setRoundSections(3, new int[]{i, j});
        long start;
        start = System.currentTimeMillis();
        int[] erg = playTrigonGame(false);
        resTrigon[i][j] = resTrigon[i][j] + erg[0] + erg[2] - erg[1] - erg[3];
        erg = playClassicGame(false);
        resClassic[i][j] = resClassic[i][j] + erg[0] + erg[2] - erg[1] - erg[3];
        long end = System.currentTimeMillis();
        Debug.printMessage("(" + i + ", " + j + ") - DONE in " + (start - end) / 1000 + " s");
      }
    }

    for (int i = 0; i < 24; i++) {
      for (int j = 0; j < 24; j++) {
        resTrigon[i][j] = (int) Math.round(resTrigon[i][j] / 5.0);
        resClassic[i][j] = (int) Math.round(resClassic[i][j] / 5.0);
      }
    }

    getResults(resTrigon, "Trigon");

    getResults(resClassic, "Classic");

  }

  static void getResults(int[][] input, String name) {
    Debug.printMessage("-----------Results " + name + " -----------");
    Debug.printMessage("Results:\n");
    for (int[] i : input) {
      Debug.printMessage(Arrays.toString(i));
    }

    Debug.printMessage("Maxima:");
    for (int[] max : getMax(input)) {
      Debug.printMessage("(" + max[0] + "," + max[1] + "): " + max[2]);
    }

    Debug.printMessage("3er Summen:\n");
    for (int[] i : get3MaxArray(input)) {
      Debug.printMessage(Arrays.toString(i));
    }

    Debug.printMessage("Maxima:");
    for (int[] max : getMax(get3MaxArray(input))) {
      Debug.printMessage("(" + max[0] + "," + max[1] + "): " + max[2]);
    }
    Debug.printMessage("\n\n");
  }

  static int[][] get3MaxArray(int[][] input) {
    int[][] res = new int[input.length][input[0].length];
    for (int i = 0; i < input.length; i++) {
      for (int j = 1; j < input[0].length - 1; j++) {
        res[i][j] = input[i][j - 1] + input[i][j] + input[i][j + 1];
      }
    }
    return res;
  }

  static ArrayList<int[]> getMax(int[][] input) {
    ArrayList<int[]> res = new ArrayList<>();
    int max = -1;
    for (int i = 0; i < input.length; i++) {
      for (int j = 0; j < input[0].length; j++) {
        if (input[i][j] == max) {
          res.add(new int[]{i, j, max});
        }
        if (input[i][j] > max) {
          max = input[i][j];
          res = new ArrayList<>();
          res.add(new int[]{i, j, max});
        }
      }
    }
    return res;
  }

  static int[] playDuoGame(boolean print) {
    GameSession gameSession = new GameSession();

    gameSession.addPlayer(new Player("BOT1", PlayerType.AI_HARD));
    gameSession.addPlayer(new Player("BOT2", PlayerType.AI_GODLIKE));

    Game game = gameSession.startGame(new GameModeDuo());

    game.startGame();
    while (game.getGameState().isStateRunning()) {
      Turn t1 = Ai.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      game.getGameState().playTurn(t1);
      if (print) {
        Debug.printMessage("" + t1);
        Debug.printMessage("" + game.getGameState().getBoard());
      }
    }
    int[] res = game.getGameState().getScores();
    gameSession.stopSession();
    return res;
  }

  static int[] playJuniorGame(boolean print) {
    GameSession gameSession = new GameSession();

    gameSession.addPlayer(new Player("BOT1", PlayerType.AI_HARD));
    gameSession.addPlayer(new Player("BOT2", PlayerType.AI_MIDDLE));

    Game game = gameSession.startGame(new GameModeJunior());

    game.startGame();
    while (game.getGameState().isStateRunning()) {
      Turn t1 = Ai.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      game.getGameState().playTurn(t1);
      if (print) {
        Debug.printMessage("" + t1);
        Debug.printMessage("" + game.getGameState().getBoard());
      }

      Turn t2 = Ai.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      game.getGameState().playTurn(t2);
      if (print) {
        Debug.printMessage("" + t2);
        Debug.printMessage("" + game.getGameState().getBoard());
      }
    }
    int[] res = game.getGameState().getScores();
    gameSession.stopSession();
    return res;
  }

  static int[] playTrigonGame(boolean print) {
    GameSession gameSession = new GameSession();

    gameSession.addPlayer(new Player("BOT1", PlayerType.AI_HARD));
    gameSession.addPlayer(new Player("BOT2", PlayerType.AI_GODLIKE));
    gameSession.addPlayer(new Player("BOT3", PlayerType.AI_HARD));
    gameSession.addPlayer(new Player("BOT4", PlayerType.AI_GODLIKE));

    Game game = gameSession.startGame(new GameModeTrigon());

    game.startGame();
    while (game.getGameState().isStateRunning()) {
      Turn t1 = Ai.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      game.getGameState().playTurn(t1);
      if (print) {
        Debug.printMessage("" + t1);
        Debug.printMessage("" + game.getGameState().getBoard());
      }
    }
    int[] res = game.getGameState().getScores();
    Debug.printMessage("" + game.getGameState().getRound());
    gameSession.stopSession();
    return res;
  }

  static int[] playClassicGame(boolean print) {
    GameSession gameSession = new GameSession();

    gameSession.addPlayer(new Player("BOT1", PlayerType.AI_HARD));
    gameSession.addPlayer(new Player("BOT2", PlayerType.AI_GODLIKE));
    gameSession.addPlayer(new Player("BOT3", PlayerType.AI_HARD));
    gameSession.addPlayer(new Player("BOT4", PlayerType.AI_GODLIKE));

    Game game = gameSession.startGame(new GameModeClassic());

    game.startGame();
    while (game.getGameState().isStateRunning()) {
      Turn t1 = Ai.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      game.getGameState().playTurn(t1);
      if (print) {
        Debug.printMessage("" + t1);
        Debug.printMessage("" + game.getGameState().getBoard());
      }
    }
    int[] res = game.getGameState().getScores();
    gameSession.stopSession();
    return res;
  }

}
