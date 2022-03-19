package game.model;

import game.view.InGameView;

public class MainT {

  public static void initGame(InGameView view) {
    GameState gameState = new GameState(GameMode.CLASSIC);
    GameLogic gameLogic = new GameLogic(gameState);
    Player p1 = new Player("AI1", PlayerType.AI_HARD, 0, 0);
    Player p2 = new Player("AI2", PlayerType.AI_MIDDLE, gameState.getBoard().getSize() - 1,
        gameState.getBoard().getSize() - 1);
    gameLogic.addPlayer(p1);
    gameLogic.addPlayer(p2);
    for (Poly p : gameState.getRemainingPolys(p1)) {
      System.out.println(p);
    }
    for (Poly p : gameState.getRemainingPolys(p2)) {
      System.out.println(p);
    }
    System.out.println(gameLogic.getGameState().getBoard());
    for (int i = 0; i < 50; i++) {
      gameLogic.playTurn(AI.calculateNextMove(p1, gameLogic));
      gameLogic.getGameState().getBoard().updateBoard(view);
      System.out.println(gameLogic.getGameState().getBoard());
      gameLogic.playTurn(AI.calculateNextMove(p2, gameLogic));
      gameLogic.getGameState().getBoard().updateBoard(view);
      System.out.println(gameLogic.getGameState().getBoard());
    }
  }
}
