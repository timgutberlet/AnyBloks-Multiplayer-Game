package game.model;

import game.model.gamemodes.GMClassic;
import game.view.InGameView;
import java.util.ArrayList;
import java.util.Arrays;

public class MainT {

  public static void initGame(InGameView view) {
    Player p1 = new Player("AI1", PlayerType.AI_EASY, 0, 0);
    Player p2 = new Player("AI2", PlayerType.AI_MIDDLE, 0,
        0);
    Game game = new Game((ArrayList) Arrays.asList(p1, p2), new GMClassic());
    for (Poly p : game.getGameState().getRemainingPolys(p1)) {
      System.out.println(p);
    }
    for (Poly p : game.getGameState().getRemainingPolys(p2)) {
      System.out.println(p);
    }
    System.out.println(game.getGameState().getBoard());
    game.startGame();
    while (game.getGameState().isStateRunning()) {
      game.getGameState().playTurn(AI.calculateNextMove(game.getGameState(), p1));
      game.getGameState().getBoard().updateBoard(view);
      //System.out.println(gameLogic.getGameState().getBoard());
      game.getGameState().playTurn(AI.calculateNextMove(game.getGameState(), p2));
      game.getGameState().getBoard().updateBoard(view);
      //System.out.println(gameLogic.getGameState().getBoard());
    }
  }
}
