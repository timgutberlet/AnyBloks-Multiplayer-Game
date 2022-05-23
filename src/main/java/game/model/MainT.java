package game.model;

import game.model.gamemodes.GMClassic;
import game.model.gamemodes.GMDuo;
import game.model.gamemodes.GMJunior;
import game.model.gamemodes.GMTrigon;
import game.model.player.AI;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.util.ArrayList;
import java.util.Arrays;


public class MainT {

  /**
   * a simple test class that shows how the domain works at the moment
   *
   * @param args
   * @author tgeilen
   */
  public static void main(String[] args) {
/*
    GameSession gameSession = new GameSession();

    gameSession.addPlayer(new Player("BOT1", PlayerType.AI_MIDDLE));
    gameSession.addPlayer(new Player("BOT2", PlayerType.AI_MIDDLE));
    gameSession.addPlayer(new Player("BOT3", PlayerType.AI_HARD));
    gameSession.addPlayer(new Player("BOT4", PlayerType.AI_MIDDLE));

    Game game = gameSession.startGame(new GMTrigon());

//    while (game.getGameState().isStateRunning()) {
//      Debug.printMessage(gameSession.toString());
//      //game.makeMove();
//    }

    game.startGame();
    while (game.getGameState().isStateRunning()) {
      Turn t1 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      Debug.printMessage(t1);
      game.getGameState().playTurn(t1);
      Debug.printMessage(game.getGameState().getBoard());

      Turn t2 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      Debug.printMessage(t2);
      game.getGameState().playTurn(t2);
      Debug.printMessage(game.getGameState().getBoard());

      Turn t3 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      Debug.printMessage(t3);
      game.getGameState().playTurn(t3);
      Debug.printMessage(game.getGameState().getBoard());

      Turn t4 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      Debug.printMessage(t4);
      game.getGameState().playTurn(t4);
      Debug.printMessage(game.getGameState().getBoard());
    }
    Debug.printMessage(game.getGameState().getRound());
    gameSession.stopSession();*/

    AI.setRoundSections(0, new int[]{0, 24});
    int[] result = playClassicGame(true);
    Debug.printMessage("" + result);
    AI.setRoundSections(1, new int[]{0, 24});
    result = playDuoGame(true);
    Debug.printMessage("" + result);
    AI.setRoundSections(2, new int[]{0, 24});
    result = playJuniorGame(true);
    Debug.printMessage("" + result);
    AI.setRoundSections(3, new int[]{0, 24});
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
        AI.setRoundSections(0, new int[]{i, j});
        AI.setRoundSections(3, new int[]{i, j});
        long start = System.currentTimeMillis();
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
    gameSession.addPlayer(new Player("BOT2", PlayerType.AI_MIDDLE));

    Game game = gameSession.startGame(new GMDuo());

    game.startGame();
    while (game.getGameState().isStateRunning()) {
      Turn t1 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      game.getGameState().playTurn(t1);
      if (print) {
        Debug.printMessage("" + t1);
        Debug.printMessage("" + game.getGameState().getBoard());
      }

      Turn t2 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
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

  static int[] playJuniorGame(boolean print) {
    GameSession gameSession = new GameSession();

    gameSession.addPlayer(new Player("BOT1", PlayerType.AI_HARD));
    gameSession.addPlayer(new Player("BOT2", PlayerType.AI_MIDDLE));

    Game game = gameSession.startGame(new GMJunior());

    game.startGame();
    while (game.getGameState().isStateRunning()) {
      Turn t1 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      game.getGameState().playTurn(t1);
      if (print) {
        Debug.printMessage("" + t1);
        Debug.printMessage("" + game.getGameState().getBoard());
      }

      Turn t2 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
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
    gameSession.addPlayer(new Player("BOT2", PlayerType.AI_MIDDLE));
    gameSession.addPlayer(new Player("BOT3", PlayerType.AI_HARD));
    gameSession.addPlayer(new Player("BOT4", PlayerType.AI_MIDDLE));

    Game game = gameSession.startGame(new GMTrigon());

    game.startGame();
    while (game.getGameState().isStateRunning()) {
      Turn t1 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      game.getGameState().playTurn(t1);
      if (print) {
        Debug.printMessage("" + t1);
        Debug.printMessage("" + game.getGameState().getBoard());
      }

      Turn t2 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      game.getGameState().playTurn(t2);
      if (print) {
        Debug.printMessage("" + t2);
        Debug.printMessage("" + game.getGameState().getBoard());
      }

      Turn t3 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      game.getGameState().playTurn(t3);
      if (print) {
        Debug.printMessage("" + t3);
        Debug.printMessage("" + game.getGameState().getBoard());
      }

      Turn t4 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      game.getGameState().playTurn(t4);
      if (print) {
        Debug.printMessage("" + t4);
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
    gameSession.addPlayer(new Player("BOT2", PlayerType.AI_MIDDLE));
    gameSession.addPlayer(new Player("BOT3", PlayerType.AI_HARD));
    gameSession.addPlayer(new Player("BOT4", PlayerType.AI_MIDDLE));

    Game game = gameSession.startGame(new GMClassic());

    game.startGame();
    while (game.getGameState().isStateRunning()) {
      Turn t1 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      game.getGameState().playTurn(t1);
      if (print) {
        Debug.printMessage("" + t1);
        Debug.printMessage("" + game.getGameState().getBoard());
      }

      Turn t2 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      game.getGameState().playTurn(t2);
      if (print) {
        Debug.printMessage("" + t2);
        Debug.printMessage("" + game.getGameState().getBoard());
      }

      Turn t3 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      game.getGameState().playTurn(t3);
      if (print) {
        Debug.printMessage("" + t3);
        Debug.printMessage("" + game.getGameState().getBoard());
      }

      Turn t4 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      game.getGameState().playTurn(t4);
      if (print) {
        Debug.printMessage("" + t4);
        Debug.printMessage("" + game.getGameState().getBoard());
      }
    }
    int[] res = game.getGameState().getScores();
    gameSession.stopSession();
    return res;
  }

}
