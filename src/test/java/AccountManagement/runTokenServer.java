package AccountManagement;

import game.model.Debug;
import net.server.HostServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Starts server for tokenGeneration
 *
 * @author tbuscher
 */
public class runTokenServer {

  static HostServer hostServer;

  @BeforeAll
  public static void beforeAll() {
    try {
      hostServer = new HostServer();
      hostServer.startRestfulServer(8082);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @AfterAll
  public static void afterAll() {
    hostServer.stopRestful();
  }

  @Test
  public void test() {
    Debug.printMessage("_____");
  }
}
