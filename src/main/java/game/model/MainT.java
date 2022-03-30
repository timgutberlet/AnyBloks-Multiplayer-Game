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
    ArrayList<Player> player = new ArrayList<>();
    player.add(p1);
    player.add(p2);
    Game game = new Game(player, new GMClassic());
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

  public static void main(String[] args) {
    Player p1 = new Player("AI1", PlayerType.AI_RANDOM, 0, 0);
    Player p2 = new Player("AI2", PlayerType.AI_RANDOM, 0,
        0);
    Player p3 = new Player("AI3", PlayerType.AI_EASY, 0,
        0);
    Player p4 = new Player("AI4", PlayerType.AI_MIDDLE, 0,
        0);
    Game game = new Game( new ArrayList<>(Arrays.asList(p1, p2,p3,p4)), new GMClassic());
    for (Poly p : game.getGameState().getRemainingPolys(p1)) {
      System.out.println(p);
    }
    for (Poly p : game.getGameState().getRemainingPolys(p2)) {
      System.out.println(p);
    }
    System.out.println(game.getGameState().getBoard());

    game.startGame();
    while (game.getGameState().isStateRunning()) {
      Turn t1 = AI.calculateNextMove(game.getGameState(), p1);
      System.out.println(t1);
      game.getGameState().playTurn(t1);
      System.out.println(game.getGameState());
      System.out.println(game.getGameState().getBoard());

      Turn t2 = AI.calculateNextMove(game.getGameState(), p2);
      System.out.println(t2);
      game.getGameState().playTurn(t2);
      System.out.println(game.getGameState());
      System.out.println(game.getGameState().getBoard());

      Turn t3 = AI.calculateNextMove(game.getGameState(), p3);
      System.out.println(t3);
      game.getGameState().playTurn(t3);
      System.out.println(game.getGameState());
      System.out.println(game.getGameState().getBoard());

      Turn t4 = AI.calculateNextMove(game.getGameState(), p4);
      System.out.println(t4);
      game.getGameState().playTurn(t4);
      System.out.println(game.getGameState());
      System.out.println(game.getGameState().getBoard());
    }
  }
}
