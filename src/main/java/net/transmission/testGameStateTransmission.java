package net.transmission;

import static net.packet.PacketType.GAME_UPDATE_PACKET;

import game.model.Debug;
import game.model.Game;
import game.model.GameSession;
import game.model.gamemodes.GMClassic;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.io.IOException;
import java.net.URI;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import net.packet.CreateAccountRequestPacket;
import net.packet.GameUpdatePacket;
import net.packet.PacketType;
import net.packet.WrappedPacket;
import net.server.HostServer;

/**
 * Tests only
 *
 * @author tbuscher
 */
public class testGameStateTransmission {

  static HostServer hostServer = new HostServer();



  public static void main(String[] args) {
    try {

      hostServer.startWebsocket(8081);
    } catch (Exception e) {
      e.printStackTrace();
    }
    int counter = 0;
    GameSession gameSession = new GameSession();

    gameSession.addPlayer(new Player("BOT1", PlayerType.AI_EASY));
    gameSession.addPlayer(new Player("BOT2", PlayerType.AI_EASY));
    gameSession.addPlayer(new Player("BOT3", PlayerType.AI_EASY));
    gameSession.addPlayer(new Player("BOT4", PlayerType.AI_EASY));

    Game game = gameSession.startGame(new GMClassic());

    while (game.getGameState().isStateRunning()) {
      Debug.printMessage(gameSession.toString());
      game.makeMove();
      counter++;
      if(counter==2){
        GameUpdatePacket gameUpdatePacket = new GameUpdatePacket(game.getGameState(),
            game.getGameState().getPlayerCurrent().getName());
        WrappedPacket wrappedPacket = new WrappedPacket(GAME_UPDATE_PACKET, gameUpdatePacket);

        final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        EndpointClient client = new EndpointClient();

        Session ses = null;
        try {
          ses = container.connectToServer(client, URI.create("ws://localhost:8080/packet"));


          ses.getBasicRemote().sendObject(wrappedPacket);
          ses.close();
        } catch (DeploymentException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        } catch (EncodeException e) {
          e.printStackTrace();
        }
      }


    }

    gameSession.stopSession();


  }
}
