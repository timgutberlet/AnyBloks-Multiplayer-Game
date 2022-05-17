package game.model;

import game.model.board.BoardSquare;
import game.model.field.FieldSquare;
import game.model.gamemodes.GMClassic;
import game.model.gamemodes.GMDuo;
import game.model.gamemodes.GMTrigon;
import game.model.player.AI;
import game.model.player.Player;
import game.model.player.PlayerType;
import game.model.polygon.Poly;
import game.model.polygon.PolySquare;
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
      System.out.println(t1);
      game.getGameState().playTurn(t1);
      System.out.println(game.getGameState().getBoard());

      Turn t2 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      System.out.println(t2);
      game.getGameState().playTurn(t2);
      System.out.println(game.getGameState().getBoard());

      Turn t3 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      System.out.println(t3);
      game.getGameState().playTurn(t3);
      System.out.println(game.getGameState().getBoard());

      Turn t4 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      System.out.println(t4);
      game.getGameState().playTurn(t4);
      System.out.println(game.getGameState().getBoard());
    }
    System.out.println(game.getGameState().getRound());
    gameSession.stopSession();*/

    playDuoGame();

   /* int[][] res = new int[20][20];
    for (int i = 0; i < 20; i++) {
      for (int j = 0; j < 20; j++) {
        if (j < i){
          continue;
        }
        AI.setRoundSections(1, new int[]{i,j});
        int[] erg = playDuoGame();
        res[i][j] = erg[1] - erg[0];
        System.out.println("("+ i + ", " + j + ") - DONE");
      }
    }
    System.out.println(Arrays.deepToString(res));*/

  }
  static int[] playDuoGame(){
    GameSession gameSession = new GameSession();

    gameSession.addPlayer(new Player("BOT1", PlayerType.AI_HARD));
    gameSession.addPlayer(new Player("BOT2", PlayerType.AI_MIDDLE));

    Game game = gameSession.startGame(new GMDuo());

    game.startGame();
    while (game.getGameState().isStateRunning()) {
      Turn t1 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      System.out.println(t1);
      game.getGameState().playTurn(t1);
      System.out.println(game.getGameState().getBoard());

      Turn t2 = AI.calculateNextMove(game.getGameState(), game.getCurrentPlayer());
      System.out.println(t2);
      game.getGameState().playTurn(t2);
      System.out.println(game.getGameState().getBoard());
    }
    int[] res = game.getGameState().getScores();
    gameSession.stopSession();
    return res;
  }

}
