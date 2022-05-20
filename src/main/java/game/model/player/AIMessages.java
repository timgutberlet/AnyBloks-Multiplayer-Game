package game.model.player;

import game.model.GameSession;
import game.model.chat.ChatMessage;

/**
 * generates chat messages for the AI player to comment the game.
 * @author tiotto
 * @date 20.05.2022
 */
public class AIMessages {

  /**
   * comments to chat after a played turn.
   */
  static String[] afterTurnComments = new String[] {"Well played!", "Great move!", "How should I react to that?!?", "I'm so close to giving up!", "That was a surprising move!", "Finally I play against someone worthy ;)", "Nice move!", "You should play in a world cup!", "What a play!", "I underestimated you!", "Let me think about my next move!", "Nice!", "Great!", "Awesome!", "Are you an AI?"};

  /**
   * comments to chat after a finished game.
   */
  static String[] afterGameComments = new String[] {"Well played!", "Such a great game!", "Let us play again!", "I want a rematch!", "Next time I win!", "You are very good!", "Not my best day :(", "This game is so nice implemented by Linus, Tim, Tore, Tobi und Tilman! It's lovely to play!", "It was great playing with you!"};

  /**
   * gets a random comment after a played turn.
   * @return random comment after a played turn
   */
  public static void getAfterTurnAIComment(GameSession gameSession){
    if (gameSession.getLocalPlayer().isAI()) {
      String message = afterTurnComments[(int) Math.round(
          Math.random() * afterTurnComments.length)];
      gameSession.addChatMessage(message);
    }
  }

  /**
   * gets a random comment after a finished game.
   * @return gets a random comment after a finished game
   */
  public static void getAfterMatchAIComment(GameSession gameSession) {
    if (gameSession.getLocalPlayer().isAI()) {
      String message = afterGameComments[(int) Math.round(Math.random() * afterTurnComments.length)];
      gameSession.addChatMessage(message);
    }
  }

}
