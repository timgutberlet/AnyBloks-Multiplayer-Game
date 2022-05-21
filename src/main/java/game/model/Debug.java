package game.model;

/**
 * Debug Class to enable/disbale Debug Messages.
 *
 * @author timgutberlet
 */
public class Debug {

  static boolean debug = true;

  /**
   * Prints the Message.
   *
   * @param message
   */
  public static void printMessage(String message) {
    if (debug) {
      System.out.println(message);
    }
  }

  /**
   * Prints Debug Message.
   *
   * @param o       Object of the Debug Source
   * @param message
   */
  public static void printMessage(Object o, String message) {
    if (debug) {
      System.out.println("[" + o.getClass().getName() + "]	" + message);
    }
  }
}
